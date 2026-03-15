import { type TranslationDto, TranslationService } from "../../api/sales";
import { type CrudFormModel, } from "@crud-daisyui/utils";
import { getCommonActions } from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const TranslationListModel: CrudFormModel<Parameters<typeof TranslationService.findTranslation>[number], TranslationDto> = {
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

      page: 1,
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
      columns: 16,
      items: [
        {
          span: 4,
          group: "args",
          name: "translationKey",
          label: "~translation.key",
          type: "search",
        },
        {
          span: 4,
          group: "args",
          name: "en",
          label: "~translation.en",
          type: "search",
        },
        {
          span: 4,
          group: "args",
          name: "bg",
          label: "~translation.bg",
          type: "search",
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