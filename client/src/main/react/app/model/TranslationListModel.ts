import {type TranslationDto, TranslationService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

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
    },
    paging: {
      page: 0,
      size: 100,
      sort: "translationKey",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
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
      "size": () => {
        return getOptionSize();
      },

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