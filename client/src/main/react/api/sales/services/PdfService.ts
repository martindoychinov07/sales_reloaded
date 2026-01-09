/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PdfService {
  /**
   * @returns string OK
   * @throws ApiError
   */
  public static getOrderPdf({
    orderId,
    lang,
    subtitle,
  }: {
    orderId: number,
    lang: string,
    subtitle: string,
  }): CancelablePromise<string> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/pdf/{orderId}',
      path: {
        'orderId': orderId,
      },
      query: {
        'lang': lang,
        'subtitle': subtitle,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
}
