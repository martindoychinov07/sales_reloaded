/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelTranslationDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'TranslationDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
