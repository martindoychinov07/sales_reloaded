/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $OrderTypeDto = {
  properties: {
    typeId: {
      type: 'number',
      format: 'int32',
    },
    typeOrder: {
      type: 'number',
      format: 'int32',
      maximum: 100,
    },
    typeKey: {
      type: 'string',
    },
    typeNum: {
      type: 'number',
      format: 'int64',
    },
    typePrint: {
      type: 'string',
      maxLength: 100,
    },
    typeEval: {
      type: 'Enum',
    },
    typeCcp: {
      type: 'string',
      maxLength: 7,
    },
    typeNote: {
      type: 'string',
      maxLength: 100,
    },
    typeTaxPct: {
      type: 'number',
    },
  },
} as const;
