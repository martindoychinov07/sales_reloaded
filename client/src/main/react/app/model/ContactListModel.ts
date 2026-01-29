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

import {type ContactDto, ContactService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

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

      page: 0,
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
      columns: 8,
      items: [
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
          name: "contactLocation",
          label: "~contact.location",
          type: "search",
        },
        {
          span: 1,
          group: "args",
          name: "contactCode1",
          label: "~contact.code1",
          type: "search",
        },
        {
          span: 1,
          group: "args",
          name: "contactCode2",
          label: "~contact.code2",
          type: "search",
        },
        {
          span: 1,
          group: "args",
          name: "contactCode",
          label: "~contact.code",
          type: "search",
        },
        {
          span: 1,
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