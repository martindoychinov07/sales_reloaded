import type {LayoutModel, LayoutModelItem} from "./LayoutModel.ts";
import type {InputOption, InputOptions} from "./Input.tsx";
import type {PageMetadata} from "../api/sales";

export type ListType<D> = {
  content?: Array<D>;
  page?: PageMetadata;
}

export interface ListMetadata {
  page?: number;
  size?: number;
  sort?: string;
  direction?: "ASC" | "DESC";
}

export type ListFormModel<F extends ListMetadata, D> = {
  action: {
    search: (args: F) => Promise<ListType<D>>;
    create?: (param: { requestBody: D }) => Promise<D>;
    save?:   (param: { requestBody: D }) => Promise<D>;
    remove?: (param: { requestBody: number }) => Promise<void>;
  }
  form: {
    args: F;
    paging?: ListMetadata;
    action: "search" | "create" | "copy" | "edit" | "save" | "cancel" | "remove" | "confirm" | "close" | string | undefined;
    selected?: string;
    disabled: string[];
    mode?: string;
    input?: D;
    inputId: keyof D;
  }
  fields: {
    layout: LayoutModel<F>;
    options: { [key: string]: InputOptions<F> }
  }
  table: {
    layout: LayoutModel<D>;
    options: { [key: string]: InputOptions<F> }
  }
}

export function getOptionSize(): InputOption[] {
  return (
    [
      {value: "100", label: "100"},
      {value: "200", label: "200"},
      {value: "300", label: "300"},
      {value: "400", label: "400"},
      {value: "500", label: "500"},
      {value: "600", label: "600"},
      {value: "700", label: "700"},
      {value: "800", label: "800"},
      {value: "900", label: "900"},
      {value: "1000", label: "1000"},
    ]
  )
}

export function getOptionSort<T>(items?: LayoutModelItem<T>[]): InputOption[] {
  return items
    ?.filter(item => item.type !== "hidden" && item.mode !== "hidden")
    .map(item => ({value: item.name, label: item.label})) ?? []
}

export function getOptionDirection(): InputOption[] {
  return (
    [
      {value: "ASC", label: "A..Z"},
      {value: "DESC", label: "Z..A"},
    ]
  )
}