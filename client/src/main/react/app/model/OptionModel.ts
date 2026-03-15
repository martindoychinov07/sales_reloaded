import type { InputOption, LayoutModelItem } from "@crud-daisyui/utils";
import {
  type ExchangeDto,
  ExchangeService,
  OrderFormDto,
  OrderTypeDto,
  OrderTypeService,
  type SettingDto,
  SettingService
} from "../../api/sales";

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

export function getOptionOrderEval(option?: string): InputOption[] {
  return (
    [
      optionAll(option),
      { value: "none", label: "~orderEval.none" },
      { value: "push", label: "~orderEval.push" },
      { value: "pull", label: "~orderEval.pull" },
      { value: "init", label: "~orderEval.init" },
    ]
  )
}

export function getOptionOrderState(option?: string): InputOption[] {
  return [optionAll(option), ...Object.values(OrderFormDto.orderState)
    .reverse()
    .map(value => (
      { value: value, label: `~orderState.${value}`, disabled: option ? undefined : value === OrderFormDto.orderState.CANCELED }
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
  return (await ExchangeService.findExchange({ sort: "exchangeSource" })).content;
}

export function getOptionExchange(list?: ExchangeDto[], option?: string): InputOption[] {
  return [optionAll(option), ...list?.map(item => (
    { value: `${item.exchangeSource}/${item.exchangeTarget}`, label: `${item.exchangeSource} (${item.exchangeTarget})` }
  )) ?? []];
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