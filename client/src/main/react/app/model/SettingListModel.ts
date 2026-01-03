import {type SettingDto, SettingService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize, getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

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
    },
    paging: {
      page: 0,
      size: 100,
      sort: "settingKey",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
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
      "size": () => {
        return getOptionSize();
      },

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