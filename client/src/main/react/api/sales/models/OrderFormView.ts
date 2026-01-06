/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type OrderFormView = {
  orderId?: number;
  orderState?: OrderFormView.orderState;
  orderCcp?: string;
  orderRate?: number;
  orderNum?: number;
  orderDate?: string;
  orderSum?: number;
  orderTax?: number;
  orderTotal?: number;
  orderCustomerContactName?: string;
  orderTypeTypeKey?: string;
  orderSupplierContactName?: string;
};
export namespace OrderFormView {
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

