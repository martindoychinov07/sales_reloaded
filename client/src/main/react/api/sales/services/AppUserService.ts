/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AppUserDto } from '../models/AppUserDto';
import type { PagedModelAppUserDto } from '../models/PagedModelAppUserDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AppUserService {
  /**
   * @returns AppUserDto OK
   * @throws ApiError
   */
  public static getAppUserById({
    id,
  }: {
    id: number,
  }): CancelablePromise<AppUserDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/user/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns AppUserDto OK
   * @throws ApiError
   */
  public static updateAppUser({
    id,
    requestBody,
  }: {
    id: number,
    requestBody: AppUserDto,
  }): CancelablePromise<AppUserDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/user/{id}',
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
  public static deleteAppUser({
    id,
  }: {
    id: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/user/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns AppUserDto Created
   * @throws ApiError
   */
  public static createAppUser({
    requestBody,
  }: {
    requestBody: AppUserDto,
  }): CancelablePromise<AppUserDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/',
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
  public static changePassword({
    requestBody,
  }: {
    requestBody: string,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'PATCH',
      url: '/api/user/pwd',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelAppUserDto OK
   * @throws ApiError
   */
  public static findAppUser({
    username,
    userRole,
    fullname,
    page,
    size,
    sort,
    direction,
  }: {
    username?: string,
    userRole?: string,
    fullname?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelAppUserDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/user/find',
      query: {
        'username': username,
        'userRole': userRole,
        'fullname': fullname,
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
