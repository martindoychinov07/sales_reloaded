import {ContactListModel} from "../model/ContactListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function ContactAdmin() {
  return <AdminForm model={ContactListModel} />
}