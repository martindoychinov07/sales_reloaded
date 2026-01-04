import {AdminForm} from "./AdminForm.tsx";
import {TypeListModel} from "../model/TypeListModel.ts";

export function TypeAdmin() {
  return <AdminForm model={TypeListModel} />
}