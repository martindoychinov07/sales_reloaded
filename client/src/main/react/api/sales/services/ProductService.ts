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
import type { PagedModelProductDto } from '../models/PagedModelProductDto';
import type { ProductDto } from '../models/ProductDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ProductService {
  /**
   * @returns ProductDto OK
   * @throws ApiError
   */
  public static getProductById({
    id,
  }: {
    id: number,
  }): CancelablePromise<ProductDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/product/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ProductDto OK
   * @throws ApiError
   */
  public static updateProduct({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: ProductDto,
  }): CancelablePromise<ProductDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/product/{id}',
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
  public static deleteProduct({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/product/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ProductDto Created
   * @throws ApiError
   */
  public static createProduct({
    requestBody,
  }: {
    requestBody: ProductDto,
  }): CancelablePromise<ProductDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/product/',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelProductDto OK
   * @throws ApiError
   */
  public static findProduct({
    productText,
    productName,
    productNote,
    fromAvailable,
    toAvailable,
    page,
    size,
    sort,
    direction,
  }: {
    productText?: string,
    productName?: string,
    productNote?: string,
    fromAvailable?: number,
    toAvailable?: number,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelProductDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/product/find',
      query: {
        'productText': productText,
        'productName': productName,
        'productNote': productNote,
        'fromAvailable': fromAvailable,
        'toAvailable': toAvailable,
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
