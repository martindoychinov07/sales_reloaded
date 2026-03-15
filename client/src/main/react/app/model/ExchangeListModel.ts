import { type ExchangeDto, ExchangeService } from "../../api/sales";
import { type CrudFormModel } from "@crud-daisyui/utils";
import { getCommonActions } from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const ExchangeListModel: CrudFormModel<Parameters<typeof ExchangeService.findExchange>[number], ExchangeDto> = {
  action: {
    search: ExchangeService.findExchange,
    create: ExchangeService.createExchange,
    save: ExchangeService.updateExchange,
    remove: ExchangeService.deleteExchange,
  },
  form: {
    args: {
      exchangeTarget: undefined,
      exchangeSource: undefined,

      page: 1,
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
      columns: 16,
      items: [
        {
          span: 4,
          group: "args",
          name: "exchangeSource",
          label: "~exchange.source",
          type: "search",
        },
        {
          span: 4,
          group: "args",
          name: "exchangeTarget",
          label: "~exchange.target",
          type: "search",
        },
        {
          span: 4,
          group: "args",
          label: "",
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
          name: "exchangeSource",
          label: "~exchange.source",
          type: "text"
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