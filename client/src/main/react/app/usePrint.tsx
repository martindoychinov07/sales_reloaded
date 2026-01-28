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