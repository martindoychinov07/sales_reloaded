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

import { ReportListModel } from "./model/ReportListModel.ts";
import ListForm from "../utils/ListForm.tsx";
import { OrderFormService, type ReportDto } from "../api/sales";
import { useNavigate } from "react-router-dom";
import { useI18n } from "../context/i18n/useI18n.tsx";
import { useContact } from "./modal/useContact.tsx";
import { useProduct } from "./modal/useProduct.tsx";
import { useConfirm } from "../utils/modal/useConfirm.tsx";
import { usePrintOrder } from "./usePrint.tsx";

export function ReportForm() {
  const { t } = useI18n();
  const navigate = useNavigate();
  const printOrder = usePrintOrder();
  const modalConfirm = useConfirm();
  const modalContact = useContact();
  const modalProduct = useProduct();

  async function onAction(action: string, payload?: ReportDto[], path?: string) {
    const selected = payload?.at(-1);
    if (action === "edit") {
      if (selected && selected.orderId) {
        navigate("/app/order", { state: { orderId: selected?.orderId, target: "edit" } });
      }
      return undefined;
    }
    else if (action === "create") {
      if (selected && selected.orderId) {
        navigate("/app/order", { state: { orderId: selected?.orderId, target: "create" } });
      }
      return undefined;
    }
    else if (action === "copy") {
      if (selected && selected.orderId) {
        navigate("/app/order", { state: { orderId: selected?.orderId, target: "copy" } });
      }
      return undefined;
    }
    else if (action === "delete") {
      if (selected && selected.orderId) {
        const confirmation = await modalConfirm.value({title: t("~confirm.delete.title"), content: t("~confirm.question")});
        if (confirmation.result?.confirmed) {
          await OrderFormService.deleteOrder( { id: selected.orderId });
        }
      }
      return "search";
    }
    else if (action === "print") {
      if (selected && selected.orderId) {
        // await print(selected.orderId, "copy");
        printOrder(selected);
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

  return <ListForm
    model={ReportListModel}
    rowClassName={(entry, row) => entry?.group }
    onAction={onAction}
  >
    {modalConfirm.component}
    {modalContact.component}
    {modalProduct.component}
  </ListForm>
}