import type { ModalProps } from "@crud-daisyui/utils";
import { CrudForm, useI18n, useModal } from "@crud-daisyui/utils";
import { type ContactDto, ContactService, UtilityService } from "../../api/sales";
import { useMemo } from "react";
import { ContactListModel } from "../model/ContactListModel.ts";

export function useContact() {
  const { t } = useI18n();
  const modalContactProps: ModalProps<ContactDto[], Parameters<typeof ContactService.findContact>[number]> = useMemo(() => (
    {
      header: <>{t("~contact.title")}</>,
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <CrudForm model={ContactListModel} onExport={UtilityService.requestXlsx} {...props} />
      },
    }
  ), [t]);
  return useModal(modalContactProps);
}