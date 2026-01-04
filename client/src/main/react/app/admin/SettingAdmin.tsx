import {SettingListModel} from "../model/SettingListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function SettingAdmin() {
  return <AdminForm model={SettingListModel} />
}