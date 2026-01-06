import {defaultPatternDatetime, formatDate, parseDate} from "./DateUtils.ts";
import {formatNumber} from "./NumberUtils.ts";
import type {ClassNameRule, InputFormatter} from "./Input.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";

export function useFormat() {
  const { t } = useI18n();
  const patternDatetime = t("~pattern.datetime") ?? defaultPatternDatetime;
  const datetimeFormatter: InputFormatter = (value, pattern) => {
    const parsed = parseDate(value, !pattern || patternDatetime.includes(pattern) ? patternDatetime : pattern)
      ?? parseDate(value);
    if (parsed) {
      const formatted = formatDate(parsed, pattern);
      if (value !== formatted) return formatted;
    }
    return undefined;
  }

  const inputFormatters: { [key: string]: InputFormatter } =
  {
    "text": (value, pattern) => pattern !== undefined ? t( `${pattern}${value}`) : value,

    "datetime": datetimeFormatter,

    "number": (value, pattern) => {
      if (!pattern) {
        pattern = "0.####";
      }
      const parsed = Number(value);
      if (isFinite(parsed)) {
        const formatted = formatNumber(parsed, pattern);
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
