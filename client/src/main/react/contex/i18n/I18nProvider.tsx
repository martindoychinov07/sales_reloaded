import { I18nContext, type I18nContextType } from "./I18nContext.tsx";
import { type ReactNode, useCallback, useState } from "react";
import { type TranslationDto, TranslationService } from "../../api/sales";
import type { Path } from "react-hook-form";
import { useAsyncState } from "../../utils/async/useAsyncState.tsx";
import { AsyncFragment } from "../../utils/async/AsyncFragment.tsx";
import { Loading } from "../../utils/Loading.tsx";

interface I18nProviderProps {
  children: ReactNode;
}

export function replaceParams<
  T extends Record<string, string | number | boolean>
>(template: string, params: T): string {
  return template.replace(/\{\{(\w+)\}\}/g, (_, key) => {
    const value = params[key as keyof T];
    return value !== undefined ? String(value) : "";
  });
}

export function I18nProvider(props: I18nProviderProps) {
  const [language, setLanguage] = useState<string>("bg");

  async function load(args: { load: boolean }) {
    try {
      if (args.load) {
        const ts = await TranslationService.findTranslation({ size: 10000 });
        const i18n = Object.fromEntries(ts.content ? ts.content.map(tn => [`~${tn.translationKey}`, tn]) : []);
        return i18n;
      }
    }
    catch (e) {
      console.log(e);
    }
    return {}
  }

  const state = useAsyncState(useCallback(load, []), { load: true });

  return <AsyncFragment
    asyncState={state}
    onFallback={() => <Loading/>}
    onFinish={(state) => {
      const translation = state.result;
      const t = (text: string | undefined | null, lang?: string) => {
        if (!text) return undefined;
        if (!text.startsWith("~")) return text;
        const i18n = translation?.[text]?.[(lang ?? language) as Path<TranslationDto>] as string;
        if (!i18n) console.log(text);
        // if (!i18n && text.indexOf(".") > 0 && !isFinite(parseFloat(text))) {
        //   TranslationService.createTranslation({ requestBody: {translationKey: text}}).catch(reason => console.log(reason));
        // }
        return i18n ?? text;
      };

      return <I18nContext.Provider
        value={{ language, setLanguage, t } satisfies I18nContextType}>{props.children}</I18nContext.Provider>
    }}
    /*AsyncFragment*/>
    <></>
  </AsyncFragment>
}

