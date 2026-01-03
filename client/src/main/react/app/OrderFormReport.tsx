import { ReportListModel } from "./model/ReportListModel.ts";
import { ListForm } from "../utils/ListForm.tsx";
import { OpenAPI, type OrderFormView, PdfService } from "../api/sales";
import { useNavigate } from "react-router-dom";
import { useI18n } from "../context/i18n/useI18n.tsx";

export function OrderFormReport() {
  const { t } = useI18n();
  const navigate = useNavigate();

  async function print(orderId: number, subtitle: string) {
    const url = `${OpenAPI.BASE}/api/pdf/${orderId}?lang=bg&subtitle=original`;
    window.open(url);
  }

  async function onAction(action: string, payload?: OrderFormView) {
    if (action === "edit") {
      if (payload && payload.orderId) {
        navigate("/app/order", { state: { orderId: payload?.orderId, target: "edit" } });
      }
    }
    else if (action === "create") {
      if (payload && payload.orderId) {
        navigate("/app/order", { state: { orderId: payload?.orderId, target: "create" } });
      }
    }
    else if (action === "copy") {
      if (payload && payload.orderId) {
        navigate("/app/order", { state: { orderId: payload?.orderId, target: "copy" } });
      }
    }
    else if (action === "delete") {
      if (payload && payload.orderId) {
        navigate("/app/order", { state: { orderId: payload?.orderId, target: "delete" } });
      }
    }
    else if (action === "print") {
      if (payload && payload.orderId) {
        print(payload.orderId, t("~print.original") ?? "");
        print(payload.orderId, "");
      }
    }
    return true;
  }

  return <ListForm
    model={ReportListModel}
    close={modal => {
    }}
    onAction={onAction}
  />
}