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
  pattern?: string;
  mode?: "input" | "disabled" | "hidden";
  variant?: "bordered" | "ghost" | "label" | "compact";
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
