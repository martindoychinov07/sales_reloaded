import {type ContactDto, ContactService} from "../../api/sales";
import {
  getOptionDirection,
  getOptionSize,
  getOptionSort,
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";

export const ContactListModel: ListFormModel<Parameters<typeof ContactService.findContact>[number], ContactDto> = {
  action: {
    search: ContactService.findContact,
    create: ContactService.createContact,
    save: ContactService.updateContact,
    remove: ContactService.deleteContact
  },
  form: {
    args: {
      contactName: "",
      contactLocation: undefined,
      contactCode1: undefined,
      contactCode2: undefined,
    },
    paging: {
      page: 0,
      size: 100,
      sort: "contactName",
      direction: "ASC"
    },
    action: undefined,
    selected: undefined,
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "contactId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "contactCode1",
          label: "~contact.code1",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "contactCode2",
          label: "~contact.code2",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "contactName",
          label: "~contact.name",
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
          label: "~contacta.address",
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