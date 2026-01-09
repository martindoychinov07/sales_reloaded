/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JsonNode } from '../models/JsonNode';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class RequestService {
  /**
   * @returns JsonNode OK
   * @throws ApiError
   */
  public static requestJson({
    api,
    params,
  }: {
    api: string,
    params: string,
  }): CancelablePromise<JsonNode> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/request/json/{api}/{params}',
      path: {
        'api': api,
        'params': params,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
}
