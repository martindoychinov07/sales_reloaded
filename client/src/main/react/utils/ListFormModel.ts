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

import type { LayoutModel } from "./LayoutModel.ts";
import type { InputOptions } from "./Input.tsx";
import type { PageMetadata } from "../api/sales";
import type { SelectionType } from "./modal/useModal.tsx";

export type ListType<D> = {
  content?: D[];
  page?: PageMetadata;
}

export interface ListMetadata {
  page?: number;
  size?: number;
  sort?: string;
  direction?: "ASC" | "DESC";
  view?: string;
}

type FormAction =
  | "search"
  | "create"
  | "copy"
  | "edit"
  | "save"
  | "cancel"
  | "remove"
  | "export"
  | "confirm"
  | "close";

export type FormValue<F extends ListMetadata & SelectionType, D> = {
  args: F & ListMetadata & SelectionType;
  action?: FormAction | (string & {});
  selected: string[];
  disabled: string[];
  mode?: string;
  input?: D;
  inputId: Extract<keyof D, string>;
};

export type ListFormModel<F extends ListMetadata & SelectionType, D> = {
  action: {
    search: (args: F) => Promise<ListType<D>>;
    create?: (param: { requestBody: D }) => Promise<D>;
    save?:   (param: { id: number, requestBody: D }) => Promise<D>;
    remove?: (param: { id: number }) => Promise<void>;
  }
  form: FormValue<F, D>
  fields: {
    layout: LayoutModel<F>;
    options: { [key: string]: InputOptions<F> };
  }
  table: {
    layout: LayoutModel<D>;
    options: { [key: string]: InputOptions<D> };
    defaults?: (entry?: D) => D | Promise<D>;
  }
}

