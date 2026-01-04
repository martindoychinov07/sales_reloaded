import {ExchangeListModel} from "../model/ExchangeListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function ExchangeAdmin() {
  return <AdminForm model={ExchangeListModel} />
}