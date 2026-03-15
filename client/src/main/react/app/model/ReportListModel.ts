import { type ReportDto, ReportService } from "../../api/sales";
import { type CrudFormModel, getDateOffset, } from "@crud-daisyui/utils";
import { getOptionOrderState, getOptionOrderType, getOptionPayment, getOrderTypeList } from "./OptionModel.ts";

export const ReportListModel: CrudFormModel<Parameters<typeof ReportService.findReport>[number], ReportDto> = {
  action: {
    search: ReportService.findReport,
    create: undefined,
    save: undefined,
    remove: undefined,
  },
  form: {
    args: {
      fromDate: getDateOffset(0, "from").toISOString(),
      toDate: getDateOffset(0, "to").toISOString(),

      page: 1,
      size: 1000,
      sort: "orderDate",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: [],
    input: undefined,
    inputId: "reportId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 16,
      items: [
        {
          group: "args",
          name: "default",
          label: "~action.default",
          type: "submit",
          mode: "hidden"
        },
        {
          span: 2,
          group: "args",
          name: "fromDate",
          label: "~report.fromDate",
          type: "datetime",
          format: "~format.datetime",
          source: "calendar",
          variant: "title",
        },
        {
          span: 2,
          group: "args",
          name: "toDate",
          label: "~report.toDate",
          type: "datetime",
          format: "~format.datetime",
          source: "calendar",
        },
        {
          span: 2,
          group: "args",
          name: "orderTypeId",
          label: "~order.type",
          type: "select",
          source: "orderType",
        },
        {
          span: 2,
          group: "args",
          name: "orderNum",
          label: "~report.ref",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "orderPayment",
          label: "~order.payment",
          type: "select",
          source: "payment",
        },
        {
          span: 2,
          group: "args",
          name: "orderState",
          label: "~order.state",
          type: "select",
          source: "orderState",
        },
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
        {
          span: 2,
          group: "args",
          name: "search",
          label: "~action.search",
          type: "submit",
        },
        {
          span: 4,
          group: "args",
          name: "customerName",
          label: "~order.customer.name",
          type: "dialog",
          source: "contact",
        },
        {
          span: 4,
          group: "args",
          name: "customerLocation",
          label: "~order.customer.location",
          type: "text",
        },
        {
          span: 4,
          group: "args",
          name: "productText",
          label: "~order.product",
          type: "dialog",
          source: "product",
        },
        {
          span: 4,
          group: "args",
          name: "productNote",
          label: "~product.note",
          type: "search",
        },
        {
          span: 2,
          group: "action",
          name: "create",
          label: "~action.create",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "export", "search", "close", "ok"],
        },
        {
          span: 2,
          group: "action",
          name: "copy",
          label: "~action.copy",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "export", "search", "close", "ok"],
        },
        {
          span: 2,
          group: "action",
          name: "edit",
          label: "~action.edit",
          type: "button",
          enable: ["save", "cancel"],
          disable: ["create", "copy", "edit", "delete", "export", "search", "close", "ok"],
        },
        {
          span: 2,
          group: "action",
          name: "delete",
          label: "~action.delete",
          type: "button",
        },
        {
          span: 2,
          group: "action",
          name: "export",
          label: "~action.export",
          type: "button",
        },
        {
          span: 2,
          group: "action",
          name: "print",
          label: "~action.print",
          type: "button",
        },
      ]
    },
    options: {
      "orderState": () => getOptionOrderState("~orderState.all"),

      "payment": () => { return getOptionPayment("~payment.all"); },

      "orderType": async () => { return getOptionOrderType(await getOrderTypeList(), "~orderType.all"); }
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
          name: "entryId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "orderDate",
          label: "~order.date",
          type: "datetime",
          format: "~format.datetime",
        },
        {
          group: "input",
          name: "orderType",
          label: "~order.type",
          type: "text",
          format: "~",
          source: "orderType"
        },
        {
          group: "input",
          name: "orderNum",
          label: "~order.num",
          type: "number",
          format: "~format.counter",
        },
        {
          group: "input",
          name: "customerName",
          label: "~order.customer.name",
          type: "text"
        },
        {
          group: "input",
          name: "customerLocation",
          label: "~order.customer.location",
          type: "text"
        },
        {
          group: "input",
          name: "entryRow",
          label: "~order.row",
          type: "number"
        },
        {
          group: "input",
          name: "entryBarcode",
          label: "~entry.barcode",
          type: "text"
        },
        {
          group: "input",
          name: "productName",
          label: "~order.product",
          type: "text"
        },
        {
          group: "input",
          name: "productNote",
          label: "~product.note",
          type: "text"
        },
        {
          group: "input",
          name: "entryAvailable",
          label: "~entry.available",
          type: "number",
          format: "~format.quantity",
        },
        {
          group: "input",
          name: "entryQuantity",
          label: "~entry.quantity",
          type: "number",
          format: "~format.quantity",
        },
        {
          group: "input",
          name: "entryPrice",
          label: "~entry.price",
          type: "number",
          format: "~format.total",
        },
        {
          group: "input",
          name: "entrySum",
          label: "~order.sum",
          type: "number",
          format: "~format.total",
        },
        {
          group: "input",
          name: "entryTax",
          label: "~order.tax",
          type: "number",
          format: "~format.total",
        },
        {
          group: "input",
          name: "entryTotal",
          label: "~order.total",
          type: "number",
          format: "~format.total",
        },
        {
          group: "input",
          name: "orderCy",
          label: "~order.ccp",
          type: "text"
        },
        {
          name: "entryDiscountPct",
          label: "~entry.discountPct",
          type: "number",
          format: "~format.percent",
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
          format: "~format.rate",
        },
        {
          group: "input",
          name: "orderPaymentDate",
          label: "~order.payment.date",
          type: "datetime",
          format: "~format.datetime",
        },
        {
          group: "input",
          name: "orderPayment",
          label: "~order.payment",
          type: "text",
          format: "~payment.",
          source: "payment",
        },
        {
          group: "input",
          name: "orderResp",
          label: "~order.resp",
          type: "text",
        },
        {
          group: "input",
          name: "orderState",
          label: "~order.state",
          type: "text",
          format: "~orderState.",
          source: "orderState"
        },
      ]
    },
    options: {}
  },
}