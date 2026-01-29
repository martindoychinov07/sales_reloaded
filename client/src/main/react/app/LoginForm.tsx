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