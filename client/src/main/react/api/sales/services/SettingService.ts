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
import type { PagedModelSettingDto } from '../models/PagedModelSettingDto';
import type { SettingDto } from '../models/SettingDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SettingService {
  /**
   * @returns SettingDto OK
   * @throws ApiError
   */
  public static getSettingById({
    id,
  }: {
    id: number,
  }): CancelablePromise<SettingDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/setting/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns SettingDto OK
   * @throws ApiError
   */
  public static updateSetting({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: SettingDto,
  }): CancelablePromise<SettingDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/setting/{id}',
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
  public static deleteSetting({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/setting/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns SettingDto Created
   * @throws ApiError
   */
  public static createSetting({
    requestBody,
  }: {
    requestBody: SettingDto,
  }): CancelablePromise<SettingDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/setting/',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelSettingDto OK
   * @throws ApiError
   */
  public static findSetting({
    settingKey,
    settingGroup,
    settingNote,
    page,
    size,
    sort,
    direction,
  }: {
    settingKey?: string,
    settingGroup?: string,
    settingNote?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelSettingDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/setting/find',
      query: {
        'settingKey': settingKey,
        'settingGroup': settingGroup,
        'settingNote': settingNote,
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
