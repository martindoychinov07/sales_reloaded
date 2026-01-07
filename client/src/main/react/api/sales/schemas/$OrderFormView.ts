/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $OrderFormView = {
  properties: {
    orderId: {
      type: 'number',
      format: 'int32',
    },
    orderState: {
      type: 'Enum',
    },
    orderCcp: {
      type: 'string',
    },
    orderRate: {
      type: 'number',
    },
    orderNum: {
      type: 'number',
      format: 'int64',
    },
    orderDate: {
      type: 'string',
      format: 'date-time',
    },
    orderSum: {
      type: 'number',
    },
    orderTax: {
      type: 'number',
    },
    orderTotal: {
      type: 'number',
    },
    orderCustomerContactName: {
      type: 'string',
    },
    orderTypeTypeKey: {
      type: 'string',
    },
    orderSupplierContactName: {
      type: 'string',
    },
  },
} as const;
