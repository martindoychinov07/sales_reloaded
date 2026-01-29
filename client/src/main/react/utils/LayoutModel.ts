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

import type { Path, ValidationRule } from "react-hook-form";

export const MetaLayoutVariants = ["none", "left", "inner", "top", "table"] as const;
export type MetaLayoutVariant = typeof MetaLayoutVariants[number];

export const MetaLayoutItemTypes = ["open", "more", "hidden", "text", "number", "select", "radio", "checkbox", "dialog", "datetime", "password", "search", "button", "submit", "reset", "toggle", "confirm"] as const;
export type MetaLayoutItemType = typeof MetaLayoutItemTypes[number];

export interface LayoutModel<T> {
  // group?: string;
  items?: LayoutModelItem<T>[];
  columns?: number;
  span?: number;
  variant?: MetaLayoutVariant | string;
}

export type ItemRules = {
  required?: boolean | string;
  min?: number;
  max?: number;
  minLength?: number;
  maxLength?: number;
  pattern?: ValidationRule<RegExp>;
};

export interface LayoutModelItem<T> {
  group?: string;
  name?: Path<T> | string;
  label: string;
  suffix?: string;
  title?: string;
  span?: number;
  size?: string;
  type?: MetaLayoutItemType;
  format?: string;
  mode?: "disabled" | "hidden" | undefined;
  variant?: "bordered" | "ghost" | "label" | "compact" | "title";
  source?: Path<T> | string;
  enable?: string[];
  disable?: string[];
  rules?: ItemRules;
}

export interface MetaLayoutGroupItem<T> {
  value?: LayoutModelItem<T>;
  children?: LayoutModelItem<T>[]
}

export function layoutDivider<T>(span: number): LayoutModelItem<T> { return ({span: span, label: ""}) }

export function findEnabled<T>(
  items: LayoutModelItem<T>[] | undefined,
  currentName: string,
  step: number
): LayoutModelItem<T> | undefined {
  if (!items) return undefined;

  const index = items.findIndex(
    i => i.name === currentName
  );

  if (index === -1) return undefined;

  for (
    let i = index + step;
    i >= 0 && i < items.length;
    i += step
  ) {
    if (items[i].mode === undefined && items[i].type !== "hidden") {
      return items[i];
    }
  }

  return undefined;
}

export function splitPath(path: string, n: number): string[] {
  const parts = path.split(".");
  const firstSegments = parts.slice(0, n);
  if (parts.length > n) {
    const remaining =  parts.slice(n).join(".");
    return [...firstSegments, remaining];
  }
  return firstSegments;
}