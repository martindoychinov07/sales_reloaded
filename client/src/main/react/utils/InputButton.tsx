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

import type {ClassNameRule, InputProps} from "./Input.tsx";
import {toClassName} from "./useFormat.tsx";

export function InputButton<T extends object>(props: InputProps<T>) {
  // const form = props.form;
  // const { register } = form;

  let type = props.type;
  if (props.type === "button" || props.type === "confirm") {
    type = "submit";
  }

  const actionValue =
    props.action ??
    props.name ??
    "true";

  const cn: ClassNameRule[] = [
    { addIf: true, add: "w-full btn btn-sm btn-primary" },
    { addIf: !!props.disabled, add: "btn-disabled" },
    { addIf: props.variant === "ghost", add: "btn-ghost" },
    { addIf: props.variant === "title", add: "btn-title" },
  ];

  return (
    <button
      type={type as "button" | "submit" | "reset"}
      name={"action"}
      value={actionValue}
      disabled={props.disabled}
      className={toClassName(cn)}
    >
      {props.prefix}
    </button>
  );
}
