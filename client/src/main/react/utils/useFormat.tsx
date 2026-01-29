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

import {defaultFormatDatetime, formatDate, parseDate} from "./DateUtils.ts";
import { formatNumber, normalizeDecimalInput } from "./NumberUtils.ts";
import type {ClassNameRule, InputFormatter} from "./Input.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";

export function useFormat() {
  const { t } = useI18n();
  const patternDatetime = t("~format.datetime") ?? defaultFormatDatetime;
  const datetimeFormatter: InputFormatter = (value, format) => {
    const parsed = parseDate(value, !format || patternDatetime.includes(format) ? patternDatetime : format)
      ?? parseDate(value);
    if (parsed) {
      const formatted = formatDate(parsed, format);
      if (value !== formatted) return formatted;
    }
    return undefined;
  }

  const inputFormatters: { [key: string]: InputFormatter } =
  {
    "text": (value, format) => format !== undefined ? t( `${format}${value}`) : value,

    "datetime": datetimeFormatter,

    "number": (value, format, normalize) => {
      if (!format) {
        format = "0.####";
      }
      if (normalize) {
        value = normalizeDecimalInput(value);
      }
      const parsed = Number(value);
      if (isFinite(parsed)) {
        const formatted = formatNumber(parsed, format);
        if (value !== formatted) return formatted;
      }
      return undefined;
    }
  }
  return inputFormatters;
}

export function toFixed(num: number, fraction: number) {
  return Number.isFinite(num) ? num.toFixed(fraction) : undefined;
}

export function toClassName(rules: ClassNameRule[]) {
  return rules.map(c => c.addIf ? c.add : c.orElse).filter(c => c).join(" ");
}
