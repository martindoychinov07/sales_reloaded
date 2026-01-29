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
export const $OrderEntryDto = {
  properties: {
    entryId: {
      type: 'number',
      format: 'int32',
    },
    entryVersion: {
      type: 'number',
      format: 'int32',
    },
    entryRow: {
      type: 'number',
      format: 'int32',
    },
    entryProduct: {
      type: 'EntryProductDto',
    },
    entryBarcode: {
      type: 'string',
      maxLength: 50,
    },
    entryCode: {
      type: 'string',
      maxLength: 50,
    },
    entryLabel: {
      type: 'string',
      maxLength: 200,
    },
    entryUnits: {
      type: 'number',
      format: 'int32',
    },
    entryMeasure: {
      type: 'string',
      maxLength: 30,
    },
    entryAvailable: {
      type: 'number',
    },
    entryQuantity: {
      type: 'number',
    },
    entryPrice: {
      type: 'number',
    },
    entryDiscountPct: {
      type: 'number',
    },
    entryDiscount: {
      type: 'number',
    },
    entrySum: {
      type: 'number',
    },
    entryTax: {
      type: 'number',
    },
    entryTotal: {
      type: 'number',
    },
  },
} as const;
