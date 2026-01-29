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

import { OpenAPI, OrderFormDto, type ReportDto } from "../api/sales";
import { formatNumber } from "../utils/NumberUtils.ts";
import { useCallback } from "react";
import { useI18n } from "../context/i18n/useI18n.tsx";

function printOrder(orderId: number, sign: string, name: string) {
  const url = `${OpenAPI.BASE}/api/pdf/${orderId}/bg/${sign}/${name}`;
  window.open(url);
}

export function mapToReportDto(orderForm: OrderFormDto) {
  return {
    orderId: orderForm.orderId,
    orderType: orderForm.orderType?.typeKey,
    orderNum: orderForm.orderNum,
    customerName: orderForm.orderCustomer?.contactName,
    customerLocation: orderForm.orderCustomer?.contactLocation
  } satisfies ReportDto;
}

export function usePrintOrder() {
  const { t } = useI18n();
  return useCallback((report: ReportDto) => printOrder(
    report.orderId ?? 0,
    "original", [
      t(`~${report.orderType}.short`),
      formatNumber(report.orderNum ?? 0, t("~format.counter") ?? ""),
      report.customerName,
      report.customerLocation
    ].filter(Boolean).map(s => s?.trim()).filter(Boolean).join("-")
  ), [t]);
}