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
import { type ProductDto, ProductService } from "../../api/sales";
import { useMemo } from "react";
import ListForm from "../../utils/ListForm.tsx";
import { ProductListModel } from "../model/ProductListModel.ts";
import useModal, { type SelectionType } from "../../utils/modal/useModal.tsx";
import { useI18n } from "../../context/i18n/useI18n.tsx";
import type { ListMetadata } from "../../utils/ListFormModel.ts";

export function useProduct() {
  const { t } = useI18n();
  const modalProductProps: ModalProps<ProductDto[], Parameters<typeof ProductService.findProduct>[number] & ListMetadata & SelectionType> = useMemo(() => (
    {
      header: <>{t("~product.title")}</>,
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <ListForm model={ProductListModel} {...props} />
      },
    }
  ), [t]);
  return useModal(modalProductProps);
}