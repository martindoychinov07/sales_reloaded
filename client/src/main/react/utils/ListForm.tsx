import {useAsyncState} from "./async/useAsyncState.tsx";
import {AsyncFragment} from "./async/AsyncFragment.tsx";
import {type Key, useCallback, useEffect, useState} from "react";
import {Table} from "./Table.tsx";
import {useFormat} from "./useFormat.tsx";
import {type Path, type SubmitHandler, useForm} from "react-hook-form";
import {LayoutInput} from "./LayoutInput.tsx";
import type {ListFormModel, ListMetadata, ListType} from "./ListFormModel.ts";
import {formatDate, parseDate, prepareDateProps} from "./DateUtils.ts";
import type {ModalComponentProps} from "./modal/Modal.tsx";
import {useConfirm} from "./modal/useConfirm.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";
import {useCalendar} from "./modal/useCalendar.tsx";

export interface ListFormProps<F extends ListMetadata, D> {
  model: ListFormModel<F, D>;
}

interface ListModalFormProps<F extends ListMetadata, D> extends ModalComponentProps<D, F> {
  model: ListFormModel<F, D>;
  onAction?: (action: string, payload?: D) => Promise<boolean>;
}

export function ListForm<F extends ListMetadata, D>(props: ListModalFormProps<F, D>) {
  const { t } = useI18n();
  const model = props.model;
  const ID = model.form.inputId;
  const [disabled, setDisabled] = useState<string[]>(model.form.disabled ?? []);
  const data = useAsyncState<ListType<D>, F>(model.action.search, { ...model.form.args, ...model.form.paging, ...props.args } as F);
  const formatters = useFormat();
  const formUse = useForm<typeof model.form>({ defaultValues: { ...model.form, action: "search" } });
  const selected = Number(formUse.getValues("selected"));
  const action = formUse.getValues("action");
  const [groups, setGroups] = useState<string[]>([]);
  const modalConfirm = useConfirm();
  const modalCalendar = useCalendar();

  useEffect(() => {
    setGroups(["args", "paging", "table", props.args ? "modal" : "action"]);
  }, [props.args]);

  const submit = (action?: typeof model.form.action) => {
    if (action) {
      return formUse.handleSubmit(data => onSubmit?.({ ...data, action: action }))();
    }
    else {
      return formUse.handleSubmit(onSubmit)();
    }
  }

  const onSubmit: SubmitHandler<typeof model.form> = useCallback(async (form, event) => {
    const nativeEvent = event?.nativeEvent as SubmitEvent;
    const submitter = nativeEvent?.submitter as {name?: string, value?: string} | null;
    const actionName = submitter?.name ?? "action";
    const actionValue = submitter?.value as string ?? form.action;
    const selected = Number(form.selected);

    if (actionValue) {
      const name = actionValue;
      formUse.setValue(actionName as Path<typeof model.form>, actionValue);
      const current = model.fields.layout.items
        ?.find(item => item.name === name);
      if (current) {
        setDisabled(disabled
          .concat(current.disable ?? [])
          .filter(name => !current.enable?.includes(name))
        );
      }

      const content = data.result?.content ?? [];
      if (props.onAction) {
        const found = content.find(item => item[ID] === selected);
        if (await props.onAction(name, found)) return;
      }

      if (actionValue === "search") {
        formUse.setValue("selected", "*");
        const requestBody = prepareDateProps({ ...form.args, ...form.paging },
          (value) => parseDate(value, t("~pattern.datetime"))?.toISOString());

        data.reload( requestBody as F);
        // formUse.setValue("action", "search");
      }
      else if (actionValue === "create" || actionValue === "copy") {
        const found = content.findIndex(item => item[ID] === selected) ?? content.length;
        const merged = [...content.slice(0, found + 1), { [ID]: 0 } as D, ...content.slice(found + 1)];
        data.update({ ...data.result, content: merged });
        formUse.setValue("selected", "0");
        if (actionValue === "copy") {
          formUse.setValue("input", { ...content[found], [ID]: 0 });
        }
        else {
          formUse.setValue("input", { [ID]: 0 } as D);
        }
      }
      else if (actionValue === "edit") {
        formUse.setValue("input", content.find(item => item[ID] === selected));
      }
      else if (actionValue === "save") {
        const entry = form.input;
        if (entry) {
          const found = content.findIndex(item => item[ID] === (entry[ID] ?? selected));
          if (found >= 0) {
            const requestBody = prepareDateProps(entry,
              (value) => parseDate(value, t("~pattern.datetime"))?.toISOString());

            if (entry[ID] && model.action.save) {
              model.action.save({ requestBody })
                .then((updated: D) => {
                  const merged = [...content.slice(0, found), updated, ...content.slice(found + 1)];
                  data.update({ ...data.result, content: merged });
                  formUse.setValue("selected", `${updated[ID]}`);
                  formUse.setValue("input", undefined);
                }).catch(reason => alert(JSON.stringify(reason)))
            }
            else if (model.action.create) {
              model.action.create({ requestBody })
                .then((created: D) => {
                  const merged = [...content.slice(0, found), created, ...content.slice(found + 1)];
                  data.update({ ...data.result, content: merged });
                  formUse.setValue("selected", `${created[ID]}`);
                  formUse.setValue("input", undefined);
                }).catch(reason => {
                  alert(JSON.stringify(reason));
                });
            }
          }
        }
        formUse.setValue("action", undefined);
      }
      else if (actionValue === "cancel") {
        const entry = form.input;
        if (!entry?.[ID]) {
          const found = content.findIndex(item => item[ID] === 0);
          if (found >= 0) {
            const merged = [...content.slice(0, found), ...content.slice(found + 1)];
            formUse.setValue("selected", `${merged[Math.min(found - 1, content.length - 1)][ID]}`);
            data.update({ ...data.result, content: merged });
          }
        }
        formUse.setValue("input", undefined);
        formUse.setValue("action", undefined);
      }
      else if (actionValue === "delete") {
        const confirmation = await modalConfirm.value({title: t("~confirm.delete.title"), content: t("~confirm.question")});
        if (confirmation.result?.confirmed) {
          const found = content.findIndex(item => item[ID] === selected);
          if (found >= 0 && model.action.remove) {
            model.action.remove({ requestBody: selected }).then(() => {
              const merged = [...content.slice(0, found), ...content.slice(found + 1)];
              formUse.setValue("selected", `${merged[Math.min(found - 1, content.length - 1)]?.[ID]}`);
              data.update({ ...data.result, content: merged });
            });
          }
        }
        formUse.setValue("action", undefined);
      }
      else if (actionValue === "close") {
        props.close({ resolve: {}, reject: "code" })
        formUse.setValue("action", "search"); // TODO
      }
      else if (actionValue === "confirm") {
        const found = content.find(item => item[ID] === selected);
        props.close({ resolve: { action: actionValue, result: found } })
        formUse.setValue("action", "search"); // TODO
      }
      else {
        const mi = actionValue.indexOf(":");
        const ti = Math.max(mi, actionValue.lastIndexOf("."));
        if (mi >= 0 && ti > 0) {
          const fn = actionValue.slice(0, mi);
          const path = actionValue.slice(mi + 1, ti);
          const value = actionValue.slice(mi + 1);
          const target = actionValue.slice(ti + 1);

          // console.log({fn, path, value, target});

          if (fn === "calendar") {
            const res = await modalCalendar.value(
              {date: parseDate(formUse.getValues(value as Path<typeof model.form>) as string, t("~pattern.datetime"))}
            );
            if (res.result) {
              formUse.setValue(value as Path<typeof model.form>, formatDate(res.result.date, t("~pattern.datetime")));
            }
          }
        }
      }
    }
  }, [formUse, model, data, props, disabled, ID, modalConfirm, modalCalendar]);

  useEffect(() => {
    formUse.setValue("args" as Path<typeof model.form>,props.args);
    // data.reload({ ...model.form.args, ...model.form.paging, ...props.args, ...formUse.getValues().args });
    submit();
  }, [props.args]);

  useEffect(() => {
    // Subscribe to all field changes
    const subscription = formUse.watch((values, {name, type}) => {
      if (type === "change" && name) {
        switch (name) {
          case "paging.page":
          case "paging.size":
          case "paging.sort":
          case "paging.direction":
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
          default:
            if (name.startsWith("args.")) {
              formUse.setValue("paging.page", 0);
            }
            break;
        }
      }
    });

    // Cleanup subscription on unmount
    return () => subscription.unsubscribe();
  }, [formUse]);

  const edit = !props.onAction && action !== undefined && ["edit", "create", "copy"].includes(action);
  const cols= model.fields.layout.columns ?? 1;

  return (<>
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
                      control={formUse.control}
                      onSubmit={onSubmit}
                      variant={"label"}
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
          return (
            <div key={group} className={"flex-1 overflow-y-auto"}>
              <AsyncFragment
                asyncState={data}
                onError={(error, children) => {
                  return <b style={{whiteSpace: "pre"}}>{JSON.stringify(error, null, "  ")}</b>;
                }}
              /*AsyncFragment*/>
                <Table
                  data={data.result?.content ?? []}
                  dataKey={ID}
                  formatters={formatters}
                  layout={model.table.layout}
                  selector={(entry) => <input
                    key={(entry?.[ID] ?? "*") as Key}
                    {...formUse.register("selected")}
                    type={"radio"}
                    value={(entry?.[ID] ?? "*").toString()}
                    defaultChecked={entry?.[ID] === undefined ? true : undefined}
                    className={entry?.[ID] !== undefined ? "peer checkbox checkbox-lg" : "checkbox checkbox-lg"}
                    disabled={edit}
                  />}
                  onSort={name => {
                    if (name) {
                      const value = formUse.getValues();
                      formUse.setValue("paging.sort", name.toString());
                      formUse.setValue("paging.direction", (name === value?.paging?.sort && value?.paging?.direction === "ASC") ? "DESC" : "ASC");
                      onSubmit(formUse.getValues());
                    }
                  }}
                  onClick={(entry, index) => {
                    if (!disabled.includes("edit")) {
                      formUse.setValue("selected", (entry?.[ID] ?? "*").toString())
                    }
                  }}
                  onDoubleClick={(entry, index) => {
                    if (groups.includes("action")) {
                      if (!disabled.includes("edit")) {
                        submit("edit");
                      }
                    }
                    else {
                      submit("confirm");
                    }
                  }}
                  /*Table*/>
                  {(props) => {
                    const optionsKey = props.item.source as keyof typeof model.fields.options;
                    const options = optionsKey ? model.table.options[optionsKey] : undefined;
                    if (edit && props.entry[ID] === selected) {
                      return (
                        <LayoutInput
                          key={props.item.name ?? props.index}
                          control={formUse.control}
                          onSubmit={onSubmit}
                          item={props.item}
                          index={props.index}
                          formatter={formatters[props.item.type ?? "none"]}
                          options={options}
                          disabled={!!props.item.name && disabled.includes(props.item.name)}
                        />
                      )
                    }
                    let value = props.item.name ? props.entry[props.item.name as keyof typeof props.entry]?.toString() : "";
                    if (props.formatter && value) {
                      value = props.formatter(value,  props.item.type === "text" ? props.item.pattern : t(props.item.pattern)) ?? value;
                    }
                    return (
                      <span key={props.item.name ?? props.index} className={props.item.type === "number" ? "text-right" : undefined}>{value}</span>
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
  </>)
}