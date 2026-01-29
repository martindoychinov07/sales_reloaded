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

import {type ExchangeDto, ExchangeService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const ExchangeListModel: ListFormModel<Parameters<typeof ExchangeService.findExchange>[number], ExchangeDto> = {
  action: {
    search: ExchangeService.findExchange,
    create: ExchangeService.createExchange,
    save: ExchangeService.updateExchange,
    remove: ExchangeService.deleteExchange,
  },
  form: {
    args: {
      exchangeBase: undefined,
      exchangeTarget: undefined,

      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "exchangeDate",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "exchangeId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "exchangeBase",
          label: "~exchange.base",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "exchangeTarget",
          label: "~exchange.target",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          label: "",
        },
        {
          span: 2,
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
        return getOptionSort(ExchangeListModel.table.layout.items);
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
          name: "exchangeId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "exchangeBase",
          label: "~exchange.base",
          type: "text"
        },
        {
          group: "input",
          name: "exchangeTarget",
          label: "~exchange.target",
          type: "text"
        },
        {
          group: "input",
          name: "exchangeRate",
          label: "~exchange.rate",
          type: "number",
          format: "~format.rate",
          rules: {
            required: true,
          }
        },
        {
          group: "input",
          name: "exchangeDate",
          label: "~exchange.date",
          type: "datetime",
          format: "~format.datetime"
        },
      ]
    },
    options: {}
  },
}