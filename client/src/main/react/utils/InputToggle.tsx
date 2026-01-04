import {Controller, type UseControllerProps} from "react-hook-form";
import type {ClassNameRule, InputProps} from "./Input.tsx";
import {toClassName} from "./useFormat.tsx";

export function InputToggle<T extends object>(props: UseControllerProps<T> & InputProps<T>) {
  return (
    <Controller
      control={props.control}
      name={props.name}
      render={({field}) => {
        const cn: ClassNameRule[] = [
          { addIf: true, add: "w-full btn btn-sm" },
          { addIf: !!props.disabled, add: "btn-disabled" },
          { addIf: props.variant === "ghost", add: "btn-ghost" },
        ];
        return (
          <input
            key="input"
            {...field}
            type={"checkbox"}
            name={props.name}
            value={props.action ?? field.value ?? "true"}
            disabled={props.disabled}
            className={toClassName(cn)}
            aria-label={props.prefix?.toString()}
          />
        )
      }}
    />
  );
}