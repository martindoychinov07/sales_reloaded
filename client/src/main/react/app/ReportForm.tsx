import { ReportListModel } from "./model/ReportListModel.ts";
import { CrudForm, useConfirm, useI18n } from "@crud-daisyui/utils";
import { OrderFormService, type ReportDto, UtilityService } from "../api/sales";
import { useNavigate } from "react-router-dom";
import { useContact } from "./modal/useContact.tsx";
import { useProduct } from "./modal/useProduct.tsx";
import { usePrintOrder } from "./usePrint.tsx";

export function ReportForm() {
  const { t } = useI18n();
  const navigate = useNavigate();
  const printOrder = usePrintOrder();
  const modalConfirm = useConfirm();
  const modalContact = useContact();
  const modalProduct = useProduct();

  async function onAction(action: string, payload?: ReportDto[], path?: string) {
    const selected = payload?.map(order => order.orderId as number).filter(id => id);
    if (["edit", "create", "copy", "delete", "print"].includes(action)) {
      if (!payload || !selected || selected.length === 0) {
        return undefined;
      }

      if (selected?.length > 1) {
        const question = await modalConfirm.value({ title: `${selected.length} ${t("~confirm.many")}`, content: t("~confirm.refusal") });
        if (question.result?.confirmed) {
          return undefined;
        }
      }

      if (action === "delete") {
        const question = await modalConfirm.value({ title: t("~confirm.delete.title"), content: t("~confirm.question") });
        if (question.result?.confirmed) {
          for (let i = 0; i < payload?.length; i++) {
            await OrderFormService.deleteOrder( { id: payload[i].orderId as number })
          }
        }
        return "search";
      }
      else if (action === "print") {
        for (let i = 0; i < payload?.length; i++) {
          printOrder(payload[i]);
        }
        return undefined;
      }
      else {
        navigate("/app/order", { state: { orderIds: selected, target: action } });
      }
      return undefined;
    }
    else if (action === "contact") {
      const res = await modalContact.value({});
      return res.result?.at(-1)?.contactName;
    }
    else if (action === "product") {
      const res = await modalProduct.value({});
      return res.result?.at(-1)?.productName;
    }
    return action;
  }

  return <CrudForm
    model={ReportListModel}
    rowClassName={(entry, row) => entry?.group }
    onAction={onAction}
    onExport={UtilityService.requestXlsx}
  >
    {modalConfirm.component}
    {modalContact.component}
    {modalProduct.component}
  </CrudForm>
}