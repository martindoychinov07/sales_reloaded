import {Controller, type UseControllerProps} from "react-hook-form";
import type {ClassNameRule, InputProps} from "./Input.tsx";
import {toClassName} from "./useFormat.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";
import type { HTMLAttributes } from "react";

export function InputSelect<T extends object>(props: UseControllerProps<T> & InputProps<T>) {
  const { t } = useI18n();
  let prefix = props.prefix;
  if (props.variant !== "label") {
    prefix = undefined;
  }

  return (
    <Controller
      control={props.control}
      name={props.name}
      rules={props.rules}
      render={({field, fieldState}) => {
        const cn: ClassNameRule[] = [
          { addIf: true, add: "w-full" },
          { addIf: true, add: "select select-sm" },
          { addIf: props.variant === "ghost", add: "select-ghost" },
          { addIf: fieldState.isTouched, add: "field-state-touched" },
          { addIf: fieldState.isDirty, add: "field-state-dirty" },
          { addIf: fieldState.invalid, add: "field-state-invalid" },
        ];

        let dataTooltip: HTMLAttributes<unknown> = {};
        if (fieldState.invalid) {
          let message: string;
          switch (fieldState.error?.type) {
            case "required":
              message = "~validation.required"
              break;
            default:
              message = fieldState.error?.message ?? "~validation.unknown";
          }
          dataTooltip = {
            "data-tooltip-id": "tooltip",
            "data-tooltip-content": t(message),
            "data-tooltip-place": "bottom-start"
          }
        }

        return (
          <label key="label" className={toClassName(cn)}>
            {prefix && <span key="prefix" className="label" {...dataTooltip}>{prefix}</span>}
            <select
              key="input"
              {...field}
              value={field.value ?? ""}
              disabled={props.disabled}
              className={"field-input"}
              autoComplete={props.autoComplete}>
              {
                props.options?.(props.entry)?.map((opt, index) =>
                  <option key={opt.value ?? index} value={opt.value} disabled={opt.disabled}>{t(opt.label)}</option>)
              }
            </select>
          </label>
        )
      }}
    />
  );
}