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

import {
  type Path,
  type UseFormReturn
} from "react-hook-form";
import { type HTMLInputAutoCompleteAttribute, type HTMLInputTypeAttribute, type ReactNode } from "react";
import { InputSelect } from "./InputSelect.tsx";
import { InputButton } from "./InputButton.tsx";
import InputCommon from "./InputCommon.tsx";
import { InputToggle } from "./InputToggle.tsx";
import { ModalIcon } from "./modal/ModalIcon.tsx";
import type { ItemRules } from "./LayoutModel.ts";

export type InputFormatter = (value: string, format?: string | null | undefined, normalize?: boolean) => string | null | undefined;
export type InputOption = { label: string, value?: string | number, disabled?: boolean };
export type InputOptions<T> = (entry?: T | unknown) => InputOption[] | Promise<InputOption[]>;

export interface InputProps<T extends object> {
  form: UseFormReturn,
  type: HTMLInputTypeAttribute | "select" | undefined,
  name: string,
  prefix?: ReactNode,
  suffix?: ReactNode,
  placeholder?: string,
  variant?: "bordered" | "ghost" | "label" | "compact" | "title",
  autoComplete?: HTMLInputAutoCompleteAttribute,
  formatter?: InputFormatter,
  format?: string | null,
  entry?: T,
  options?: InputOptions<T>,
  action?: string,
  rules?: ItemRules,
  disabled?: boolean
}

export interface ClassNameRule {
  addIf: boolean;
  add: string;
  orElse?: string;
}

export function Input<T extends object>(props: InputProps<T>) {
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
      return InputCommon({
        ...props,
        type: "text",
        suffix: InputButton({
          ...props,
          prefix: <ModalIcon/>,
          type: "button",
          name: "action" as Path<T>,
          action: [props.action, props.name].filter(s => s).join(":"),
          variant: "ghost",
        })
      })
  }

  return InputCommon(props);
}