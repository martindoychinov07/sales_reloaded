import { useAuth } from "../context/auth/useAuth.tsx";
import { useNavigate } from "react-router-dom";
import { useConfirm, useI18n } from "@crud-daisyui/utils";

export function AuthStatus() {
  const { t } = useI18n();
  const auth = useAuth();
  const navigate = useNavigate();
  const modalConfirm = useConfirm();

  async function handleLogout() {
    const question = await modalConfirm.value({
      title: t("~confirm.title"),
      content: t("~confirm.question")
    });
    if (question.result?.confirmed) {
      auth.signout(() => {
        navigate("/", { replace: true })
        navigate(0);
      });
    }
  }

  if (!auth.username) {
    return <p>{t("~login.action")}</p>;
  }

  return (<>
    <div className="dropdown dropdown-bottom dropdown-end">
      <div tabIndex={0} role="button" className={"w-auto"}>{auth.fullname}</div>
      <ul tabIndex={-1} className="dropdown-content menu bg-base-100 auto shadow-sm -mr-2 mt-2">
        <li><div onClick={() => navigate("/app")}>{t("~home.title")}</div></li>
        <li><div onClick={() => navigate("/app/admin")}>{t("~action.admin")}</div></li>
        <li><div onClick={async() => handleLogout()}>{t("~action.logout")}</div></li>
      </ul>
    </div>
    {modalConfirm.component}
  </>);
}