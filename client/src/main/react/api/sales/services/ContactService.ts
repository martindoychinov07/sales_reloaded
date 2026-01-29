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
import type { ContactDto } from '../models/ContactDto';
import type { PagedModelContactDto } from '../models/PagedModelContactDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ContactService {
  /**
   * @returns ContactDto OK
   * @throws ApiError
   */
  public static getContactById({
    id,
  }: {
    id: number,
  }): CancelablePromise<ContactDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/contact/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ContactDto OK
   * @throws ApiError
   */
  public static updateContact({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: ContactDto,
  }): CancelablePromise<ContactDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/contact/{id}',
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
  public static deleteContact({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/contact/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ContactDto Created
   * @throws ApiError
   */
  public static createContact({
    requestBody,
  }: {
    requestBody: ContactDto,
  }): CancelablePromise<ContactDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/contact/',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelContactDto OK
   * @throws ApiError
   */
  public static findContact({
    contactCode,
    contactLocation,
    contactText,
    page,
    size,
    sort,
    direction,
  }: {
    contactCode?: string,
    contactLocation?: string,
    contactText?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelContactDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/contact/find',
      query: {
        'contactCode': contactCode,
        'contactLocation': contactLocation,
        'contactText': contactText,
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
