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