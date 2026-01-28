/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ReportDto = {
  reportId?: number;
  orderId?: number;
  entryId?: number;
  orderState?: ReportDto.orderState;
  orderDate?: string;
  orderNum?: number;
  orderType?: string;
  supplierName?: string;
  supplierLocation?: string;
  customerName?: string;
  customerLocation?: string;
  orderCcp?: string;
  orderCy?: string;
  orderRate?: number;
  orderResp?: string;
  orderPayment?: string;
  orderPaymentDate?: string;
  productName?: string;
  entryRow?: number;
  entryLabel?: string;
  entryUnits?: number;
  entryMeasure?: string;
  entryAvailable?: number;
  entryQuantity?: number;
  entryPrice?: number;
  entryDiscountPct?: number;
  entryDiscount?: number;
  entrySum?: number;
  entryTax?: number;
  entryTotal?: number;
  group?: string;
};
export namespace ReportDto {
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

