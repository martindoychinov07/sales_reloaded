/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelAppUserDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'AppUserDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
