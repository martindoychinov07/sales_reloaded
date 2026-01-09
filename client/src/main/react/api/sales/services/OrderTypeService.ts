/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderTypeDto } from '../models/OrderTypeDto';
import type { PagedModelOrderTypeDto } from '../models/PagedModelOrderTypeDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class OrderTypeService {
  /**
   * @returns OrderTypeDto OK
   * @throws ApiError
   */
  public static updateOrderType({
    requestBody,
  }: {
    requestBody: OrderTypeDto,
  }): CancelablePromise<OrderTypeDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/orderType/updateOrderType',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns OrderTypeDto Created
   * @throws ApiError
   */
  public static createOrderType({
    requestBody,
  }: {
    requestBody: OrderTypeDto,
  }): CancelablePromise<OrderTypeDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/orderType/createOrderType',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelOrderTypeDto OK
   * @throws ApiError
   */
  public static findOrderType({
    page,
    size,
    sort,
    direction,
  }: {
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelOrderTypeDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/orderType/findOrderType',
      query: {
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
  /**
   * @returns any OK
   * @throws ApiError
   */
  public static deleteOrderType({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/orderType/deleteOrderType',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
