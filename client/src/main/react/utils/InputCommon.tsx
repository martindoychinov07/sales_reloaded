import {Controller, type UseControllerProps} from "react-hook-form";
import type {ClassNameRule, InputProps} from "./Input.tsx";
import {toClassName} from "./useFormat.tsx";
import { type HTMLAttributes, useRef } from "react";
import type {KeyCode} from "./KeyCode.ts";
import { useI18n } from "../context/i18n/useI18n.tsx";

export function InputCommon<T extends object>(props: UseControllerProps<T> & InputProps<T>) {
  const { t } = useI18n();
  const focused = useRef(false);
  let prefix = props.prefix;
  if (!props.variant || !["label", "compact", "float"].includes(props.variant)) {
    prefix = undefined;
  }

  return (
    <Controller
      control={props.control}
      name={props.name}
      rules={props.rules}
      render={({field, fieldState}) => {
        const cn: ClassNameRule[] = [
          { addIf: true, add: "w-full static" },
          { addIf: ["button", "submit", "reset", "toggle"].includes(props.type as string), add: "btn btn-sm", orElse: "input input-sm" },
          { addIf: !!props.disabled && ["button", "submit", "reset"].includes(props.type as string), add: "btn-disabled" },
          { addIf: props.type === "hidden", add: "hidden" },
          { addIf: props.variant === "float", add: "floating-label" },
          { addIf: props.variant === "ghost", add: "input-ghost" },
          { addIf: props.variant === "compact", add: "input-compact" },
          { addIf: !!props.action, add: "input-action" },
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

        return (<>
          <label key="label" className={toClassName(cn)}>
            {prefix && <span key={"prefix"} className={"label"} {...dataTooltip}>{prefix}</span>}
            <input
              key="input"
              {...field}
              type={props.type}
              value={((!focused.current && props.formatter && props.pattern) ? (props.formatter(field.value, props.pattern) ?? field.value) : field.value) ?? ""}
              disabled={props.disabled}
              className={"field-input"}
              autoComplete={props.autoComplete}
              onFocus={() => focused.current = true}
              onBlur={(e) => {
                if (props.formatter && props.pattern) {
                  const formatted = props.formatter(field.value, props.pattern);
                  if (formatted !== undefined && formatted !== null) {
                    e.target.value = formatted;
                  }
                }
                focused.current = false;
              }}
              onKeyDown={(e) => {
                if ((["ArrowUp", "ArrowDown"] as KeyCode[]).includes(e.code as KeyCode)) { e.preventDefault(); }
              }}
            />
            {props.suffix && <span key="suffix" className="label">{props.suffix}</span>}
          </label>
        </>)
      }}
    />
  )
}