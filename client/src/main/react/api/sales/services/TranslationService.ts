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
  public static updateTranslation({
    requestBody,
  }: {
    requestBody: TranslationDto,
  }): CancelablePromise<TranslationDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/translation/updateTranslation',
      body: requestBody,
      mediaType: 'application/json',
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
      url: '/api/translation/createTranslation',
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
      url: '/api/translation/findTranslation',
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
  /**
   * @returns any OK
   * @throws ApiError
   */
  public static deleteTranslation({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/translation/deleteTranslation',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
