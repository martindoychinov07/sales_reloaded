/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelOrderTypeDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'OrderTypeDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
