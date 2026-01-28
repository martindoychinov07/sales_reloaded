import type {LayoutModel} from "../../utils/LayoutModel.ts";
import {type OrderEntryDto, OrderFormDto} from "../../api/sales";

export type OrderFormModelType = {
  fields: LayoutModel<OrderFormDto>;
  table: LayoutModel<OrderEntryDto>;
}

export const OrderFormModel: OrderFormModelType = {
  fields: {
    columns: 8,
    variant: "inner",
    items: [
      {
        span: 2,
        name: "orderState",
        label: "~order.state",
        type: "select",
        source: "orderState",
        rules: {
          required: true,
        }
      },
      {
        span: 2,
        name: "orderType.typeId",
        label: "~order.type",
        type: "select",
        source: "orderType",
        rules: {
          required: true,
        }
      },
      {
        span: 2,
        name: "orderNum",
        label: "~order.num",
        type: "number",
        format: "~format.counter",
        rules: {
          required: true,
        }
      },
      {
        span: 2,
        name: "orderDate",
        label: "~order.date",
        type: "datetime",
        format: "~format.datetime",
        source: "calendar",
        rules: {
          required: true,
        }
      },
      {
        span: 4,
        name: "orderCustomer.contactName",
        label: "~order.customer.name",
        type: "dialog",
        source: "contact",
        rules: {
          required: true,
        }
      },
      {
        span: 4,
        name: "orderSupplier.contactName",
        label: "~order.supplier.name",
        type: "dialog",
        source: "contact",
        rules: {
          required: true,
        }
      },

      {
        span: 4,
        name: "orderCustomer.contactLocation",
        label: "~order.customer.location",
        type: "dialog",
        source: "location",
      },
      {
        span: 2,
        name: "orderCcp",
        label: "~order.ccp",
        type: "select",
        source: "orderCcp",
        rules: {
          required: true,
        }
      },
      {
        span: 2,
        name: "orderRate",
        label: "~order.rate",
        type: "number",
        format: "~format.rate",
        rules: {
          required: true,
        }
      },

      {
        span: 4,
        name: "orderCustomer.contactAddress",
        label: "~order.customer.address",
        type: "text",
      },
      {
        span: 2,
        name: "orderPayment",
        label: "~order.payment",
        type: "select",
        source: "payment",
        rules: {
          required: true,
        },
      },
      {
        span: 2,
        name: "orderPaymentDate",
        label: "~order.payment.date",
        type: "datetime",
        format: "~format.date",
        source: "calendar",
      },

      {
        span: 2,
        name: "orderCustomer.contactCode1",
        label: "~order.customer.code1",
        type: "dialog",
        source: "contact.uic"
      },
      {
        span: 2,
        name: "orderCustomer.contactCode2",
        label: "~order.customer.code2",
        type: "dialog",
        source: "contact",
      },

      {
        span: 2,
        name: "orderResp",
        label: "~order.resp",
        type: "text",
      },
      {
        span: 2,
        name: "orderRespDate",
        label: "~order.resp.date",
        type: "datetime",
        format: "~format.date",
        source: "calendar",
      },

      {
        span: 2,
        name: "orderRcvdDate",
        label: "~order.rcvd.date",
        type: "datetime",
        format: "~format.datetime.short",
        source: "calendar",
        mode: "hidden",
      },

      {
        span: 2,
        name: "orderDlvd",
        label: "~order.dlvd",
        type: "text",
        mode: "hidden",
      },
      {
        span: 2,
        name: "orderDlvdDate",
        label: "~order.dlvd.date",
        type: "datetime",
        format: "~format.datetime.short",
        source: "calendar",
        mode: "hidden",
      },

      {
        span: 4,
        name: "orderNote",
        label: "~order.note",
        type: "search"
      },
      {
        span: 4,
        name: "orderRef",
        label: "~order.ref",
        type: "search"
      },

      {
        span: 2,
        name: "orderRcvd",
        label: "~order.rcvd",
        type: "text",
      },
      {
        span: 2,
        name: "orderTaxPct",
        label: "~order.tax_pct",
        type: "number",
        format: "~format.percent",
      },
      {
        span: 1,
        name: "orderDiscount",
        label: "~order.discount",
        type: "number",
        mode: "disabled",
        variant: "compact",
        format: "~format.total",
      },
      {
        span: 1,
        name: "orderSum",
        label: "~order.sum",
        type: "number",
        mode: "disabled",
        variant: "compact",
        format: "~format.total",
      },
      {
        span: 1,
        name: "orderTax",
        label: "~order.tax",
        type: "number",
        mode: "disabled",
        variant: "compact",
        format: "~format.total",
      },
      {
        span: 1,
        name: "orderTotal",
        label: "~order.total",
        type: "number",
        mode: "disabled",
        variant: "compact",
        format: "~format.total",
      },
      {
        span: 1,
        group: "action",
        name: "delete",
        label: "~action.delete.row",
        type: "button",
      },
      {
        span: 4,
        group: "action",
        label: "",
      },
      {
        span: 1,
        group: "action",
        name: "cancel",
        label: "~action.cancel",
        type: "button",
      },
      {
        span: 1,
        group: "action",
        name: "save",
        label: "~action.save",
        type: "submit",
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
  table: {
    variant: "table",
    items: [
      {
        name: "entryId",
        label: "",
        type: "hidden"
      },
      {
        name: "entryRow",
        label: "~entry.row",
        type: "number",
        size: "7ch",
      },
      {
        name: "entryProduct.productId",
        label: "",
        type: "hidden",
      },
      {
        name: "entryCode",
        label: "~entry.code",
        type: "text",
        size: "1ch",
      },
      {
        name: "entryBarcode",
        label: "~entry.barcode",
        type: "text",
        size: "1ch",
      },
      {
        name: "entryLabel",
        label: "~entry.label",
        type: "dialog",
        source: "product",
        rules: {
          required: true,
        }
      },
      {
        name: "entryUnits",
        label: "~entry.units",
        type: "number",
        format: "~format.quantity",
        mode: "disabled",
        size: "10ch",
      },
      {
        name: "entryMeasure",
        label: "~entry.measure",
        type: "text",
        mode: "disabled",
        size: "10ch",
      },
      {
        name: "entryPrice",
        label: "~entry.price",
        type: "number",
        format: "~format.price",
        size: "12ch",
      },
      {
        name: "entryAvailable",
        label: "~entry.available",
        type: "number",
        format: "~format.quantity",
        mode: "disabled",
        rules: {
          required: true,
        },
        size: "12ch",
      },
      {
        name: "entryQuantity",
        label: "~entry.quantity",
        type: "number",
        format: "~format.quantity",
        size: "12ch",
      },
      {
        name: "entryDiscountPct",
        label: "~entry.discountPct",
        type: "number",
        format: "~format.percent",
        size: "8ch",
      },
      {
        name: "entrySum",
        label: "~entry.sum",
        type: "number",
        mode: "disabled",
        format: "~format.total",
        size: "15ch",
      },
      {
        name: "entryDiscount",
        label: "~entry.discount",
        type: "hidden",
        mode: "disabled",
        format: "~format.total",
        size: "15ch",
      },
      {
        name: "entryTax",
        label: "~entry.tax",
        type: "number",
        mode: "disabled",
        format: "~format.total",
        size: "15ch",
      },
      {
        name: "entryTotal",
        label: "~entry.total",
        type: "number",
        mode: "disabled",
        format: "~format.total",
        size: "15ch",
      },
    ]
  }
}