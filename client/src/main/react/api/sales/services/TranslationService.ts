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
import type { PagedModelTranslationDto } from '../models/PagedModelTranslationDto';
import type { TranslationDto } from '../models/TranslationDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class TranslationService {
  /**
   * @returns TranslationDto OK
   * @throws ApiError
   */
  public static getTranslationById({
    id,
  }: {
    id: number,
  }): CancelablePromise<TranslationDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/translation/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns TranslationDto OK
   * @throws ApiError
   */
  public static updateTranslation({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: TranslationDto,
  }): CancelablePromise<TranslationDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/translation/{id}',
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
  public static deleteTranslation({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/translation/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns TranslationDto Created
   * @throws ApiError
   */
  public static createTranslation({
    requestBody,
  }: {
    requestBody: TranslationDto,
  }): CancelablePromise<TranslationDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/translation/',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelTranslationDto OK
   * @throws ApiError
   */
  public static findTranslation({
    translationKey,
    en,
    bg,
    page,
    size,
    sort,
    direction,
  }: {
    translationKey?: string,
    en?: string,
    bg?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelTranslationDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/translation/find',
      query: {
        'translationKey': translationKey,
        'en': en,
        'bg': bg,
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
