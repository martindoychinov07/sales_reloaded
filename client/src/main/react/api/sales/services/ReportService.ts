/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagedModelOrderFormView } from '../models/PagedModelOrderFormView';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportService {
  /**
   * @returns PagedModelOrderFormView OK
   * @throws ApiError
   */
  public static findReport({
    startOrderDate,
    endOrderDate,
    customerName,
    page,
    size,
    sort,
    direction,
  }: {
    startOrderDate?: string,
    endOrderDate?: string,
    customerName?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelOrderFormView> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/report/findReport',
      query: {
        'startOrderDate': startOrderDate,
        'endOrderDate': endOrderDate,
        'customerName': customerName,
        'page': page,
        'size': size,
        'sort': sort,
        'direction': direction,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
}
