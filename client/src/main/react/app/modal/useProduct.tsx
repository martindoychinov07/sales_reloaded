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