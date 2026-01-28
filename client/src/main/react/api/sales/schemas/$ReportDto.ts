/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $ReportDto = {
  properties: {
    reportId: {
      type: 'number',
      format: 'int32',
    },
    orderId: {
      type: 'number',
      format: 'int32',
    },
    entryId: {
      type: 'number',
      format: 'int32',
    },
    orderState: {
      type: 'Enum',
    },
    orderDate: {
      type: 'string',
      format: 'date-time',
    },
    orderNum: {
      type: 'number',
      format: 'int64',
    },
    orderType: {
      type: 'string',
    },
    supplierName: {
      type: 'string',
    },
    supplierLocation: {
      type: 'string',
    },
    customerName: {
      type: 'string',
    },
    customerLocation: {
      type: 'string',
    },
    orderCcp: {
      type: 'string',
    },
    orderCy: {
      type: 'string',
    },
    orderRate: {
      type: 'number',
    },
    orderResp: {
      type: 'string',
    },
    orderPayment: {
      type: 'string',
    },
    orderPaymentDate: {
      type: 'string',
      format: 'date-time',
    },
    productName: {
      type: 'string',
    },
    entryRow: {
      type: 'number',
      format: 'int32',
    },
    entryLabel: {
      type: 'string',
    },
    entryUnits: {
      type: 'number',
      format: 'int32',
    },
    entryMeasure: {
      type: 'string',
    },
    entryAvailable: {
      type: 'number',
    },
    entryQuantity: {
      type: 'number',
    },
    entryPrice: {
      type: 'number',
    },
    entryDiscountPct: {
      type: 'number',
    },
    entryDiscount: {
      type: 'number',
    },
    entrySum: {
      type: 'number',
    },
    entryTax: {
      type: 'number',
    },
    entryTotal: {
      type: 'number',
    },
    group: {
      type: 'string',
    },
  },
} as const;
