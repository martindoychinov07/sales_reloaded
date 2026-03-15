/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $AppUserDto = {
  properties: {
    userId: {
      type: 'number',
      format: 'int32',
    },
    username: {
      type: 'string',
      maxLength: 100,
    },
    userRole: {
      type: 'string',
      maxLength: 100,
    },
    userExpireDate: {
      type: 'string',
      format: 'date-time',
    },
    userLockDate: {
      type: 'string',
      format: 'date-time',
    },
    fullname: {
      type: 'string',
      maxLength: 100,
    },
    newPassword: {
      type: 'string',
    },
  },
} as const;
