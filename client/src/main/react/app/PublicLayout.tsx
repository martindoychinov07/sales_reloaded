import { Outlet } from "react-router-dom";
import { Footer } from "./Footer.tsx";
import { useI18n, VerticalLayout } from "@crud-daisyui/utils";
import { Language } from "./Language.tsx";

export function PublicLayout() {
  const { t } = useI18n();
  const menu = [
    [],
    [
      {
        key: "user",
        label: t("~login.action"),
        link: "/app",
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