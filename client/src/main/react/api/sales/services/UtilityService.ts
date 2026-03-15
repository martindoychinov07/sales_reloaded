/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JsonNode } from '../models/JsonNode';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UtilityService {
  /**
   * @returns binary Generated XLSX file
   * @throws ApiError
   */
  public static requestXlsx({
    name,
    sep,
    eol,
    formData,
  }: {
    name: string,
    sep?: string,
    eol?: string,
    formData?: {
      blob: Blob;
    },
  }): CancelablePromise<Blob> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/utility/xlsx',
      query: {
        'name': name,
        'sep': sep,
        'eol': eol,
      },
      formData: formData,
      mediaType: 'multipart/form-data',
    });
  }
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
      url: '/api/utility/json/{api}/{params}',
      path: {
        'api': api,
        'params': params,
      },
    });
  }
}
