import type { InputOption } from "../../utils/Input.tsx";
import {
  type ExchangeDto,
  ExchangeService,
  OrderFormDto,
  OrderTypeDto,
  OrderTypeService, type SettingDto,
  SettingService
} from "../../api/sales";
import type { LayoutModelItem } from "../../utils/LayoutModel.ts";

function optionAll(option?: string) {
  return { label: option === undefined ? "" : option || "~option.all", value: option !== undefined ? "" : undefined }
}

export function getOptionPayment(option?: string): InputOption[] {
  return (
    [
      optionAll(option),
      { value: "bank", label: "~payment.bank" },
      { value: "cash", label: "~payment.cash" },
    ]
  )
}

export function getOptionOrderState(option?: string): InputOption[] {
  return [optionAll(option), ...Object.values(OrderFormDto.orderState)
    .reverse()
    .map(value => (
      { value: value, label: `~orderState.${value}` }
    ))];
}

export async function getOrderTypeList() {
  return (await OrderTypeService.findOrderType({ sort: "typeIndex" })).content;
}

export function getOptionOrderType(list?: OrderTypeDto[], option?: string): InputOption[] {
  return [optionAll(option), ...list ?.map(item => (
    { value: `${item.typeId}`, label: `~${item.typeKey}` }
  )) ?? []];
}

export async function getExchangeList() {
  return (await ExchangeService.findExchange({ sort: "exchangeBase" })).content;
}

export function getOptionExchange(list?: ExchangeDto[]): InputOption[] {
  return list?.map(item => (
    { value: `${item.exchangeTarget}/${item.exchangeBase}`, label: `${item.exchangeTarget} (${item.exchangeBase})` }
  )) ?? [];
}

export async function getViewList(name: string) {
  return (await SettingService.findSetting({ settingGroup: name, sort: "settingKey" })).content;
}

export function getOptionView(list?: SettingDto[]): InputOption[] {
  return list?.map((item, index) => (
    { value: `${String(index).padStart(2, "0")}:${item.settingValue}`, label: `~${item.settingNote}` }
  )) ?? [];
}

export function getOptionSort<T>(items?: LayoutModelItem<T>[]): InputOption[] {
  return [...items
    ?.filter(item => item.type !== "hidden" && item.mode !== "hidden")
    .map(item => ({ value: item.name, label: item.label })) ?? []];
}

export function getOptionDirection(): InputOption[] {
  return (
    [
      { value: "ASC", label: "A..Z" },
      { value: "DESC", label: "Z..A" },
    ]
  )
}