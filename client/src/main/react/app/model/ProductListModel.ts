import {type ProductDto, ProductService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

export const ProductListModel: ListFormModel<Parameters<typeof ProductService.findProduct>[number], ProductDto> = {
  action: {
    search: ProductService.findProduct,
    create: ProductService.createProduct,
    save: ProductService.updateProduct,
    remove: ProductService.deleteProduct,
  },
  form: {
    args: {
      productCode: undefined,
      productName: undefined,
      productNote: undefined,
    },
    paging: {
      page: 0,
      size: 100,
      sort: "productName",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
    disabled: ["save", "cancel"],
    mode: undefined,
    input: undefined,
    inputId: "productId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "productCode",
          label: "~product.code",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "productName",
          label: "~product.name",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "productNote",
          label: "~product.note",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "search",
          label: "~action.search",
          type: "submit",
        },
        ...getCommonActions()
      ],
    },
    options: {
      "size": () => {
        return getOptionSize();
      },

      "sort": () => {
        return getOptionSort(ProductListModel.table.layout.items);
      },

      "direction": () => {
        return getOptionDirection();
      },
    },
  },
  table: {
    layout: {
      variant: "table",
      items: [
        {
          group: "input",
          name: "productId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "productCode",
          label: "~product.code",
          type: "text"
        },
        {
          group: "input",
          name: "productName",
          label: "~product.name",
          type: "text"
        },
        {
          group: "input",
          name: "productNote",
          label: "~product.note",
          type: "text"
        },
        {
          group: "input",
          name: "productUnits",
          label: "~product.units",
          type: "number",
          pattern: "~pattern.quantity"
        },
        {
          group: "input",
          name: "productMeasure",
          label: "~product.measure",
          type: "text"
        },
        {
          group: "input",
          name: "productPrice",
          label: "~product.price",
          type: "number",
          pattern: "~pattern.price"
        },
        {
          group: "input",
          name: "productCy",
          label: "~product.cy",
          type: "text",
        },
        {
          group: "input",
          name: "productAvailable",
          label: "~product.available",
          type: "number",
          pattern: "~pattern.quantity"
        },
      ]
    },
    options: {}
  },
}