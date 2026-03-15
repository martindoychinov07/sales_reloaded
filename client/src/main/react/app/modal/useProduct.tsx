import type { ListMetadata, ModalProps, SelectionType } from "@crud-daisyui/utils";
import { CrudForm, useI18n, useModal } from "@crud-daisyui/utils";
import { type ProductDto, ProductService, UtilityService } from "../../api/sales";
import { useMemo } from "react";
import { ProductListModel } from "../model/ProductListModel.ts";

export function useProduct() {
  const { t } = useI18n();
  const modalProductProps: ModalProps<ProductDto[], Parameters<typeof ProductService.findProduct>[number] & ListMetadata & SelectionType> = useMemo(() => (
    {
      header: <>{t("~product.title")}</>,
      variant: "full",
      noWrapper: true,
      children: (props) => {
        return <CrudForm model={ProductListModel} onExport={UtilityService.requestXlsx} {...props} />
      },
    }
  ), [t]);
  return useModal(modalProductProps);
}