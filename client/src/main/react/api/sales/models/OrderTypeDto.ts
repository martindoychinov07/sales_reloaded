/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type OrderTypeDto = {
  typeId?: number;
  typeCounter?: number;
  typeIndex?: number;
  typeKey?: string;
  typeNum?: number;
  typePrint?: string;
  typeEval?: OrderTypeDto.typeEval;
  typeCcp?: string;
  typeNote?: string;
  typeTaxPct?: number;
};
export namespace OrderTypeDto {
  export enum typeEval {
    NONE = 'none',
    INIT = 'init',
    PUSH = 'push',
    PULL = 'pull',
  }
}

