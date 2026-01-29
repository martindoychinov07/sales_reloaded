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
import type { Path } from "react-hook-form";

export function InputToggle<T extends object>(props: InputProps<T>) {
  const { register } = props.form;

  const cn: ClassNameRule[] = [
    { addIf: true, add: "w-full btn btn-sm" },
    { addIf: !!props.disabled, add: "btn-disabled" },
    { addIf: props.variant === "ghost", add: "btn-ghost" },
  ];

  return (
    <input
      {...register(props.name as Path<T>)}
      type="checkbox"
      aria-label={props.prefix?.toString()}
      disabled={props.disabled}
      className={toClassName(cn)}
      value={props.action ?? "true"}
    />
  );
}
