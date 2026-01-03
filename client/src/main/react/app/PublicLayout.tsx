import {Outlet} from "react-router-dom";
import {Footer} from "./Footer.tsx";
import {VerticalLayout} from "../utils/VerticalLayout.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";

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