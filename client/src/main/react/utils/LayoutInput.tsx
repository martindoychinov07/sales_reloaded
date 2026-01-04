import {Input, type InputFormatter, type InputOptions, type InputProps} from "./Input.tsx";
import type {LayoutModelItem} from "./LayoutModel.ts";
import type {Control, Path, SubmitHandler} from "react-hook-form";
import {useI18n} from "../context/i18n/useI18n.tsx";

interface LayoutInputProps<T extends object> {
  control: Control<T>;
  onSubmit: SubmitHandler<T>;
  variant?: InputProps<T>["variant"];
  item: LayoutModelItem<T>;
  name?: Path<T> | string;
  index: number;
  disabled?: boolean;
  formatter?: InputFormatter;
  options?: InputOptions<T>;
}

export function LayoutInput<T extends object>(props: LayoutInputProps<T>) {
  const { t } = useI18n();
  let name: string | undefined;
  switch (props.item.type) {
    case "button":
    case "submit":
    case "reset":
    case "toggle":
    case "confirm":
      name = props.name ?? props.item.name;
      break;
    default:
      name = (props.name ?? [props.item.group, props.item.name].filter(p => p).join("."));
  }

  return (
    <div key={props.item.name ?? props.index} className={`col-span-${Math.min(props.item.span ?? 1, 2)} lg:col-span-${props.item.span ?? 1}`}>
      {
        props.item.name
          ? <Input<T>
            control={props.control}
            onSubmit={props.onSubmit}
            key={name}
            name={name as Path<T>}
            type={props.item.mode === "hidden" ? "hidden" : props.item.type}
            prefix={t(props.item.label)}
            disabled={props.disabled ?? props.item.mode === "disabled"}
            autoComplete="off"
            formatter={props.formatter}
            pattern={props.item.type === "text" ? undefined : t(props.item.pattern)}
            options={props.options}
            action={props.item.source}
            variant={props.variant}
          />
          : <>{props.item.label}</>
      }
    </div>
  )
}