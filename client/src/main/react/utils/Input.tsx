import {type Path, type SubmitHandler, type UseControllerProps} from "react-hook-form";
import {type HTMLInputAutoCompleteAttribute, type HTMLInputTypeAttribute, type ReactNode} from "react";
import {InputSelect} from "./InputSelect.tsx";
import {InputButton} from "./InputButton.tsx";
import {InputCommon} from "./InputCommon.tsx";
import {InputToggle} from "./InputToggle.tsx";
import {ModalIcon} from "./modal/ModalIcon.tsx";
import type { ItemRules } from "./LayoutModel.ts";

export type InputFormatter = (value: string, pattern?: string | null| undefined) => string | null | undefined;
export type InputOption = { label: string, value?: string | number, disabled?: boolean };
export type InputOptions<T> = (entry?: T | unknown) => InputOption[];

export interface InputProps<T extends object> {
  type: HTMLInputTypeAttribute | "select" | undefined;
  prefix?: ReactNode;
  suffix?: ReactNode;
  placeholder?: string;
  variant?: "bordered" | "ghost" | "label" | "compact" | "float";
  autoComplete?: HTMLInputAutoCompleteAttribute;
  formatter?: InputFormatter;
  pattern?: string | null;
  entry?: T;
  options?: InputOptions<T>;
  action?: string;
  onSubmit?: SubmitHandler<T>;
  rules?: ItemRules;
}

export interface ClassNameRule {
  addIf: boolean;
  add: string;
  orElse?: string;
}

export function Input<T extends object>(props: UseControllerProps<T> & InputProps<T>) {
  switch (props.type) {
    case "select":
      return InputSelect(props);

    case "button":
    case "submit":
    case "reset":
    case "confirm":
      return InputButton(props);

    case "toggle":
      return InputToggle(props);

    case "date":
    case "datetime":
    case "datetime-local":
    case "dialog":
      return InputCommon({...props, type: "text", suffix: InputButton({
        ...props,
        prefix: <ModalIcon />,
        type: "button",
        name: "action" as Path<T>,
        action: [props.action, props.name].filter(s => s).join(":"),
        variant: "ghost",
      })})
  }

  return InputCommon(props);
}