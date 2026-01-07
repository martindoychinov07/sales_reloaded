/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelExchangeDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'ExchangeDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
