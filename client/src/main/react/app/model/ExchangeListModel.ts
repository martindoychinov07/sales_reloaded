import {type ExchangeDto, ExchangeService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

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
    },
    paging: {
      page: 0,
      size: 100,
      sort: "exchangeDate",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
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
      "size": () => {
        return getOptionSize();
      },

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
          pattern: "~pattern.rate"
        },
        {
          group: "input",
          name: "exchangeDate",
          label: "~exchange.date",
          type: "datetime",
          pattern: "~pattern.datetime"
        },
      ]
    },
    options: {}
  },
}