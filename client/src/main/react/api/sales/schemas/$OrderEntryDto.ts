/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $OrderEntryDto = {
  properties: {
    entryId: {
      type: 'number',
      format: 'int32',
    },
    entryVersion: {
      type: 'number',
      format: 'int32',
    },
    entryRow: {
      type: 'number',
      format: 'int32',
    },
    entryProduct: {
      type: 'EntryProductDto',
    },
    entryBarcode: {
      type: 'string',
      maxLength: 50,
    },
    entryCode: {
      type: 'string',
      maxLength: 50,
    },
    entryLabel: {
      type: 'string',
      maxLength: 200,
    },
    entryUnits: {
      type: 'number',
      format: 'int32',
    },
    entryMeasure: {
      type: 'string',
      maxLength: 30,
    },
    entryAvailable: {
      type: 'number',
      format: 'int32',
    },
    entryQuantity: {
      type: 'number',
      format: 'int32',
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
  },
} as const;
