import { useI18n } from "@crud-daisyui/utils";

export function PublicHome() {
  const { t } = useI18n();

  return <p className={"flex-1 text-center content-around"}>{t("~public.welcome")}</p>
}
