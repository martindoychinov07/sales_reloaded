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