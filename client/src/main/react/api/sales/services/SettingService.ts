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
  public static updateSetting({
    requestBody,
  }: {
    requestBody: SettingDto,
  }): CancelablePromise<SettingDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/setting/updateSetting',
      body: requestBody,
      mediaType: 'application/json',
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
      url: '/api/setting/createSetting',
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
      url: '/api/setting/findSetting',
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
  /**
   * @returns any OK
   * @throws ApiError
   */
  public static deleteSetting({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/setting/deleteSetting',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
