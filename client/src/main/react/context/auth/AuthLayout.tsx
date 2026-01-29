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

import {useAuth} from "./useAuth.tsx";
import {Outlet, useNavigate} from "react-router-dom";
import {LoginForm} from "../../app/LoginForm.tsx";
import {Modal} from "../../utils/modal/Modal.tsx";
import * as React from "react";
import {useLocation} from "react-router";
import {useI18n} from "../i18n/useI18n.tsx";

export function AuthLayout() {
  const { t } = useI18n();
  const auth = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>)=> {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);
    const username = formData.get("username") as string;
    const password = formData.get("password") as string;

    auth.signin(username, password, () => {
      const from = location.pathname || "/";
      navigate(from, {replace: true});
    });
  }

  if (!auth.username) {
    return <Modal
      open={true}
      header={t("~login.title")}
      onClose={modal => { if (modal.reject) navigate("/", {replace: true}) }}
    /*Modal*/>
      <LoginForm method={"dialog"} onSubmit={handleSubmit} />
    </Modal>
  }

  return <Outlet />;
}