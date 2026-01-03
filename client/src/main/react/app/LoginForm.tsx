import * as React from "react";
import {useI18n} from "../context/i18n/useI18n.tsx";

interface LoginParams {
  action?: string;
  method?: string;
  onSubmit?: React.FormEventHandler<HTMLFormElement> | undefined
}

export function LoginForm(props: LoginParams) {
  const { t } = useI18n();
  return (
    <form className={"min-w-2xs"} action={props.action} method={props.method} onSubmit={props.onSubmit}>
      <fieldset className="fieldset">
        <label className="label">{t("~login.username")}</label>
        <input name={"username"} type="text" className="input w-full" required={true} placeholder={t("~login.username")}/>
        <label className="label">{t("~login.password")}</label>
        <input name={"password"} type="password" className="input w-full" required={true} placeholder={t("~login.password")}/>
        <div><a className="link link-hover">{t("~login.forgot")}</a></div>
        <button className="btn btn-neutral mt-4" value="login">{t("~login.action")}</button>
      </fieldset>
    </form>
  );
}