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

import {type ProductDto, ProductService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort, getOptionView, getViewList } from "./OptionModel.ts";

type ProductArgs = Parameters<typeof ProductService.findProduct>[number];
export const ProductListModel: ListFormModel<ProductArgs, ProductDto> = {
  action: {
    search: async (args: ProductArgs) => {
      // console.trace("call", {args});
      return ProductService.findProduct(args);
    },
    create: ProductService.createProduct,
    save: ProductService.updateProduct,
    remove: ProductService.deleteProduct,
  },
  form: {
    args: {
      productText: undefined,
      productName: undefined,
      productNote: undefined,

      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "productName",
      direction: "ASC",
      view: "00:^(?:productCode(.)|productPrice(.))",
    },
    action: undefined,
    selected: [],
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
      "sort": () => {
        return getOptionSort(ProductListModel.table.layout.items);
      },

      "direction": () => {
        return getOptionDirection();
      },

      "view": async () => { return getOptionView(await getViewList("product.view")); },
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
          name: "productCode1",
          label: "~product.code1",
          type: "text"
        },
        {
          group: "input",
          name: "productCode2",
          label: "~product.code2",
          type: "text"
        },
        {
          group: "input",
          name: "productCode3",
          label: "~product.code3",
          type: "text"
        },
        {
          group: "input",
          name: "productCode4",
          label: "~product.code4",
          type: "text"
        },
        {
          group: "input",
          name: "productCode5",
          label: "~product.code5",
          type: "text"
        },
        {
          group: "input",
          name: "productCode6",
          label: "~product.code6",
          type: "text"
        },
        {
          group: "input",
          name: "productCode7",
          label: "~product.code7",
          type: "text"
        },
        {
          group: "input",
          name: "productCode8",
          label: "~product.code8",
          type: "text"
        },
        {
          group: "input",
          name: "productCode9",
          label: "~product.code9",
          type: "text"
        },
        {
          group: "input",
          name: "productBarcode",
          label: "~product.barcode",
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
          format: "~format.quantity"
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
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice1",
          label: "~product.price1",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice2",
          label: "~product.price2",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice3",
          label: "~product.price3",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice4",
          label: "~product.price4",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice5",
          label: "~product.price5",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice6",
          label: "~product.price6",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice7",
          label: "~product.price7",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice8",
          label: "~product.price8",
          type: "number",
          format: "~format.price"
        },
        {
          group: "input",
          name: "productPrice9",
          label: "~product.price9",
          type: "number",
          format: "~format.price"
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
          format: "~format.quantity",
        },

      ]
    },
    options: {},
    defaults: (entry) => {
      return {
        productUnits: entry?.productUnits,
        productMeasure: entry?.productMeasure,
        productCy: entry?.productCy,
        productAvailable: 0,
        productPrice: 0,
      }
    }
  },
}