/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $SettingDto = {
  properties: {
    settingId: {
      type: 'number',
      format: 'int32',
    },
    settingType: {
      type: 'Enum',
    },
    settingKey: {
      type: 'string',
      maxLength: 100,
    },
    settingNote: {
      type: 'string',
      maxLength: 300,
    },
    settingValue: {
      type: 'string',
      maxLength: 300,
    },
    settingGroup: {
      type: 'string',
      maxLength: 100,
    },
  },
} as const;
