import {TranslationListModel} from "../model/TranslationListModel.ts";
import {AdminForm} from "./AdminForm.tsx";

export function TranslationAdmin() {
  return <AdminForm model={TranslationListModel} />
}