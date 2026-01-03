import { Input, type InputOptions } from "../utils/Input.tsx";
import {
  type ContactDto,
  ContactService,
  type ExchangeDto,
  ExchangeService, OpenAPI,
  type OrderEntryDto,
  OrderFormDto,
  OrderFormService,
  type OrderTypeDto,
  OrderTypeService,
  PdfService,
  type ProductDto,
  ProductService, RequestService
} from "../api/sales";
import {
  Controller,
  type Path,
  type SubmitErrorHandler,
  type SubmitHandler,
  useFieldArray,
  useForm
} from "react-hook-form";
import { useLocation } from "react-router";
import { useAsyncState } from "../utils/async/useAsyncState.tsx";
import { AsyncFragment } from "../utils/async/AsyncFragment.tsx";
import { type Key, useCallback, useEffect, useMemo, useState } from "react";
import { Table } from "../utils/Table.tsx";
import { useFormat } from "../utils/useFormat.tsx";
import useModal from "../utils/modal/useModal.tsx";
import { ContactListModel } from "./model/ContactListModel.ts";
import { ListForm } from "../utils/ListForm.tsx";
import type { ModalProps } from "../utils/modal/Modal.tsx";
import { ProductListModel } from "./model/ProductListModel.ts";
import { useI18n } from "../context/i18n/useI18n.tsx";
import { formatDate, parseDate, prepareDateProps } from "../utils/DateUtils.ts";
import { useCalendar } from "../utils/modal/useCalendar.tsx";
import { Loading } from "../utils/Loading.tsx";
import { OrderFormModel } from "./model/OrderFormModel.ts";
import { useConfirm } from "../utils/modal/useConfirm.tsx";
import { useAuth } from "../context/auth/useAuth.tsx";
import { formatNumber } from "../utils/NumberUtils.ts";
import { useNavigate } from "react-router-dom";
import { delay } from "../utils/async/AsyncUtils.tsx";


