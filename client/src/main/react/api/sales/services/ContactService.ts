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
  public static updateContact({
    requestBody,
  }: {
    requestBody: ContactDto,
  }): CancelablePromise<ContactDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/contact/updateContact',
      body: requestBody,
      mediaType: 'application/json',
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
      url: '/api/contact/createContact',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
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
   * @returns string OK
   * @throws ApiError
   */
  public static findLocation({
    contactCode1,
  }: {
    contactCode1: string,
  }): CancelablePromise<Array<string>> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/contact/findLocation',
      query: {
        'contactCode1': contactCode1,
      },
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
    contactName,
    contactLocation,
    contactCode1,
    contactCode2,
    page,
    size,
    sort,
    direction,
  }: {
    contactName?: string,
    contactLocation?: string,
    contactCode1?: string,
    contactCode2?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelContactDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/contact/findContact',
      query: {
        'contactName': contactName,
        'contactLocation': contactLocation,
        'contactCode1': contactCode1,
        'contactCode2': contactCode2,
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
  public static deleteContact({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/contact/deleteContact',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
