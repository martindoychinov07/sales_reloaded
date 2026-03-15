/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $ExchangeDto = {
  properties: {
    exchangeId: {
      type: 'number',
      format: 'int32',
    },
    exchangeDate: {
      type: 'string',
      format: 'date-time',
    },
    exchangeTarget: {
      type: 'string',
      isRequired: true,
      maxLength: 3,
    },
    exchangeSource: {
      type: 'string',
      isRequired: true,
      maxLength: 3,
    },
    exchangeRate: {
      type: 'number',
      isRequired: true,
    },
  },
} as const;
