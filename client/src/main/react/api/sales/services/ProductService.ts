/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagedModelProductDto } from '../models/PagedModelProductDto';
import type { Product } from '../models/Product';
import type { ProductDto } from '../models/ProductDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ProductService {
  /**
   * @returns ProductDto OK
   * @throws ApiError
   */
  public static updateProduct({
    requestBody,
  }: {
    requestBody: ProductDto,
  }): CancelablePromise<ProductDto> {
    return __request(OpenAPI, {
      method: 'PUT',
      url: '/api/product/updateProduct',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns ProductDto Created
   * @throws ApiError
   */
  public static createProduct({
    requestBody,
  }: {
    requestBody: ProductDto,
  }): CancelablePromise<ProductDto> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/product/createProduct',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns Product OK
   * @throws ApiError
   */
  public static getProductById({
    id,
  }: {
    id: number,
  }): CancelablePromise<Product> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/product/{id}',
      path: {
        'id': id,
      },
      errors: {
        409: `Conflict`,
      },
    });
  }
  /**
   * @returns PagedModelProductDto OK
   * @throws ApiError
   */
  public static findProduct({
    productCode,
    productName,
    productNote,
    page,
    size,
    sort,
    direction,
  }: {
    productCode?: string,
    productName?: string,
    productNote?: string,
    page?: number,
    size?: number,
    sort?: string,
    direction?: 'ASC' | 'DESC',
  }): CancelablePromise<PagedModelProductDto> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/product/findProduct',
      query: {
        'productCode': productCode,
        'productName': productName,
        'productNote': productNote,
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
  public static deleteProduct({
    requestBody,
  }: {
    requestBody: number,
  }): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: 'DELETE',
      url: '/api/product/deleteProduct',
      body: requestBody,
      mediaType: 'application/json',
      errors: {
        409: `Conflict`,
      },
    });
  }
}
