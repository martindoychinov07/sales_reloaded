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
import type { ExchangeDto } from '../models/ExchangeDto';
import type { PagedModelExchangeDto } from '../models/PagedModelExchangeDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ExchangeService {
  /**
   * @returns ExchangeDto OK
   * @throws ApiError
   */
  public static getExchangeById({
    id,
  }: {
    id: number,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/exchange/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ExchangeDto OK
   * @throws ApiError
   */
  public static updateExchange({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: ExchangeDto,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/exchange/{id}',
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
  public static deleteExchange({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/exchange/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ExchangeDto Created
   * @throws ApiError
   */
  public static createExchange({
    requestBody,
  }: {
    requestBody: ExchangeDto,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/exchange/',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelExchangeDto OK
   * @throws ApiError
   */
  public static findExchange({
    exchangeBase,
    exchangeTarget,
    page,
    size,
    sort,
    direction,
  }: {
    exchangeBase?: string,
    exchangeTarget?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelExchangeDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/exchange/find',
      query: {
        'exchangeBase': exchangeBase,
        'exchangeTarget': exchangeTarget,
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
