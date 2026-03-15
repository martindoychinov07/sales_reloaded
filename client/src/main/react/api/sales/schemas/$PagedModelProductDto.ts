/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelProductDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'ProductDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
