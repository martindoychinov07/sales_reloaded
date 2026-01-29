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

import {useAuth} from "../context/auth/useAuth.tsx";
import {useNavigate} from "react-router-dom";
import { useI18n } from "../context/i18n/useI18n.tsx";
import { useConfirm } from "../utils/modal/useConfirm.tsx";

export function AuthStatus() {
  const { t } = useI18n();
  const auth = useAuth();
  const navigate = useNavigate();
  const modalConfirm = useConfirm();

  if (!auth.username) {
    return <p>Login</p>;
  }

  return (<>
    <div className="tooltip tooltip-bottom">
      <div className="tooltip-content">{t("~action.logout")}</div>
      <div onClick={() => {
        auth.signout(() => {
          navigate("/", { replace: true })
          navigate(0);
        });
      }}>{auth.fullname}</div>
    </div>
    {modalConfirm.component}
  </>);
}