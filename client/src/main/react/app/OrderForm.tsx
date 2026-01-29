/**
 * Copyright 2026 Martin Doychinov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Input, type InputOptions } from "../utils/Input.tsx";
import {
  ExchangeService,
  type OrderEntryDto,
  OrderFormDto,
  OrderFormService, type ProductDto,
  RequestService
} from "../api/sales";
import {
  type Path,
  type SubmitErrorHandler,
  type SubmitHandler,
  useFieldArray,
  useForm
} from "react-hook-form";
import { useLocation } from "react-router";
import { useAsyncState } from "../utils/async/useAsyncState.tsx";
import { AsyncFragment } from "../utils/async/AsyncFragment.tsx";
import { type Key, useCallback, useEffect, useEffectEvent, useLayoutEffect, useRef, useState } from "react";
import { Table } from "../utils/Table.tsx";
import { useFormat } from "../utils/useFormat.tsx";
import { useI18n } from "../context/i18n/useI18n.tsx";
import { formatDate, parseDate, prepareDateProps } from "../utils/DateUtils.ts";
import { useCalendar } from "../utils/modal/useCalendar.tsx";
import { Loading } from "../utils/Loading.tsx";
import { OrderFormModel } from "./model/OrderFormModel.ts";
import { useConfirm } from "../utils/modal/useConfirm.tsx";
import { useAuth } from "../context/auth/useAuth.tsx";
import { formatNumber, range, toFixedNumber } from "../utils/NumberUtils.ts";
import { useNavigate } from "react-router-dom";
import { useProduct } from "./modal/useProduct.tsx";
import { useContact } from "./modal/useContact.tsx";
import {
  getExchangeList,
  getOptionExchange,
  getOptionOrderState,
  getOptionOrderType,
  getOptionPayment,
  getOrderTypeList
} from "./model/OptionModel.ts";
import { useKeyboardNavigation } from "../utils/useKeyboard.tsx";
import { findEnabled, splitPath } from "../utils/LayoutModel.ts";
import { getSelection } from "../utils/TableUtils.tsx";
import { mapToReportDto, usePrintOrder } from "./usePrint.tsx";

const programmatic = {
  shouldDirty: false,
  shouldTouch: false,
  shouldValidate: false,
}

const priceList = ["productPrice", ...range(1, 9).map(i => `productPrice${i}`)];
const codeList = ["productCode", ...range(1, 9).map(i => `productCode${i}`)];

function OrderForm() {
  const { state } = useLocation();
  const { t } = useI18n();
  const { fullname } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState<string[]>([]);
  const [orderFormOptions, setOrderFormOptions] = useState<{ [key: string]: InputOptions<OrderFormDto> }>({});
  const printOrder = usePrintOrder();
  const modalConfirm = useConfirm();
  const modalCalendar = useCalendar();
  const modalContact = useContact();
  const modalProduct = useProduct();
  const model = OrderFormModel;

  // console.log("order render")

  const loadOrder = useCallback(async (args: { orderId?: number, target?: "edit" | "create" | "copy" | "delete" }) => {
    setOrderFormOptions({
      ...orderFormOptions,
      orderState: () => getOptionOrderState(),
      orderType: async () => { return getOptionOrderType(await getOrderTypeList()); },
      orderCcp: async () => { return getOptionExchange(await getExchangeList()); },
      payment: () => getOptionPayment(),
    });

    let res: FormType;

    if (args.orderId) {
      if (args.target === undefined || args.target === "edit") {
        res = {
          selected: [],
          ...await OrderFormService.getOrderById({ id: args.orderId })
        };
      }
      else {
        res = {
          selected: [],
          ...await OrderFormService.getOrderCopyById({ id: args.orderId, content: args.target === "copy" }),
        };
        res.orderId = undefined;
        res.orderRef = `${t(`~orderType.${res.orderType?.typeId}`)} ${formatNumber(res.orderNum ?? 0, "0000000000")} / ${formatDate(parseDate(res.orderDate), t("~format.date"))}`
        res.orderState = OrderFormDto.orderState.DRAFT;
        res.orderType = undefined;
        res.orderNum = undefined;
        res.orderDate = new Date().toISOString();
      }
    }
    else {
      res = {
        selected: [],
        orderState: OrderFormDto.orderState.DRAFT,
        orderDate: formatDate(new Date(), t("~format.datetime")),
        orderTaxPct: 0,
        orderTax: 0,
        orderDiscount: 0,
        orderSum: 0,
        orderTotal: 0,
        orderEntries: [],
      };
    }

    return res;
  }, [orderFormOptions, t]);

  const orderData = useAsyncState(
    useCallback(loadOrder, [state?.orderId, state?.target]),
    { orderId: state?.orderId, target: state?.target }
  );

  type FormType = OrderFormDto & { action?: Path<OrderFormDto> } & { selected?: string[] };
  const formUse = useForm<FormType>({
    defaultValues: {
      orderState: OrderFormDto.orderState.DRAFT,
      orderResp: fullname ?? "",
      orderEntries: []
    },
    shouldUnregister: false,
    mode: "onBlur",
    reValidateMode: "onBlur"
  });
  useEffect(() => {
    if (orderData.finished) {
      // console.log("init", orderData.result);
      formUse.reset(orderData.result);
      formUse.trigger();
    }
  }, [formUse, orderData.result, orderData.finished]);

  const orderEntries = useFieldArray<FormType>({
    control: formUse.control,
    name: "orderEntries",
  });

  const updateTime = useEffectEvent(() => {
    const values = formUse.getValues();
    if (!values.orderId && values.orderState === OrderFormDto.orderState.DRAFT) {
      formUse.setValue("orderDate", formatDate(new Date(), t("~format.datetime")), programmatic);
    }
  });

  useEffect(() => {
    const id = setInterval(() => {
      updateTime(); // always latest setValue
    }, 1000);
    return () => clearInterval(id);
  }, [updateTime]);

  useKeyboardNavigation((ctx) => {
    const name = ctx.current.name;
    let next: string | undefined;
    if (name.startsWith("orderEntries.")) {
      const p = splitPath(name, 2);
      let step: number;
      switch (ctx.key) {
        case "ArrowLeft":
          step = -1;
          break;
        case "ArrowRight":
          step = +1;
          break;
        case "ArrowUp":
          p[1] = `${Math.max(0, +p[1] - 1)}`;
          step = 0;
          break;
        case "ArrowDown":
        case "Enter":
          p[1] = `${Math.min(+p[1] + 1, orderEntries.fields.length ?? 0 - 1)}`;
          step = 0;
          break;
        default:
          step = 0;
          break
      }
      const found = findEnabled(
        model.table.items,
        p[2],
        step
      );
      if (found) {
        next = `${p[0]}.${p[1]}.${found.name}`;
      }
    }
    else {
      const found = findEnabled(
        model.fields.items,
        name,
        ((ctx.key === "ArrowLeft" || ctx.key === "ArrowUp") ? -1 : 1)
      );
      next = found?.name;
    }

    if (next) {
      formUse.setFocus(next as Path<FormType>, { shouldSelect : true });
      ctx.event.preventDefault();
      ctx.event.stopPropagation();
    }

    return null;
  }, [formUse, orderEntries, model.fields.items]);

  async function getExchangeRate(ccp?: string) {
    const cur = ccp?.split("/") ?? [undefined, undefined];
    const exchange = (await ExchangeService.findExchange({ exchangeBase: cur[0], exchangeTarget: cur[1]}))?.content;
    return exchange?.length ? exchange[0].exchangeRate : undefined;
  }

  const activeName = useRef<string | null>(null);

  useLayoutEffect(() => {
    const el = document.activeElement as HTMLElement | null;
    activeName.current = el?.getAttribute("name") ?? null;
  });

  useEffect(() => {
    // Subscribe to all field changes
    const subscription = formUse.watch(async (values, { name, type }) => {
      if ((type === "change" && name !== "selected") || (type === undefined && (name === "selected" || values.action as string === "save"))) {
        const p = splitPath(name as Path<FormType>, 2);
        let target = p.at(-1);
        let orderTaxPct = values.orderTaxPct;
        if (target === "typeId") {
          setLoading(true);
          try {
            const typeId = Number(values.orderType?.typeId);
            const last = await OrderFormService.getLastOrderByOrderType({ orderTypeId: typeId });
            if (last) {
              formUse.setValue("orderSupplier", last.orderSupplier);
              if (last) {
                formUse.setValue("orderType", last.orderType);
                formUse.setValue("orderTaxPct", last.orderType?.typeTaxPct);
                formUse.setValue("orderCcp", last.orderType?.typeCcp);
                formUse.setValue("orderRate", await getExchangeRate(last.orderType?.typeCcp));
                formUse.setValue("orderSupplier", last.orderSupplier);
                formUse.setValue("orderCounter", last.orderCounter);
                formUse.setValue("orderNum", last.orderNum);
                formUse.setValue("orderDate", last.orderDate);
                formUse.setValue("orderState", last.orderState);
                orderTaxPct = last.orderType?.typeTaxPct;
                target = "orderTaxPct";
                await formUse.trigger();
              }
            }
          }
          finally {
            setLoading(false);
          }
        }

        if (target === "entryQuantity"
          || target === "entryPrice"
          || target === "selected"
          || target === "entryDiscountPct"
          || target === "orderTaxPct"
        ) {
          const current = p[1] !== undefined ? Number(p[1]) : undefined;
          const summary = { orderDiscount: 0, orderSum: 0, orderTax: 0, orderTotal: 0 };
          values.orderEntries?.forEach((entry, index) => {
            if (entry) {
              const normal = (entry.entryQuantity ?? 0) * (entry.entryPrice ?? 0);
              const discount = normal * ((entry.entryDiscountPct ?? 0) / 100.0);
              const sum = normal - discount;
              const tax = sum * (orderTaxPct ?? 0) / 100;

              summary.orderDiscount = summary.orderDiscount + discount;
              summary.orderSum = summary.orderSum + sum;
              summary.orderTax = summary.orderTax + tax;
              summary.orderTotal = summary.orderTotal + sum + tax;

              if (current === undefined || current === index) {
                formUse.setValue(`orderEntries.${index}.entryDiscount`, toFixedNumber(discount, 2), programmatic);
                formUse.setValue(`orderEntries.${index}.entrySum`, toFixedNumber(sum, 2), programmatic);
                formUse.setValue(`orderEntries.${index}.entryTax`, toFixedNumber(tax, 2), programmatic);
                formUse.setValue(`orderEntries.${index}.entryTotal`, toFixedNumber(sum + tax, 2), programmatic);
              }
            }
          });
          formUse.setValue("orderDiscount", toFixedNumber(summary?.orderDiscount, 2), programmatic);
          formUse.setValue("orderSum", toFixedNumber(summary?.orderSum, 2), programmatic);
          formUse.setValue("orderTax", toFixedNumber(summary?.orderTax, 2), programmatic);
          formUse.setValue("orderTotal", toFixedNumber(summary?.orderTotal, 2), programmatic);
        }
        else if (target === "orderCcp") {
          formUse.setValue("orderRate", await getExchangeRate(values.orderCcp), programmatic);
        }
        // console.log({type, name, path, target, values});
      }
    });

    // Cleanup subscription on unmount
    return () => subscription.unsubscribe();
  }, [formUse]);

  const onSubmit: SubmitHandler<FormType> = useCallback(async (values, event) => {
    const nativeEvent = event?.nativeEvent as SubmitEvent;
    const submitter = nativeEvent?.submitter as { name?: string, value?: string } | null;

    const actionName = submitter?.name ?? "action";
    const actionValue = submitter?.value as string ?? "true";
    formUse.setValue(actionName as Path<OrderFormDto>, actionValue, programmatic);

    if (actionValue === "add") {
      orderEntries.append({
        entryRow: orderEntries.fields.length + 1,
        entryQuantity: 0,
        entryPrice: 0,
        entryDiscountPct: 0,
        entryDiscount: 0,
        entrySum: 0,
        entryTax: 0,
        entryTotal: 0,
      });
      await formUse.trigger();
    }
    else if (actionValue === "save") {
      const isValid = await formUse.trigger();
      if (isValid) {
        const zeroPrice: number[] = [];
        const zeroQuantity: number[] = [];
        values.orderEntries?.forEach((entry, index) => {
          if (entry.entryPrice == 0) {
            zeroPrice.push(index + 1);
          }
          if (entry.entryQuantity == 0) {
            zeroQuantity.push(index + 1);
          }
        });
        let confirmed = true;
        if (zeroPrice.length || zeroQuantity.length) {
          const confirmation = await modalConfirm.value(
            {
              title: t("~confirm.question"),
              content: <>{!!zeroPrice.length && `${t("~confirm.zeroPrice")}: ${zeroPrice}`}
                {!!zeroPrice.length && <br/>}
                {!!zeroQuantity.length && `${t("~confirm.zeroQuantity")}: ${zeroQuantity}`}
              </>
            }
          );
          confirmed = confirmation.result?.confirmed ?? false;
        }

        if (confirmed) {
          setLoading(true);
          try {
            const requestBody = prepareDateProps(
              values,
              (value) => parseDate(value, t("~format.datetime"))?.toISOString()
            );
            let response: OrderFormDto;
            if (requestBody.orderId) {
              requestBody.orderResp = fullname ?? requestBody.orderResp;
              response = await OrderFormService.updateOrder({ id: requestBody.orderId, requestBody });
            }
            else {
              response = await OrderFormService.createOrder({ requestBody });
            }
            formUse.reset(response);

            if (response.orderId !== undefined) {
              const confirmation = await modalConfirm.value(
                { title: t("~confirm.print.title"), content: t("~confirm.question") }
              );
              if (confirmation.result?.confirmed) {
                printOrder(mapToReportDto(response));
              }
              navigate("/app/order", { replace: true });
              navigate(0);
            }
          }
          finally {
            setLoading(false);
          }
        }
      }
    }
    else if (actionValue === "cancel") {
      const confirmation = await modalConfirm.value(
        { title: t("~confirm.undo.title"), content: t("~confirm.question") }
      );
      if (confirmation.result?.confirmed) {
        formUse.reset();
      }
    }
    else if (actionValue === "delete") {
      if (values.selected?.length) {
        const confirmation = await modalConfirm.value(
          { title: t("~confirm.delete.title"), content: t("~confirm.question") }
        );
        if (confirmation.result?.confirmed) {
          const ids = values.selected.map(id => Number(id));
          orderEntries.remove(ids);
          const len = orderEntries.fields.length - ids.length;
          for (let i = 0; i < len; i++) {
            formUse.setValue(`orderEntries.${i}.entryRow`, i + 1, programmatic);
          }
          formUse.setValue("selected", len === 0 ? [] : ids[0] < len ? [String(ids[0])] : [String(len - 1)], programmatic);
          await formUse.trigger();
        }
      }
    }
    else if (actionValue === "print") {
      const isValid = await formUse.trigger();
      if (isValid && values.orderId) {
        setLoading(true);
        try {
          printOrder(mapToReportDto(values));
        }
        finally {
          setLoading(false);
        }
      }
    }
    else {
      const mi = actionValue.indexOf(":");
      const ti = Math.max(mi, actionValue.lastIndexOf("."));
      if (mi >= 0 && ti > 0) {
        const fn = actionValue.slice(0, mi);
        const path = actionValue.slice(mi + 1, ti);
        const value = actionValue.slice(mi + 1);
        const target = actionValue.slice(ti + 1);

        // console.log({ fn, path, value, target });

        if (fn === "calendar") {
          const res = await modalCalendar.value(
            { date: parseDate(formUse.getValues(value as Path<FormType>) as string, t("~format.datetime")) }
          );
          if (res.result) {
            formUse.setValue(value as Path<FormType>, formatDate(res.result.date, t("~format.datetime")), programmatic);
            await formUse.trigger();
          }
        }
        else if (fn === "contact") {
          const res = (await modalContact.value(
            { [target]: formUse.getValues(value as Path<FormType>) as string }
          )).result?.at(-1);
          if (res) {
            formUse.setValue((path || value) as Path<FormType>, res);
            if (path === "orderCustomer") {
              formUse.setValue("orderRcvd", res.contactResp, programmatic);
            }
            await formUse.trigger();
          }
        }
        else if (fn === "location") {
          const res = (await modalContact.value(
            { contactCode1: values.orderCustomer?.contactCode1 }
          )).result?.at(-1);
          if (res) {
            if (path === "orderCustomer") {
              formUse.setValue("orderCustomer.contactLocation", res.contactLocation, programmatic);
            }
            await formUse.trigger();
          }
        }
        else if (fn === "contact.uic") {
          if (values.orderCustomer?.contactCode1) {
            const found = await RequestService.requestJson({api: "uic", params: values.orderCustomer?.contactCode1});
            if (found) {
              formUse.setValue("orderCustomer.contactName", found["contactName"], programmatic);
              formUse.setValue("orderCustomer.contactAddress", found["contactAddress"], programmatic);
              formUse.setValue("orderCustomer.contactCode2", found["contactCode2"], programmatic);
              await formUse.trigger();
            }
          }
        }
        else if (fn === "product") {
          const p = splitPath(`${(path || value)}`, 1);
          const i = parseInt(p[1]);
          const res = await modalProduct.value(
            { selection: "many" } // productName: formUse.getValues(value as Path<FormType>) as string
          );
          const list = res.result;
          if (list) {
            const pattern = res.args?.view?.slice(3);
            const regex = pattern ? new RegExp(pattern) : undefined;
            const priceField = (priceList.find(item => !regex?.test(item)) ?? "productPrice") as keyof ProductDto;
            const codeField = (codeList.find(item => !regex?.test(item)) ?? "productCode") as keyof ProductDto;
            const rate = values.orderRate ? values.orderRate : 1.0;
            for (let j = 0; j < list.length; j++) {
              const k = i + j;
              if (j > 0 || k >= orderEntries.fields.length) {
                orderEntries.insert(k,{
                  entryRow: k + 1,
                  entryQuantity: 0,
                  entryPrice: 0,
                  entryDiscountPct: 0,
                  entryDiscount: 0,
                  entrySum: 0,
                  entryTax: 0,
                  entryTotal: 0,
                });
              }
              const product = list[j];
              formUse.setValue(`orderEntries.${k}.entryProduct`, product, programmatic);
              formUse.setValue(`orderEntries.${k}.entryLabel`, product.productName, programmatic);
              formUse.setValue(`orderEntries.${k}.entryUnits`, product.productUnits, programmatic);
              formUse.setValue(`orderEntries.${k}.entryMeasure`, product.productMeasure, programmatic);
              formUse.setValue(`orderEntries.${k}.entryCode`, product[codeField] as string, programmatic);
              formUse.setValue(`orderEntries.${k}.entryBarcode`, product.productBarcode, programmatic);
              const price = (product?.[priceField] as number) ?? 0.0;
              if (values.orderCcp?.endsWith(product.productCy ?? "")) {
                formUse.setValue(`orderEntries.${k}.entryPrice`, toFixedNumber(price / rate, 2), programmatic);
              }
              else {
                formUse.setValue(`orderEntries.${k}.entryPrice`, price, programmatic);
              }
              formUse.setValue(`orderEntries.${k}.entryAvailable`, product.productAvailable, programmatic);
            }

            const len = orderEntries.fields.length - 1;
            for (let j = len; j < len + list.length; j ++) {
              formUse.setValue(`orderEntries.${j}.entryRow`, j + 1, programmatic);
            }

            await formUse.trigger();
            setTimeout(() => formUse.setFocus(`orderEntries.${i}.entryQuantity`, { shouldSelect: true }), 300);
          }
        }
      }
    }

    // console.log({ actionName, actionValue, values: values });
  }, [formUse, orderEntries, modalConfirm, t, fullname, navigate, modalCalendar, modalContact, modalProduct]);

  const onError: SubmitErrorHandler<FormType> = useCallback(
    async (errors, event) => {
      await onSubmit(formUse.getValues(), event);
  }, [formUse, onSubmit]);

  const handleSelect = (data?: OrderEntryDto[], index?: number, ctrlKey?: boolean, shiftKey?: boolean) => {
    const selected = formUse.getValues("selected") || [];
    const res: string[] = getSelection("many", selected, undefined, data, index, ctrlKey, shiftKey);
    formUse.setValue("selected", res, {
      shouldDirty: false,
      shouldTouch: false,
      shouldValidate: false,
    });
  };

  const formatters = useFormat();
  const cols = model.fields.columns ?? 1;
  const groups = [undefined, "table", "action"] as const;

  return <>
    <AsyncFragment key={"order"} asyncState={orderData}>
      <form
        key={"order_form"}
        onSubmit={formUse.handleSubmit(onSubmit, onError)}
        onReset={() => formUse.reset()}
        className="flex-1 flex flex-col overflow-hidden"
      /*form*/>
        {groups.map(group => {
          if (group !== "table") {
            return (
              <div key={group ? group : "_"} className={`grid lg:grid-cols-${cols} sm:grid-cols-4 gap-1 p-1`}>
                {model.fields.items?.filter(item => item.group === group).map((item, index) => {
                  return (
                    <div
                      key={item.name ?? index}
                      className={`col-span-${Math.min(item.span ?? 1, 2)} lg:col-span-${item.span ?? 1} ${item.mode === "hidden" ? "hidden" : ""}`}
                    /*div*/>
                      {
                        item.name
                          ? <Input<OrderFormDto>
                            key={item.name}
                            form={formUse}
                            rules={item.rules}
                            variant={item.variant ?? "label"}
                            name={item.name as Path<OrderFormDto>}
                            type={item.mode === "hidden" ? "hidden" : item.type}
                            prefix={t(item.label)}
                            suffix={t(item.suffix)}
                            disabled={item.mode === "disabled" || (!!item.name && disabled.includes(item.name))}
                            autoComplete="off"
                            formatter={formatters[item.type ?? "none"]}
                            format={item.format}
                            options={orderFormOptions[item.source ?? "none"]}
                            action={item.source}
                          />
                          : <>{item.label}</>
                      }
                    </div>
                  )
                })}
              </div>
            )
          }
          if (group == "table") {
            return (
              <div key={group} className={`flex-1 overflow-y-auto`}>
                <Table
                  context={"order"}
                  data={orderEntries.fields}
                  dataKey={"entryId"}
                  formatters={formatters}
                  layout={model.table}
                  selector={(entry, index) => {
                    return (
                      <input
                        key={(index !== undefined ? index : "*") as Key}
                        {...formUse.register("selected", {
                          onChange: (e) => {
                            if (e.target.value === "*") {
                              handleSelect(orderEntries.fields, undefined);
                            }
                          }
                        })}
                        type={"checkbox"}
                        value={(index !== undefined ? index : "*").toString()}
                        className={index !== undefined ? "peer checkbox checkbox-lg" : "checkbox checkbox-lg"}
                      />
                    )
                  }}
                  onClick={handleSelect}
                /*Table*/>
                  {(props) => {
                    return (
                      props.item.name
                        ? <Input<OrderFormDto>
                          key={props.item.name}
                          form={formUse}
                          rules={props.item.rules}
                          name={`orderEntries.${props.index}.${props.item.name}` as Path<OrderFormDto>}
                          type={props.item.mode === "hidden" ? "hidden" : props.item.type}
                          disabled={props.item.mode === "disabled"}
                          variant={"ghost"}
                          autoComplete="off"
                          formatter={formatters[props.item.type ?? "none"]}
                          format={props.item.format}
                          options={orderFormOptions[props.item.source ?? "none"]}
                          action={props.item.source}
                        />
                        : <span key={props.index}>{props.item.name ? `${props.entry[props.item.name as keyof OrderEntryDto]}` : ""}</span>
                    )
                  }}
                </Table>
                <button type={"submit"} className={"btn w-full"} name={"action"} value={`product:orderEntries.${orderEntries.fields.length}.entryLabel`}>{t("~action.add")}</button>
              </div>
            )
          }
        })}
      </form>
    </AsyncFragment>
    {modalConfirm.component}
    {modalCalendar.component}
    {modalContact.component}
    {modalProduct.component}
    {loading && <Loading/>}
  </>
}

export default OrderForm;