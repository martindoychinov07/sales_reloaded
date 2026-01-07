/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelContactDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'ContactDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
