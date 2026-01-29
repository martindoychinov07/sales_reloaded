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
