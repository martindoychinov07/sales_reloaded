import { AuthStatus } from "./AuthStatus.tsx";
import { Outlet } from "react-router-dom";
import { Footer } from "./Footer.tsx";
import { Language } from "./Language.tsx";
import { useI18n, VerticalLayout } from "@crud-daisyui/utils";

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
    ],
    [
      {
        key: "user",
        label: <AuthStatus />,
      },
      {
        key: "language",
        label: <Language />,
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