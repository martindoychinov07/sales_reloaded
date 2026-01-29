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
export const $OrderFormDto = {
  properties: {
    orderId: {
      type: 'number',
      format: 'int32',
    },
    orderVersion: {
      type: 'number',
      format: 'int32',
    },
    orderRefId: {
      type: 'number',
      format: 'int32',
    },
    orderState: {
      type: 'Enum',
    },
    orderDate: {
      type: 'string',
      format: 'date-time',
    },
    orderNum: {
      type: 'number',
      format: 'int64',
    },
    orderCounter: {
      type: 'number',
      format: 'int32',
    },
    orderType: {
      type: 'OrderTypeDto',
    },
    orderUserId: {
      type: 'number',
      format: 'int32',
    },
    orderSupplier: {
      type: 'ContactDto',
    },
    orderCustomer: {
      type: 'ContactDto',
    },
    orderNote: {
      type: 'string',
      maxLength: 300,
    },
    orderResp: {
      type: 'string',
      maxLength: 100,
    },
    orderRespDate: {
      type: 'string',
      format: 'date-time',
    },
    orderDlvd: {
      type: 'string',
      maxLength: 100,
    },
    orderDlvdDate: {
      type: 'string',
      format: 'date-time',
    },
    orderRcvd: {
      type: 'string',
      maxLength: 100,
    },
    orderRcvdDate: {
      type: 'string',
      format: 'date-time',
    },
    orderRef: {
      type: 'string',
      maxLength: 100,
    },
    orderPayment: {
      type: 'string',
      maxLength: 20,
    },
    orderPaymentDate: {
      type: 'string',
      format: 'date-time',
    },
    orderEval: {
      type: 'number',
      format: 'int32',
    },
    orderDate1: {
      type: 'string',
      format: 'date-time',
    },
    orderDate2: {
      type: 'string',
      format: 'date-time',
    },
    orderText1: {
      type: 'string',
      maxLength: 100,
    },
    orderText2: {
      type: 'string',
      maxLength: 100,
    },
    orderRows: {
      type: 'number',
      format: 'int32',
    },
    orderTaxPct: {
      type: 'number',
    },
    orderCcp: {
      type: 'string',
      maxLength: 7,
    },
    orderRate: {
      type: 'number',
    },
    orderDiscount: {
      type: 'number',
    },
    orderSum: {
      type: 'number',
    },
    orderTax: {
      type: 'number',
    },
    orderTotal: {
      type: 'number',
    },
    orderEntries: {
      type: 'array',
      contains: {
        type: 'OrderEntryDto',
      },
    },
  },
} as const;
