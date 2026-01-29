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

import {type SettingDto, SettingService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const SettingListModel: ListFormModel<Parameters<typeof SettingService.findSetting>[number], SettingDto> = {
  action: {
    search: SettingService.findSetting,
    create: SettingService.createSetting,
    save: SettingService.updateSetting,
    remove: SettingService.deleteSetting,
  },
  form: {
    args: {
      settingKey: undefined,
      settingGroup: undefined,
      settingNote: undefined,

      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "settingKey",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "settingId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "settingKey",
          label: "~setting.key",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "settingGroup",
          label: "~setting.group",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "settingNote",
          label: "~setting.note",
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
        return getOptionSort(SettingListModel.table.layout.items);
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
          name: "settingId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "settingKey",
          label: "~setting.key",
          type: "text"
        },
        {
          group: "input",
          name: "settingGroup",
          label: "~setting.group",
          type: "text"
        },
        {
          group: "input",
          name: "settingNote",
          label: "~setting.note",
          type: "text"
        },
        {
          group: "input",
          name: "settingValue",
          label: "~setting.value",
          type: "text"
        },
      ]
    },
    options: {}
  },
}