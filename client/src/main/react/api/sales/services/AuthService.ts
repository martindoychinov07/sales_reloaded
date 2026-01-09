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
