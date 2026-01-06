/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EntryProductDto } from './EntryProductDto';
export type OrderEntryDto = {
  entryId?: number;
  entryVersion?: number;
  entryRow?: number;
  entryProduct?: EntryProductDto;
  entryBarcode?: string;
  entryCode?: string;
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
};

