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
import type { ContactDto } from './ContactDto';
import type { OrderEntryDto } from './OrderEntryDto';
import type { OrderTypeDto } from './OrderTypeDto';
export type OrderFormDto = {
  orderId?: number;
  orderVersion?: number;
  orderRefId?: number;
  orderState?: OrderFormDto.orderState;
  orderDate?: string;
  orderNum?: number;
  orderCounter?: number;
  orderType?: OrderTypeDto;
  orderUserId?: number;
  orderSupplier?: ContactDto;
  orderCustomer?: ContactDto;
  orderNote?: string;
  orderResp?: string;
  orderRespDate?: string;
  orderDlvd?: string;
  orderDlvdDate?: string;
  orderRcvd?: string;
  orderRcvdDate?: string;
  orderRef?: string;
  orderPayment?: string;
  orderPaymentDate?: string;
  orderEval?: number;
  orderDate1?: string;
  orderDate2?: string;
  orderText1?: string;
  orderText2?: string;
  orderRows?: number;
  orderTaxPct?: number;
  orderCcp?: string;
  orderRate?: number;
  orderDiscount?: number;
  orderSum?: number;
  orderTax?: number;
  orderTotal?: number;
  orderEntries?: Array<OrderEntryDto>;
};
export namespace OrderFormDto {
  export enum orderState {
    CANCELED = 'canceled',
    ARCHIVED = 'archived',
    DRAFT = 'draft',
    SCHEDULED = 'scheduled',
    IN_PROGRESS = 'in_progress',
    FOR_REVIEW = 'for_review',
    REVIEWED = 'reviewed',
    FOR_APPROVE = 'for_approve',
    APPROVED = 'approved',
    FINISHED = 'finished',
  }
}

