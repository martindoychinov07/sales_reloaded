import {AppUserListModel} from "../model/AppUserListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function AppUserAdmin() {
  return <AdminForm model={AppUserListModel} />
}