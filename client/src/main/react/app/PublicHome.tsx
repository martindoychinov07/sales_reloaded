import {useI18n} from "../context/i18n/useI18n.tsx";

export function PublicHome() {
  const { t } = useI18n();

  return <p className={"flex-1 text-center content-around"}>{t("~public.welcome")}</p>
}
