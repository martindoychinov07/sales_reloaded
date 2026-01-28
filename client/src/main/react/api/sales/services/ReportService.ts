/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagedModelReportDto } from '../models/PagedModelReportDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportService {
  /**
   * @returns PagedModelReportDto OK
   * @throws ApiError
   */
  public static findReport({
    fromDate,
    toDate,
    orderNum,
    customerName,
    customerLocation,
    productName,
    orderTypeId,
    orderPayment,
    orderState,
    page,
    size,
    sort,
    direction,
  }: {
    fromDate?: string,
    toDate?: string,
    orderNum?: number,
    customerName?: string,
    customerLocation?: string,
    productName?: string,
    orderTypeId?: number,
    orderPayment?: string,
    orderState?: 'canceled' | 'archived' | 'draft' | 'scheduled' | 'in_progress' | 'for_review' | 'reviewed' | 'for_approve' | 'approved' | 'finished',
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelReportDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/report/',
      query: {
        'fromDate': fromDate,
        'toDate': toDate,
        'orderNum': orderNum,
        'customerName': customerName,
        'customerLocation': customerLocation,
        'productName': productName,
        'orderTypeId': orderTypeId,
        'orderPayment': orderPayment,
        'orderState': orderState,
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
