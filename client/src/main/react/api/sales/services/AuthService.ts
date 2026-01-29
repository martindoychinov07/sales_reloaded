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
import type { AuthUser } from '../models/AuthUser';
import type { CsrfToken } from '../models/CsrfToken';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AuthService {
  /**
   * @returns AuthUser OK
   * @throws ApiError
   */
  public static info(): CancelablePromise<AuthUser> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/auth/info',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns CsrfToken OK
   * @throws ApiError
   */
  public static csrf({
    token,
  }: {
    token: CsrfToken,
  }): CancelablePromise<CsrfToken> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/auth/csrf',
      query: {
        'token': token,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
}
