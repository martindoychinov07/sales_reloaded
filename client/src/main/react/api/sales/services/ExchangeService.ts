/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ExchangeDto } from '../models/ExchangeDto';
import type { PagedModelExchangeDto } from '../models/PagedModelExchangeDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ExchangeService {
  /**
   * @returns ExchangeDto OK
   * @throws ApiError
   */
  public static getExchangeById({
    id,
  }: {
    id: number,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/exchange/{id}',
      path: {
        'id': id,
      },
    });
  }
  /**
   * @returns ExchangeDto OK
   * @throws ApiError
   */
  public static updateExchange({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: ExchangeDto,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/exchange/{id}',
      path: {
        'id': id,
      },
      body: requestBody,
      mediaType: 'application/json',
    });
  }
  /**
   * @returns any OK
   * @throws ApiError
   */
  public static deleteExchange({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/exchange/{id}',
      path: {
        'id': id,
      },
    });
  }
  /**
   * @returns ExchangeDto Created
   * @throws ApiError
   */
  public static createExchange({
    requestBody,
  }: {
    requestBody: ExchangeDto,
  }): CancelablePromise<ExchangeDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/exchange',
      body: requestBody,
      mediaType: 'application/json',
    });
  }
  /**
   * @returns PagedModelExchangeDto OK
   * @throws ApiError
   */
  public static findExchange({
    exchangeSource,
    exchangeTarget,
    page,
    size,
    sort,
    direction,
  }: {
    exchangeSource?: string,
    exchangeTarget?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelExchangeDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/exchange/find',
      query: {
        'exchangeSource': exchangeSource,
        'exchangeTarget': exchangeTarget,
        'page': page,
        'size': size,
        'sort': sort,
        'direction': direction,
      },
    });
  }
}
