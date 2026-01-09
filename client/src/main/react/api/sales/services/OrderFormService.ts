/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderFormDto } from '../models/OrderFormDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class OrderFormService {
  /**
   * @returns OrderFormDto OK
   * @throws ApiError
   */
  public static updateOrder({
    requestBody,
  }: {
    requestBody: OrderFormDto,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/order/updateOrderForm',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns OrderFormDto Created
   * @throws ApiError
   */
  public static createOrder({
    requestBody,
  }: {
    requestBody: OrderFormDto,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/order/createOrderForm',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns OrderFormDto OK
   * @throws ApiError
   */
  public static getOrderById({
    id,
  }: {
    id: number,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/order/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns OrderFormDto OK
   * @throws ApiError
   */
  public static getLastOrderByOrderType({
    orderTypeId,
  }: {
    orderTypeId: number,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/order/last/{orderTypeId}',
      path: {
        'orderTypeId': orderTypeId,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns OrderFormDto OK
   * @throws ApiError
   */
  public static getOrderCopyById({
    id,
    content,
  }: {
    id: number,
    content: boolean,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/order/copy/{id}',
      path: {
        'id': id,
      },
      query: {
        'content': content,
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
  public static deleteOrder({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/order/deleteOrderForm',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
