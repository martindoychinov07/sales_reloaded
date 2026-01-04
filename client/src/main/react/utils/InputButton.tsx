import {Controller, type Path, type UseControllerProps} from "react-hook-form";
import type {ClassNameRule, InputProps} from "./Input.tsx";
import {toClassName} from "./useFormat.tsx";

export function InputButton<T extends object>(props: UseControllerProps<T> & InputProps<T>) {
  let type = props.type;
  if (props.type === "button" || props.type === "confirm") {
    type = "submit";
  }

  return (
    <Controller
      control={props.control}
      name={"action" as Path<T>}
      render={({field}) => {
        const cn: ClassNameRule[] = [
          { addIf: true, add: "w-full btn btn-sm btn-primary" },
          { addIf: !!props.disabled, add: "btn-disabled" },
          { addIf: props.variant === "ghost", add: "btn-ghost" },
        ];
        return (
          <button
            key="input"
            {...field}
            value={props.action ?? props.name ?? field.value ?? "true"}
            type={type as "button" | "submit" | "reset"}
            disabled={props.disabled}
            className={toClassName(cn)}
            onClick={props.type === "confirm" ? ((e) => {
              if (confirm("Do you confirm?") && props.onSubmit) {
                props.control?.handleSubmit(data => props.onSubmit?.({ ...data, action: props.action ?? props.name ?? field.value }, e))();
              }
            }) : undefined}
          /*button*/>
            {props.prefix}
          </button>
        )
      }}
    />
  );
}