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

import {type OrderTypeDto, OrderTypeService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const TypeListModel: ListFormModel<Parameters<typeof OrderTypeService.findOrderType>[number], OrderTypeDto> = {
  action: {
    search: OrderTypeService.findOrderType,
    create: OrderTypeService.createOrderType,
    save: OrderTypeService.updateOrderType,
    remove: OrderTypeService.deleteOrderType,
  },
  form: {
    args: {
      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "typeIndex",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "typeId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 8,
          group: "args",
          name: "search",
          label: "~action.search",
          type: "submit",
        },
        ...getCommonActions()
      ]
    },
    options: {
      "sort": () => {
        return getOptionSort(TypeListModel.table.layout.items);
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
          name: "typeId",
          label: "~type.id",
          type: "number",
          mode: "disabled"
        },
        {
          group: "input",
          name: "typeIndex",
          label: "~type.index",
          type: "number",
          rules: {
            required: true,
          }
        },
        {
          group: "input",
          name: "typeKey",
          label: "~type.key",
          type: "text",
          format: "~"
        },
        {
          group: "input",
          name: "typeCounter",
          label: "~type.counter",
          type: "number",
          rules: {
            required: true,
          }
        },
        {
          group: "input",
          name: "typeNum",
          label: "~type.num",
          type: "number",
        },
        {
          group: "input",
          name: "typePrint",
          label: "~type.print",
          type: "text"
        },
        {
          group: "input",
          name: "typeEval",
          label: "~type.eval",
          type: "text",
          format: "~orderEval."
        },
        {
          group: "input",
          name: "typeCcp",
          label: "~type.ccp",
          type: "text"
        },
        {
          group: "input",
          name: "typeTaxPct",
          label: "~type.taxPct",
          type: "number",
          format: "~format.percent",
        },
        {
          group: "input",
          name: "typeNote",
          label: "~type.note",
          type: "text"
        },
      ]
    },
    options: {}
  },
}