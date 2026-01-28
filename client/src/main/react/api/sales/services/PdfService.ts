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
    sign,
    name,
  }: {
    orderId: number,
    lang: string,
    sign: string,
    name: string,
  }): CancelablePromise<string> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/pdf/{orderId}/{lang}/{sign}/{name}',
      path: {
        'orderId': orderId,
        'lang': lang,
        'sign': sign,
        'name': name,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
}
