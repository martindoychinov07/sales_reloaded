import type {LayoutModelItem} from "../../utils/LayoutModel.ts";

export function getCommonActions<T>() {
  const actions: LayoutModelItem<T>[] = [
    {
      span: 1,
      group: "args",
      name: "page",
      label: "~filter.page",
      type: "number",
      rules: {
        required: true,
      }
    },
    {
      span: 1,
      group: "args",
      name: "size",
      label: "~filter.size",
      type: "number",
      rules: {
        required: true,
      }
    },
    // {
    //   span: 2,
    //   group: "args",
    //   name: "sort",
    //   label: "~filter.sort",
    //   type: "select",
    //   source: "sort",
    // },
    // {
    //   span: 2,
    //   group: "args",
    //   name: "direction",
    //   label: "~filter.direction",
    //   type: "select",
    //   source: "direction",
    // },
    {
      span: 2,
      group: "args",
      name: "view",
      label: "~filter.view",
      type: "select",
      source: "view",
    },
    {
      span: 1,
      group: "action",
      name: "create",
      label: "~action.create",
      type: "button",
      enable: ["save", "cancel"],
      disable: ["create", "copy", "edit", "delete", "export", "search", "close", "confirm", "mode"],
    },
    {
      span: 1,
      group: "action",
      name: "copy",
      label: "~action.copy",
      type: "button",
      enable: ["save", "cancel"],
      disable: ["create", "copy", "edit", "delete", "export", "search", "close", "confirm", "mode"],
    },
    {
      span: 1,
      group: "action",
      name: "edit",
      label: "~action.edit",
      type: "button",
      enable: ["save", "cancel"],
      disable: ["create", "copy", "edit", "delete", "export", "search", "close", "confirm", "mode"],
    },
    {
      span: 1,
      group: "action",
      name: "delete",
      label: "~action.delete",
      type: "button",
    },
    {
      span: 1,
      group: "action",
      name: "export",
      label: "~action.export",
      type: "button",
    },
    {
      span: 1,
      group: "action",
      label: "",
    },
    {
      span: 1,
      group: "action",
      name: "cancel",
      label: "~action.cancel",
      type: "button",
      enable: ["create", "copy", "edit", "delete", "export", "search", "close", "confirm", "mode"],
      disable: ["save", "cancel"],
    },
    {
      span: 1,
      group: "action",
      name: "save",
      label: "~action.save",
      type: "submit",
      enable: ["create", "copy", "edit", "delete", "export", "search", "close", "confirm", "mode"],
      disable: ["save", "cancel"],
    },
    {
      span: 1,
      group: "modal",
      name: "mode",
      label: "~action.mode",
      type: "toggle",
    },
    {
      span: 5,
      group: "modal",
      label: "",
    },
    {
      span: 1,
      group: "modal",
      name: "close",
      label: "~action.close",
      type: "button",
    },
    {
      span: 1,
      group: "modal",
      name: "confirm",
      label: "~action.ok",
      type: "button",
    },
  ]

  return actions;
}