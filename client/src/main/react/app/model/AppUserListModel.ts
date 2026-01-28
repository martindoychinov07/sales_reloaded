import {type AppUserDto, AppUserService} from "../../api/sales";
import {
  type ListFormModel,
} from "../../utils/ListFormModel.ts";
import {getCommonActions} from "./CommonListModel.ts";
import { getOptionDirection, getOptionSort } from "./OptionModel.ts";

export const AppUserListModel: ListFormModel<Parameters<typeof AppUserService.findAppUser>[number], AppUserDto> = {
  action: {
    search: AppUserService.findAppUser,
    create: AppUserService.createAppUser,
    save: AppUserService.updateAppUser,
    remove: AppUserService.deleteAppUser
  },
  form: {
    args: {
      username: "",

      page: 0,
      size: import.meta.env.VITE_PAGE_SIZE,
      sort: "fullname",
      direction: "ASC"
    },
    action: undefined,
    selected: [],
    disabled: ["save", "cancel"],
    input: undefined,
    inputId: "userId",
  },
  fields: {
    layout: {
      variant: "inner",
      columns: 8,
      items: [
        {
          span: 2,
          group: "args",
          name: "username",
          label: "~user.username",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "userRole",
          label: "~user.role",
          type: "search",
        },
        {
          span: 2,
          group: "args",
          name: "fullname",
          label: "~user.fullname",
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

      "sort": () => {
        return getOptionSort(AppUserListModel.table.layout.items);
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
          name: "appUserId",
          label: "",
          type: "hidden"
        },
        {
          group: "input",
          name: "fullname",
          label: "~user.fullname",
          type: "text"
        },
        {
          group: "input",
          name: "username",
          label: "~user.username",
          type: "text"
        },
        {
          group: "input",
          name: "userRole",
          label: "~user.role",
          type: "text"
        },
        {
          group: "input",
          name: "userExpireDate",
          label: "~user.expireDate",
          type: "datetime",
          format: "~format.datetime",
          source: "calendar",
        },
        {
          group: "input",
          name: "userLockDate",
          label: "~user.lockDate",
          type: "datetime",
          format: "~format.datetime",
          source: "calendar",
        },
        {
          group: "input",
          name: "newPassword",
          label: "~user.newPassword",
          type: "password",
        },
      ]
    },
    options: {}
  },
}