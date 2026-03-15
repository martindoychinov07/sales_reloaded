/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelSettingDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'SettingDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
