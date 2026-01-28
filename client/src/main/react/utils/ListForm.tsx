import {useAsyncState} from "./async/useAsyncState.tsx";
import {AsyncFragment} from "./async/AsyncFragment.tsx";
import { type Key, type ReactNode, useCallback, useEffect, useMemo, useRef, useState } from "react";
import { Table } from "./Table.tsx";
import {useFormat} from "./useFormat.tsx";
import { type Path, type SubmitHandler, useForm } from "react-hook-form";
import {LayoutInput} from "./LayoutInput.tsx";
import type {
  FormValue,
  ListFormModel,
  ListMetadata,
  ListType
} from "./ListFormModel.ts";
import {formatDate, parseDate, prepareDateProps} from "./DateUtils.ts";
import type {ModalComponentProps} from "./modal/Modal.tsx";
import {useConfirm} from "./modal/useConfirm.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";
import {useCalendar} from "./modal/useCalendar.tsx";
import { copyTable, getSelection } from "./TableUtils.tsx";
import type { SelectionType } from "./modal/useModal.tsx";

export interface ListFormProps<F extends ListMetadata & SelectionType, D> {
  model: ListFormModel<F, D>;
}

interface ListModalFormProps<F extends ListMetadata & SelectionType, D> extends ModalComponentProps<D[], F> {
  rowClassName?: (entry?: D, index?: (number | undefined)) => (string | undefined);
  model: ListFormModel<F, D>;
  onAction?: (action: string, payload?: D[], path?: string) => Promise<string | undefined>;
  children?: ReactNode;
}

const groupsModal = ["args", "table", "modal"];
const groupsAction = ["args", "table", "action"];

