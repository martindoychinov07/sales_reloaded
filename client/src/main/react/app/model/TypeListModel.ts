import { type OrderTypeDto, OrderTypeService } from "../../api/sales";
import { type CrudFormModel, } from "@crud-daisyui/utils";
import { getCommonActions } from "./CommonListModel.ts";
import {
  getExchangeList,
  getOptionDirection,
  getOptionExchange,
  getOptionOrderEval,
  getOptionSort
} from "./OptionModel.ts";

export const TypeListModel: CrudFormModel<Parameters<typeof OrderTypeService.findOrderType>[number], OrderTypeDto> = {
  action: {
    search: OrderTypeService.findOrderType,
    create: OrderTypeService.createOrderType,
    save: OrderTypeService.updateOrderType,
    remove: OrderTypeService.deleteOrderType,
  },
  form: {
    args: {
      page: 1,
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
      columns: 16,
      items: [
        {
          span: 2,
          group: "args",
          name: "typeCounter",
          label: "~type.counter",
          type: "number",
        },
        {
          span: 4,
          group: "args",
          name: "typeEval",
          label: "~type.eval",
          type: "select",
          source: "orderEval",
        },
        {
          span: 6,
          group: "args",
          name: "typeNote",
          label: "~type.note",
          type: "search",
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

      "orderEval": () => { return getOptionOrderEval("~option.all"); },
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
          format: "~",
          source: "orderType",
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
          type: "select",
          format: "~orderEval.",
          source: "orderEval",
        },
        {
          group: "input",
          name: "typeCcp",
          label: "~type.ccp",
          type: "select",
          source: "orderCcp",
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
    options: {

      "orderEval": () => { return getOptionOrderEval(); },

      "orderCcp": async () => { return getOptionExchange(await getExchangeList()); }

    }
  },
}