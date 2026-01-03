import {AuthStatus} from "./AuthStatus.tsx";
import {Outlet} from "react-router-dom";
import {Footer} from "./Footer.tsx";
import {VerticalLayout} from "../utils/VerticalLayout.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";

export function AppLayout() {
  const { t } = useI18n();
  const menu = [
    [
      {
        key: "home",
        label: t("~home.title"),
        link: "/app",
      },
      {
        key: "order",
        label: t("~order.title"),
        link: "/app/order",
      },
      {
        key: "reports",
        label: t("~report.title"),
        link: "/app/report",
      },
      {
        key: "settings",
        label: t("~setting.title"),
        link: "/app/admin",
      },
    ],
    [
      {
        key: "user",
        label: <AuthStatus />,
      },
    ],
  ];

  return (
      <VerticalLayout
        menu={menu}
        footer={<Footer />}
      /*VerticalLayout*/>
        <Outlet />
      </VerticalLayout>
  );
}