import {type OrderTypeDto, OrderTypeService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

export const TypeListModel: ListFormModel<Parameters<typeof OrderTypeService.findOrderType>[number], OrderTypeDto> = {
  action: {
    search: OrderTypeService.findOrderType,
    create: OrderTypeService.createOrderType,
    save: OrderTypeService.updateOrderType,
    remove: OrderTypeService.deleteOrderType,
  },
  form: {
    args: {
    },
    paging: {
      page: 0,
      size: 100,
      sort: "typeOrder",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
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
      "size": () => {
        return getOptionSize();
      },

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
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "typeOrder",
          label: "~type.order",
          type: "number"
        },
        {
          group: "input",
          name: "typeKey",
          label: "~type.key",
          type: "text",
          pattern: "~"
        },
        {
          group: "input",
          name: "typeNum",
          label: "~type.num",
          type: "number"
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
          pattern: "~orderEval."
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
          pattern: "~pattern.percent",
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