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
export const $EntryProductDto = {
  properties: {
    productId: {
      type: 'number',
      format: 'int32',
    },
    productVersion: {
      type: 'number',
      format: 'int32',
    },
    productRefId: {
      type: 'number',
      format: 'int32',
    },
    productName: {
      type: 'string',
      maxLength: 200,
    },
    productUnits: {
      type: 'number',
      format: 'int32',
    },
    productMeasure: {
      type: 'string',
      maxLength: 30,
    },
    productAvailable: {
      type: 'number',
      format: 'int32',
    },
  },
} as const;
