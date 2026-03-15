import { type ContactDto, ContactService } from "../../api/sales";
import { type CrudFormModel, } from "@crud-daisyui/utils";
import { getCommonActions } from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const ContactListModel: CrudFormModel<Parameters<typeof ContactService.findContact>[number], ContactDto> = {
  action: {
    search: ContactService.findContact,
    create: ContactService.createContact,
    save: ContactService.updateContact,
    remove: ContactService.deleteContact
  },
  form: {
    args: {
      contactText: "",
      contactLocation: undefined,
      contactCode: undefined,

      page: 1,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "contactName",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "contactId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 16,
      items: [
        {
          span: 3,
          group: "args",
          name: "contactCode",
          label: "~contact.code",
          type: "search",
        },
        {
          span: 3,
          group: "args",
          name: "contactLocation",
          label: "~contact.location",
          type: "search",
        },
        {
          span: 6,
          group: "args",
          name: "contactText",
          label: "~text.search",
          type: "search",
        },
        ...getCommonActions(),
      ]
    },
    options: {
      "sort": () => {
        return getOptionSort(ContactListModel.table.layout.items);
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
          name: "contactId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "contactCode",
          label: "~contact.code",
          type: "text"
        },
        {
          group: "input",
          name: "contactCode1",
          label: "~contact.code1",
          type: "text"
        },
        {
          group: "input",
          name: "contactCode2",
          label: "~contact.code2",
          type: "text"
        },
        {
          group: "input",
          name: "contactName",
          label: "~contact.name",
          type: "text"
        },
        {
          group: "input",
          name: "contactLocation",
          label: "~contact.location",
          type: "text"
        },
        {
          group: "input",
          name: "contactAddress",
          label: "~contact.address",
          type: "text"
        },
        {
          group: "input",
          name: "contactResp",
          label: "~contact.resp",
          type: "text"
        },
        // {
        //   group: "input",
        //   name: "contactCode3",
        //   label: "~contact.code3",
        //   type: "text"
        // },
      ]
    },
    options: {}
  },
}