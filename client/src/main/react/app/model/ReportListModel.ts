import {type OrderFormView, ReportService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import { getDateOffsetMonth } from "../../utils/DateUtils.ts";

export const ReportListModel: ListFormModel<Parameters<typeof ReportService.findReport>[number], OrderFormView> = {
  action: {
    search: ReportService.findReport,
    create: undefined,
    save: undefined,
    remove: undefined,
  },
  form: {
    args: {
      startOrderDate: getDateOffsetMonth(-1).toISOString(),
      endOrderDate: undefined,
    },
    paging: {
      page: 0,
      size: 100,
      sort: "orderDate",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
    disabled: [],
    input: undefined,
    inputId: "orderId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "startOrderDate",
          label: "~report.startOrderDate",
          type: "datetime",
          pattern: "~pattern.datetime",
          source: "calendar",
        },
        {
          span: 2,
          group: "args",
          name: "endOrderDate",
          label: "~report.endOrderDate",
          type: "datetime",
          pattern: "~pattern.datetime",
          source: "calendar",
        },
        {
          span: 2,
          group: "args",
          name: "customerName",
          label: "~customer.name",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "search",
          label: "~action.search",
          type: "submit",
        },
        {
          span: 2,
          group: "paging",
          name: "page",
          label: "~filter.page",
          type: "number",
        },
        {
          span: 2,
          group: "paging",
          name: "size",
          label: "~filter.size",
          type: "select",
          source: "size",
        },
        {
          span: 2,
          group: "paging",
          name: "sort",
          label: "~filter.sort",
          type: "select",
          source: "sort",
        },
        {
          span: 2,
          group: "paging",
          name: "direction",
          label: "~filter.direction",
          type: "select",
          source: "direction",
        },
        {
          span: 1,
          group: "action",
          name: "create",
          label: "~action.create",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "search", "close", "ok"],
        },
        {
          span: 1,
          group: "action",
          name: "copy",
          label: "~action.copy",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "search", "close", "ok"],
        },
        {
          span: 1,
          group: "action",
          name: "edit",
          label: "~action.edit",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "search", "close", "ok"],
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
          name: "print",
          label: "~action.print",
          type: "button",
        },
      ]
    },
    options: {
      "size": () => {
        return getOptionSize();
      },

      "sort": () => {
        return getOptionSort(ReportListModel.table.layout.items);
      },

      "direction": () => {
        return getOptionDirection();
      },
    },
  },
  table: {
    layout: {
      variant: "table",
      items: [
        {
          group: "input",
          name: "orderId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "orderDate",
          label: "~order.date",
          type: "datetime",
          pattern: "~pattern.datetime",
        },
        {
          group: "input",
          name: "orderTypeTypeKey",
          label: "~order.type",
          type: "text",
          pattern: "~",
        },
        {
          group: "input",
          name: "orderNum",
          label: "~order.num",
          type: "number",
          pattern: "~pattern.counter",
        },
        {
          group: "input",
          name: "orderCustomerContactName",
          label: "~order.customer.name",
          type: "text"
        },
        {
          group: "input",
          name: "orderState",
          label: "~order.state",
          type: "text",
          pattern: "~orderState.",
        },
        {
          group: "input",
          name: "orderCcp",
          label: "~order.ccp",
          type: "text"
        },
        {
          group: "input",
          name: "orderRate",
          label: "~order.rate",
          type: "number",
          pattern: "~pattern.rate",
        },
        {
          group: "input",
          name: "orderSum",
          label: "~order.sum",
          type: "number",
          pattern: "~pattern.total",
        },
        {
          group: "input",
          name: "orderTax",
          label: "~order.tax",
          type: "number",
          pattern: "~pattern.total",
        },
        {
          group: "input",
          name: "orderTotal",
          label: "~order.total",
          type: "number",
          pattern: "~pattern.total",
        },
      ]
    },
    options: {}
  },
}