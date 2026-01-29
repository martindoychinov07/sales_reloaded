/**
 * Copyright 2026 Martin Doychinov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
  public static updateOrder({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: OrderFormDto,
  }): CancelablePromise<OrderFormDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/order/{id}',
      path: {
        'id': id,
      },
      body: requestBody,
      mediaType: 'application/json',
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
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
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
      url: '/api/order/',
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
}
