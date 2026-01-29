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

import {type TranslationDto, TranslationService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const TranslationListModel: ListFormModel<Parameters<typeof TranslationService.findTranslation>[number], TranslationDto> = {
  action: {
    search: TranslationService.findTranslation,
    create: TranslationService.createTranslation,
    save: TranslationService.updateTranslation,
    remove: TranslationService.deleteTranslation,
  },
  form: {
    args: {
      translationKey: undefined,
      en: undefined,
      bg: undefined,

      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "translationKey",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "translationId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "translationKey",
          label: "~translation.key",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "en",
          label: "~translation.en",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "bg",
          label: "~translation.bg",
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
      ]
    },
    options: {
      "sort": () => {
        return getOptionSort(TranslationListModel.table.layout.items);
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
          name: "translationId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "translationKey",
          label: "~translation.key",
          type: "text"
        },
        {
          group: "input",
          name: "en",
          label: "~translation.en",
          type: "text"
        },
        {
          group: "input",
          name: "bg",
          label: "~translation.bg",
          type: "text"
        },
        {
          group: "input",
          name: "t1",
          label: "~translation.t1",
          type: "text"
        },
        {
          group: "input",
          name: "t2",
          label: "~translation.t2",
          type: "text"
        },
        {
          group: "input",
          name: "t3",
          label: "~translation.t3",
          type: "text"
        },
      ]
    },
    options: {}
  },
}