export function OrderForm() {
  const { state } = useLocation();
  const { t } = useI18n();
  const { fullname } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState<string[]>([]);
  const [orderFormOptions, setOrderFormOptions] = useState<{ [key: string]: InputOptions<OrderFormDto> }>({});
  const [orderTypeList, setOrderTypeList] = useState<OrderTypeDto[]>();
  const [exchangeList, setExchangeList] = useState<ExchangeDto[]>();
  const modalConfirm = useConfirm();
  const modalCalendar = useCalendar();

  const modalContactProps: ModalProps<ContactDto, Parameters<typeof ContactService.findContact>[number]> = useMemo(() => (
    {
      header: t("~contact.title"),
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <ListForm model={ContactListModel} close={props.close} args={props.args}/>
      },
    }
  ), []);
  const modalContact = useModal(modalContactProps);

  const modalProductProps: ModalProps<ProductDto, Parameters<typeof ProductService.findProduct>[number]> = useMemo(() => (
    {
      header: t("~product.title"),
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <ListForm model={ProductListModel} close={props.close} args={props.args}/>
      },
    }
  ), []);
  const modalProduct = useModal(modalProductProps);

  const model = OrderFormModel;

  const loadOrder = async (args: { orderId?: number, target?: "edit" | "create" | "copy" | "delete" }) => {
    const _type = await OrderTypeService.findOrderType({ sort: "typeOrder" });
    const orderType = [{ label: "" }, ..._type.content?.map(item => (
      { value: `${item.typeId}`, label: t(`~${item.typeKey}`) ?? "?", disabled: (args === undefined || args.target === "edit") }
    )) ?? []];
    setOrderTypeList(_type.content);

    const _exchange = await ExchangeService.findExchange({});
    const orderCcp = _exchange.content?.map(item => (
      { value: `${item.exchangeTarget}/${item.exchangeBase}`, label: `${item.exchangeTarget} (${item.exchangeBase})` }
    )) ?? [];
    setExchangeList(_exchange.content);

    const orderState = Object.values(OrderFormDto.orderState)
      .reverse()
      .map(value => (
        { value: value, label: `~orderState.${value}`, disabled: (args === undefined || args.target === "edit") }
      ));

    setOrderFormOptions({
      ...orderFormOptions,
      orderState: () => orderState,
      orderType: () => orderType,
      orderCcp: () => orderCcp,
      payment: () => [
        { label: "" },
        { value: "bank", label: "~payment.bank" },
        { value: "cash", label: "~payment.cash" },
      ],
    });

    let res: FormType;

    if (args.orderId) {
      if (args.target === undefined || args.target === "edit") {
        res = {
          selected: "*",
          ...await OrderFormService.getOrderById({ id: args.orderId })
        };
      }
      else {
        if (args.target === "delete") {
          res = {
            selected: "*",
            ...await OrderFormService.getOrderById({ id: args.orderId }),
          };
        }
        else {
          res = {
            selected: "*",
            ...await OrderFormService.getOrderCopyById({ id: args.orderId, content: args.target === "copy" })
          };
        }
        res.orderRef = `${t(`~orderType.${res.orderType?.typeId}`)} ${formatNumber(res.orderNum ?? 0, "0000000000")}/${formatDate(parseDate(res.orderDate), t("~pattern.datetime"))}`
        res.orderState = OrderFormDto.orderState.DRAFT;
        res.orderType = undefined;
        res.orderNum = undefined;
        res.orderDate = new Date().toISOString();
      }
    }
    else {
      res = {
        selected: "*",
        orderState: OrderFormDto.orderState.DRAFT,
        orderDate: formatDate(new Date(), t("~pattern.datetime")),
        orderTaxPct: 0,
        orderTax: 0,
        orderDiscount: 0,
        orderSum: 0,
        orderTotal: 0,
        orderEntries: [],
      };
    }

    // await formUse.trigger();
    // formUse.setFocus("orderType.typeId");

    return res;
  }

  const orderData = useAsyncState(
    useCallback(loadOrder, [state?.orderId, state?.target]),
    { orderId: state?.orderId, target: state?.target }
  );

  type FormType = OrderFormDto & { action?: Path<OrderFormDto> } & { selected?: string };
  const formUse = useForm<FormType>({
    defaultValues: {
      orderResp: fullname ?? "",
      orderEntries: []
    },
    values: orderData.result,
    mode: "onChange",
    reValidateMode: "onChange",
  });
  useEffect(() => {
    formUse.reset(orderData.result);
    formUse.trigger();
  }, [orderData.result]);

  const orderEntries = useFieldArray<FormType>({
    control: formUse.control,
    name: "orderEntries",
  });

  async function print(orderId: number, subtitle: string) {
    const url = `${OpenAPI.BASE}/api/pdf/${orderId}?lang=bg&subtitle=${subtitle}`;
    window.open(url);
  }

  useEffect(() => {
    // Subscribe to all field changes
    const subscription = formUse.watch(async (values, { name, type }) => {
      if (type === "change" && name) {
        const mt = name.lastIndexOf(".");
        const path = mt < 0 ? undefined : name.slice(0, mt);
        const target = mt < 0 ? name : name.slice(mt + 1);
        if (target === "entryPrice"
          || target === "entryDiscountPct"
          || target === "entryQuantity"
        ) {
          const orderEntry = formUse.getValues(path as Path<OrderFormDto>) as OrderEntryDto;
          const discount = (orderEntry.entryQuantity ?? 0) * (orderEntry.entryPrice ?? 0) * ((orderEntry.entryDiscountPct ?? 0) / 100.0);
          const sum = (orderEntry.entryQuantity ?? 0) * (orderEntry.entryPrice ?? 0) * ((100.0 - (orderEntry.entryDiscountPct ?? 0)) / 100.0);
          const tax = sum * (values.orderTaxPct ?? 0) / 100;
          if (!isFinite(orderEntry.entrySum ?? NaN) || (Math.abs((orderEntry.entrySum ?? 0) - sum) > 0.000001)) {
            formUse.setValue(path as Path<OrderFormDto>, {
              ...orderEntry,
              entryDiscount: discount,
              entrySum: sum,
              entryTax: tax,
              entryTotal: (sum + tax)
            });

            const summary = values.orderEntries?.reduce((res, entry) => {
              res.orderDiscount = res.orderDiscount + (entry?.entryDiscount ?? 0);
              res.orderSum = res.orderSum + (entry?.entrySum ?? 0);
              res.orderTax = res.orderTax + (entry?.entryTax ?? 0);
              res.orderTotal = res.orderTotal + (entry?.entryTotal ?? 0);
              return res;
            }, { orderDiscount: 0, orderSum: 0, orderTax: 0, orderTotal: 0 });

            formUse.setValue("orderDiscount", summary?.orderDiscount);
            formUse.setValue("orderSum", summary?.orderSum);
            formUse.setValue("orderTax", summary?.orderTax);
            formUse.setValue("orderTotal", summary?.orderTotal);
          }
        }
        else if (target === "selected") {

        }
        else if (target === "orderCcp") {
          const found = exchangeList?.find(item => `${item.exchangeTarget}/${item.exchangeBase}` === values.orderCcp);
          formUse.setValue("orderRate", found?.exchangeRate);
        }
        else if (target === "typeId") {
          const typeId = Number(values.orderType?.typeId);
          const orderType = orderTypeList?.find(orderType => orderType.typeId === typeId);
          formUse.setValue("orderType", orderType);
          formUse.setValue("orderNum", orderType?.typeNum);
          formUse.setValue("orderTaxPct", orderType?.typeTaxPct);
          formUse.setValue("orderCcp", orderType?.typeCcp);

          if (orderType) {
            const exchange = exchangeList?.find(item => `${item.exchangeTarget}/${item.exchangeBase}` === orderType.typeCcp);
            formUse.setValue("orderRate", exchange?.exchangeRate);
          }

          const last = await OrderFormService.getLastOrderByOrderType({ orderTypeId: typeId});
          if (last) {
            formUse.setValue("orderSupplier", last.orderSupplier);
          }
          await formUse.trigger();
        }
        // console.log({type, name, path, target, values});
      }
    });

    // Cleanup subscription on unmount
    return () => subscription.unsubscribe();
  }, [formUse, orderTypeList, exchangeList]);

  const onSubmit: SubmitHandler<FormType> = useCallback(async (values, event) => {
    const nativeEvent = event?.nativeEvent as SubmitEvent;
    const submitter = nativeEvent?.submitter as { name?: string, value?: string } | null;

    const actionName = submitter?.name ?? "action";
    const actionValue = submitter?.value as string ?? "true";
    formUse.setValue(actionName as Path<OrderFormDto>, actionValue);

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
      if (formUse.formState.isValid) {
        setLoading(true);
        try {
          const requestBody = prepareDateProps(values,
            (value) => parseDate(value, t("~pattern.datetime"))?.toISOString());

          let response: OrderFormDto;
          if (requestBody.orderId) {
            response = await OrderFormService.updateOrder({ requestBody });
          }
          else {
            response = await OrderFormService.createOrder({ requestBody });
          }
          formUse.reset(response);

          if (response.orderId !== undefined) {
            const confirmation = await modalConfirm.value({title: t("~confirm.print.title"), content: t("~confirm.question")});
            if (confirmation.result?.confirmed) {
              print(response.orderId, t("~print.original") ?? "");
              print(response.orderId, "");
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
    else if (actionValue === "cancel") {
      const confirmation = await modalConfirm.value({title: t("~confirm.undo.title"), content: t("~confirm.question")});
      if (confirmation.result?.confirmed) {
        formUse.reset();
      }
    }
    else if (actionValue === "delete") {
      if ((values.selected ?? "*") !== "*") {
        const confirmation = await modalConfirm.value({title: t("~confirm.delete.title"), content: t("~confirm.question")});
        if (confirmation.result?.confirmed) {
          const index = Number(values.selected);
          for (let i = index + 1; i < orderEntries.fields.length; i++) {
            formUse.setValue(`orderEntries.${i}.entryRow`, i);
          }
          orderEntries.remove(index);
        }
      }
    }
    else if (actionValue === "print") {
      if (formUse.formState.isValid && values.orderId) {
        setLoading(true);
        try {
          print(values.orderId, t("~print.original") ?? "");
          print(values.orderId, "");
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
            { date: parseDate(formUse.getValues(value as Path<FormType>) as string, t("~pattern.datetime")) }
          );
          if (res.result) {
            formUse.setValue(value as Path<FormType>, formatDate(res.result.date, t("~pattern.datetime")));
            await formUse.trigger();
          }
        }
        else if (fn === "contact") {
          const res = await modalContact.value({ [target]: formUse.getValues(value as Path<FormType>) as string });
          if (res.result) {
            formUse.setValue((path || value) as Path<FormType>, res.result);
            if (path === "orderCustomer") {
              formUse.setValue("orderRcvd", res.result.contactResp);
            }
            await formUse.trigger();
          }
        }
        else if (fn === "location") {
          const res = await modalContact.value({ contactCode1: values.orderCustomer?.contactCode1 });
          if (res.result) {
            if (path === "orderCustomer") {
              formUse.setValue("orderCustomer.contactLocation", res.result.contactLocation);
            }
            await formUse.trigger();
          }
        }
        else if (fn === "contact.uic") {
          if (values.orderCustomer?.contactCode1) {
            const found = await RequestService.requestJson({api: "uic", params: values.orderCustomer?.contactCode1});
            if (found) {
              formUse.setValue("orderCustomer.contactName", found["contactName"]);
              formUse.setValue("orderCustomer.contactAddress", found["contactAddress"]);
              formUse.setValue("orderCustomer.contactCode2", found["contactCode2"]);
              await formUse.trigger();
            }
          }
        }
        else if (fn === "product") {
          const res = await modalProduct.value({ productName: formUse.getValues(value as Path<FormType>) as string });
          if (res.result) {
            formUse.setValue(`${(path || value)}.entryProduct.productId` as Path<FormType>, res.result.productId);
            formUse.setValue(`${(path || value)}.entryLabel` as Path<FormType>, res.result.productName);
            formUse.setValue(`${(path || value)}.entryUnits` as Path<FormType>, res.result.productUnits);
            formUse.setValue(`${(path || value)}.entryMeasure` as Path<FormType>, res.result.productMeasure);
            if (values.orderCcp?.endsWith(res.result.productCy ?? "")) {
              formUse.setValue(`${(path || value)}.entryPrice` as Path<FormType>, (res.result.productPrice ?? 0.0) * (values.orderRate ?? 1.0));
            }
            formUse.setValue(`${(path || value)}.entryAvailable` as Path<FormType>, res.result.productAvailable);
            await formUse.trigger();
          }
        }
      }
    }

    // console.log({ actionName, actionValue, values: values });
  }, [formUse, orderEntries, modalConfirm, modalCalendar,  modalContact, modalProduct]);

  const onError: SubmitErrorHandler<FormType> = useCallback(async (errors, event) => {
    await onSubmit(formUse.getValues(), event);
  }, [formUse, onSubmit]);

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
                            control={formUse.control}
                            rules={item.rules}
                            variant={item.variant ?? "label"}
                            name={item.name as Path<OrderFormDto>}
                            type={item.mode === "hidden" ? "hidden" : item.type}
                            prefix={t(item.label)}
                            suffix={t(item.suffix)}
                            disabled={item.mode === "disabled" || (!!item.name && disabled.includes(item.name))}
                            autoComplete="off"
                            formatter={formatters[item.type ?? "none"]}
                            pattern={item.type === "text" ? item.pattern : t(item.pattern)}
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
                  data={orderEntries.fields}
                  dataKey={"entryId"}
                  formatters={formatters}
                  layout={model.table}
                  selector={(entry, index) => <Controller
                    control={formUse.control}
                    name={"selected"}
                    render={({field}) => {
                      return (
                        <input
                          key={(index !== undefined ? index : "*") as Key}
                          name={field.name}
                          type={"radio"}
                          value={(index !== undefined ? index : "*").toString()}
                          defaultChecked={index === undefined}
                          className={index !== undefined ? "peer checkbox checkbox-lg" : "checkbox checkbox-lg"}/>
                      )
                    }}/>
                  }
                /*Table*/>
                  {(props) => {
                    return (
                      props.item.name
                        ? <Input<OrderFormDto>
                          key={props.item.name}
                          control={formUse.control}
                          rules={props.item.rules}
                          name={`orderEntries.${props.index}.${props.item.name}` as Path<OrderFormDto>}
                          type={props.item.mode === "hidden" ? "hidden" : props.item.type}
                          disabled={props.item.mode === "disabled"}
                          variant={"ghost"}
                          autoComplete="off"
                          formatter={formatters[props.item.type ?? "none"]}
                          pattern={props.item.type === "text" ? props.item.pattern : t(props.item.pattern)}
                          options={orderFormOptions[props.item.source ?? "none"]}
                          action={props.item.source}
                        />
                        : <span key={props.index}>{props.item.name ? `${props.entry[props.item.name as keyof OrderEntryDto]}` : ""}</span>
                    )
                  }}
                </Table>
                <button type={"submit"} className={"btn w-full"} name={"action"} value={"add"}>{t("~action.add")}</button>
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