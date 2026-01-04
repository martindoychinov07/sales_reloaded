import {ProductListModel} from "../model/ProductListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function ProductAdmin() {
  return <AdminForm model={ProductListModel} />
}