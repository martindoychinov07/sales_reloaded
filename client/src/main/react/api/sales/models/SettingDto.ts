/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type SettingDto = {
  settingId?: number;
  settingType?: SettingDto.settingType;
  settingKey?: string;
  settingNote?: string;
  settingValue?: string;
  settingGroup?: string;
};
export namespace SettingDto {
  export enum settingType {
    TYPE_TEXT = 'type_text',
    TYPE_NUMBER = 'type_number',
  }
}