function ListForm<F extends ListMetadata & SelectionType, D>(props: ListModalFormProps<F, D>) {
  const { t } = useI18n();
  const model = props.model;
  const ID = model.form.inputId;
  const [disabled, setDisabled] = useState<string[]>(model.form.disabled ?? []);
  const formatters = useFormat();
  const [groups, setGroups] = useState<string[]>(props.close ? groupsModal : groupsAction);
  const modalConfirm = useConfirm();
  const modalCalendar = useCalendar();
  const render = (props.close ? props.open !== false : true);
  const tableRef = useRef<HTMLTableElement | null>(null);

  // useEffect(() => {
  //   console.log("init", render, ID);
  // }, []);
  // console.log("render", render, ID);

  const tableContext = useMemo(() => (props.close ? "modal." : "") + (ID.replace(/Id$/, "") + ".")
    , [ID, props.close]);

  const args = useMemo(
    () => {
      return { ...(model.form.args ?? {}), ...props.args };
    },
    [model.form.args, props.args]
  );

  const search = useCallback((args: F): Promise<ListType<D>> => {
    // console.log("search", ID, { render })
    if (render) {
      const requestBody = prepareDateProps(args,
        (value) => parseDate(value, t("~format.datetime"))?.toISOString());

      return model.action.search(requestBody);
    }
    else {
      return new Promise(resolve => { resolve({}) });
    }
  }, [model.action.search]);
  const data = useAsyncState<ListType<D>, F>(search, args);

  const formUse = useForm<FormValue<F, D>>({
    defaultValues: {
      ...model.form,
      selected: [],
      args
    },
    mode: "onChange",
    shouldUnregister: false
  });

  const action = formUse.getValues("action"); // formUse.watch("action");
  // useEffect(() => {
  //   formUse.reset({ ...model.form, args });
  //   // formUse.setValue("args", { ...formUse.getValues("args"), ...args });
  // }, [formUse, args]); //

  const getTableLayout = useCallback((
    layout: typeof model.table.layout,
    rule?: string
  ) => {
    const pattern = rule?.slice(3);
    const regex = pattern ? new RegExp(pattern) : undefined;
    const items = layout.items?.map(item => {
      if (!regex) return { ...item };

      return item.type !== "hidden" && item.name
        ? { ...item, mode: regex.test(item.name) ? "hidden" : undefined }
        : { ...item };
    });

    return {
      ...layout,
      items,
    } as typeof model.table.layout;
  }, [model]);
  const [tableLayout, setTableLayout] = useState<typeof model.table.layout>(() => getTableLayout(model.table.layout, model.form.args.view));

  const onSubmit: SubmitHandler<typeof model.form> = useCallback(async (form, event) => {
    const nativeEvent = event?.nativeEvent as SubmitEvent;
    const submitter = nativeEvent?.submitter as {name?: string, value?: string} | null;
    const actionName = submitter?.name ?? "action";
    let actionValue = submitter?.value as string ?? form.action;
    const selected = form.selected ?? [];
    const selectedOne = form.selected?.at(-1);

    function findIndex(list: D[], id: string | undefined): number {
      if (id === undefined || !list || !list.length) {
        return -1;
      }
      return list.findIndex(item => String(item[ID]) === id);
    }

    function findAll(list: D[], selected: string[]): D[] {
      return selected
        .map(id => list.find(item => String(item[ID]) === id))
        .filter(Boolean) as D[]
    }

    if (actionValue) {
      const name = actionValue;
      const content = data.result?.content ?? [];

      if (props.onAction) {
        const found = findAll(content, selected);
        const action = await props.onAction(name, found);
        if (action) {
          actionValue = action;
        }
        else {
          return;
        }
      }
      if (actionValue === "search") {
        data.reload( {...form.args});
        formUse.setValue("selected", []);
        formUse.setValue("action", "search");
      }
      else if (actionValue === "create" || actionValue === "copy") {
        const found = findIndex(content, selectedOne) ?? content.length;
        const merged = [...content.slice(0, found + 1), { [ID]: 0 } as D, ...content.slice(found + 1)];
        data.update({ ...data.result, content: merged });
        formUse.setValue("selected", [...selected, "0"]);
        if (actionValue === "copy") {
          formUse.setValue("input", { ...content[found], [ID]: 0 });
        }
        else {
          formUse.setValue("input", { ...model.table.defaults?.(content[found]), [ID]: 0 } as D);
        }
        formUse.setValue("action", actionValue);
      }
      else if (actionValue === "edit") {
        const found = findIndex(content, selectedOne);
        if (found >= 0) {
          formUse.setValue("action", actionValue);
          formUse.setValue("input", content[found]);
        }
        else {
          return;
        }
      }
      else if (actionValue === "save") {
        const isValid = await formUse.trigger();
        if (isValid) {
          const entry = form.input;
          if (entry) {
            const found = findIndex(content, String(entry[ID] ?? selectedOne));
            if (found >= 0) {
              const requestBody = prepareDateProps(entry,
                (value) => parseDate(value, t("~format.datetime"))?.toISOString());

              try {
                if (entry[ID] && model.action.save) {
                  const id = Number(entry[ID]);
                  const updated = await model.action.save({ id, requestBody });
                  const merged = [...content.slice(0, found), updated, ...content.slice(found + 1)];
                  data.update({ ...data.result, content: merged });
                }
                else if (model.action.create) {
                  const created = await model.action.create({ requestBody })
                  const merged = [...content.slice(0, found), created, ...content.slice(found + 1)];
                  data.update({ ...data.result, content: merged });
                  formUse.setValue("selected", [`${created[ID]}`]);
                  formUse.setValue("input", undefined);
                }
                formUse.setValue("input", undefined);
                formUse.setValue("action", undefined);
              }
              catch (reason) {
                alert(JSON.stringify(reason))
              }
            }
          }
        }
      }
      else if (actionValue === "cancel") {
        const entry = form.input;
        if (String(entry?.[ID]) === "0") {
          const id = String(0);
          const found = findIndex(content, id);
          if (found >= 0) {
            const merged = [...content.slice(0, found), ...content.slice(found + 1)];
            formUse.setValue("selected", selected.filter(s => s !== id));
            data.update({ ...data.result, content: merged });
          }
        }
        formUse.setValue("input", undefined);
        formUse.setValue("action", actionValue);
      }
      else if (actionValue === "delete") {
        const confirmation = await modalConfirm.value({title: t("~confirm.delete.title"), content: t("~confirm.question")});
        if (confirmation.result?.confirmed) {
          for (let i = 0; i < selected.length; i++) {
            const id = Number(selected[i]);
            if (isFinite(id) && model.action.remove) {
              await model.action.remove({ id });
            }
          }
          const ids = new Set(selected);
          data.update({ ...data.result, content: data.result?.content?.filter(entry => !ids.has(String(entry[ID]))) });
          formUse.setValue("selected", []);
        }
        formUse.setValue("action", "search");
      }
      else if (actionValue === "export") {
        const confirmation = await modalConfirm.value({
          title: t("~confirm.export.title"),
          content: t("~confirm.question")
        });
        if (confirmation.result?.confirmed) {
          copyTable(tableRef, tableContext);
        }
      }
      else if (actionValue === "close") {
        props.close?.({ resolve: {}, reject: "code" })
        formUse.setValue("action", "search"); // TODO
      }
      else if (actionValue === "confirm") {
        const found = findAll(content, selected);
        props.close?.({ resolve: { args: form.args, action: actionValue, result: found } })
        formUse.setValue("action", "search"); // TODO
      }
      else {
        const mi = actionValue.indexOf(":");
        const ti = Math.max(mi, actionValue.lastIndexOf("."));
        if (mi >= 0 && ti > 0) {
          const fn = actionValue.slice(0, mi);
          // const path = actionValue.slice(mi + 1, ti);
          const value = actionValue.slice(mi + 1);
          // const target = actionValue.slice(ti + 1);

          // console.log({fn, path, value, target});

          if (fn === "calendar") {
            const res = await modalCalendar.value(
              {date: parseDate(formUse.getValues(value as Path<typeof model.form>) as string, t("~format.datetime"))}
            );
            if (res.result) {
              formUse.setValue(value as Path<typeof model.form>, formatDate(res.result.date, t("~format.datetime")));
            }
          }
          else {
            if (props.onAction) {
              const found = findAll(content, selected);
              const res = await props.onAction(fn, found, value);
              if (res) {
                formUse.setValue(value as Path<typeof model.form>, res);
              }
            }
          }
        }
      }

      const current = model.fields.layout.items
        ?.find(item => item.name === name);

      if (current) {
        setDisabled(prev => {
          const set = new Set(prev);

          current.disable?.forEach(set.add, set);
          current.enable?.forEach(set.delete, set);

          if (
            prev.length === set.size &&
            prev.every(v => set.has(v))
          ) {
            return prev;
          }

          return [...set];
        });
      }
    }
  }, [formUse, model, data, props]);

  const submit = useCallback((action?: typeof model.form.action) => {
    if (action) {
      return formUse.handleSubmit(data => onSubmit?.({ ...data, action: action }))();
    }
    else {
      return formUse.handleSubmit(onSubmit)();
    }
  }, [formUse, model, onSubmit]);

  useEffect(() => {
    const subscription = formUse.watch((values, { name, type }) => {
      if (type === "change" && name) {
        switch (name) {
          case "args.page":
          case "args.size":
          case "args.sort":
          case "args.direction":
            submit("search");
            break;
          case "selected":
            break;
          case "mode":
            setGroups(groups => values.mode
              ? [...groups.slice(0, -1), "action", ...groups.slice(-1)]
              : groups.filter(g => g !== "action")
            );
            break;
          case "args.view":
            setTableLayout(getTableLayout(tableLayout, values?.args?.view));
            break;
          default:
            if (name.startsWith("args.")) {
              // formUse.setValue("args.page", 0);
            }
            break;
        }
      }
    });

    // Cleanup subscription on unmount
    return () => subscription.unsubscribe();
  }, [formUse, getTableLayout, submit, tableLayout]);

  const edit = !props.onAction && action !== undefined && ["edit", "create", "copy"].includes(action);
  const cols= model.fields.layout.columns ?? 1;

  const handleSelect = (data?: D[], index?: number, ctrlKey?: boolean, shiftKey?: boolean) => {
    if (!disabled.includes("edit")) {
      const selection = args.selection ?? (props.close ? "one" : "many");
      const selected = formUse.getValues("selected") || [];
      const res: string[] = getSelection(selection, selected, ID as string, data, index, ctrlKey, shiftKey);
      formUse.setValue("selected", res, {
        shouldDirty: false,
        shouldTouch: false,
        shouldValidate: false,
      });
    }
  };

  const res = (<>
    <form key={"form"} onSubmit={formUse.handleSubmit(onSubmit)} onReset={() => formUse.reset()} className="flex-1 flex flex-col overflow-hidden">
      {groups.map(group => {
        if (group !== "table") return (
          <div key={group}>
            <div key="content" className={`grid lg:grid-cols-${cols} gap-1 p-1`}>
              {model.fields.layout.items
                ?.filter(item => item.group === group)
                .map((item, index) => {
                  const optionsKey = item.source as keyof typeof model.fields.options;
                  const options = optionsKey ? model.fields.options[optionsKey] : undefined;
                  return (
                    <LayoutInput
                      key={item.name ?? index}
                      form={formUse}
                      variant={item.variant ?? "title"}
                      item={item}
                      index={index}
                      formatter={formatters[item.type ?? "none"]}
                      options={options}
                      disabled={!!item.name && disabled.includes(item.name)}
                    />
                  )
                })}
            </div>
          </div>
        )
        if (group === "table") {
          const selected = formUse.getValues("selected") ?? [];
          const selectedOne = selected.at(-1);
          return (
            <div key={group} className={"flex-1 overflow-y-auto"}>
              <AsyncFragment
                asyncState={data}
                onError={(error, children) => {
                  return <b style={{whiteSpace: "pre"}}>{JSON.stringify(error, null, "  ")}</b>;
                }}
              /*AsyncFragment*/>
                <Table
                  onTableRef={el => (tableRef.current = el)}
                  context={tableContext}
                  data={data.result?.content ?? []}
                  dataKey={ID}
                  formatters={formatters}
                  layout={tableLayout}
                  pager={(position) => {
                    const size = data.result?.page?.size;
                    const page = data.result?.page?.number;
                    const pages = data.result?.page?.totalPages ?? 0;
                    const count = data.result?.page?.totalElements ?? 0;
                    if (page !== undefined && size !== undefined && pages > 0) {
                      if (position === "next") {
                        return <div className={"pl-8 cursor-pointer"} onMouseEnter={(e) => {
                          if (pages > 1) {
                            const values = formUse.getValues();
                            formUse.reset(
                              {
                                ...values,
                                args: {
                                  ...values.args,
                                  size: Number(values.args.size ?? 0) + 100,
                                }
                              }
                            );
                            submit("search");
                          }
                        }}>{Math.min((page + 1) * size, count)} / {count} ({Math.round(100 * Math.min((page + 1) * size, count) / (count > 0 ? count : 1))}%)</div>
                      }
                    }
                    else {
                      return null;
                    }
                  }}
                  selector={(data, index) => {
                    const key = index !== undefined ? data?.[index]?.[ID] : "*";
                    if (key === null || key === undefined) return null;
                    const id = String(key);
                    return <input
                      key={id as Key}
                      {...formUse.register("selected", {
                        onChange: (e) => {
                          if (e.target.value === "*") {
                            handleSelect(data, undefined);
                          }
                        }
                      })}
                      onDoubleClick={() => handleSelect(data, 0)}
                      type={"checkbox"}
                      value={id}
                      className={id !== "*" ? "peer checkbox checkbox-lg bg-base-100" : "checkbox checkbox-lg bg-base-100"}
                      disabled={edit}
                    />
                  }}
                  onSort={(name) => {
                    if (name) {
                      const values = formUse.getValues();
                      formUse.reset(
                        {
                          ...values,
                          args: {
                            ...values.args,
                              sort: name.toString(),
                              direction: (name === values.args.sort) ? (values.args.direction === "ASC" ? "DESC" : "ASC") : "ASC",
                          }
                        }
                      );
                      submit("search");
                    }
                  }}
                  rowClassName={props.rowClassName}
                  onClick={handleSelect}
                  onDoubleClick={(entry, index) => {
                    if (groups.includes("action")) {
                      if (!disabled.includes("edit")) {
                        submit("edit");
                      }
                    }
                    else {
                      const value = String(entry?.[ID] ?? "*");
                      formUse.setValue("selected", [value], {
                        shouldDirty: false,
                        shouldTouch: false,
                        shouldValidate: false,
                      });
                      submit("confirm");
                    }
                  }}
                  /*Table*/>
                  {(props) => {
                    const optionsKey = props.item.source as keyof typeof model.table.options;
                    const options = optionsKey ? model.table.options[optionsKey] : undefined;
                    if (edit && String(props.entry[ID]) === selectedOne) {
                      return (
                        <LayoutInput
                          key={props.item.name ?? props.index}
                          form={formUse}
                          item={props.item}
                          index={props.index}
                          formatter={formatters[props.item.type ?? "none"]}
                          options={options}
                          disabled={props.item.mode === "disabled" || (!!props.item.name && disabled.includes(props.item.name))}
                        />
                      )
                    }
                    let value = props.item.name ? props.entry[props.item.name as keyof typeof props.entry]?.toString() : "";
                    if (props.formatter && value) {
                      value = props.formatter(value,  props.item.type === "text" ? props.item.format : t(props.item.format)) ?? value;
                    }

                    return (
                      <div key={props.item.name ?? props.index} className={props.item.type === "number" ? "text-right" : undefined}>{value}</div>
                    )
                  }}
                </Table>
              </AsyncFragment>
            </div>
          )
        }
      })}
    </form>
    {modalConfirm.component}
    {modalCalendar.component}
    {props.children}
  </>);
  // console.timeEnd(ID.toString());
  return res;
}

export default ListForm