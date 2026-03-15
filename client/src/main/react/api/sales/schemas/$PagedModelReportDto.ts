/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $PagedModelReportDto = {
  properties: {
    content: {
      type: 'array',
      contains: {
        type: 'ReportDto',
      },
    },
    page: {
      type: 'PageMetadata',
    },
  },
} as const;
