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
      open
      header={t("~login.title")}
      onClose={modal => { if (modal.reject) navigate("/", {replace: true}) }}
    /*Modal*/>
      <LoginForm method={"dialog"} onSubmit={handleSubmit} />
    </Modal>
  }

  return <Outlet />;
}