import type { ModalProps } from "../../utils/modal/Modal.tsx";
import { type ContactDto, ContactService } from "../../api/sales";
import { useMemo } from "react";
import ListForm from "../../utils/ListForm.tsx";
import useModal from "../../utils/modal/useModal.tsx";
import { useI18n } from "../../context/i18n/useI18n.tsx";
import { ContactListModel } from "../model/ContactListModel.ts";

export function useContact() {
  const { t } = useI18n();
  const modalContactProps: ModalProps<ContactDto[], Parameters<typeof ContactService.findContact>[number]> = useMemo(() => (
    {
      header: <>{t("~contact.title")}</>,
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <ListForm model={ContactListModel} {...props} />
      },
    }
  ), [t]);
  return useModal(modalContactProps);
}