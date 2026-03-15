import { AuthStatus } from "../AuthStatus.tsx";
import { Outlet } from "react-router-dom";
import { Footer } from "../Footer.tsx";
import { useI18n, VerticalLayout } from "@crud-daisyui/utils";
import { Language } from "../Language.tsx";

export function AdminLayout() {
  const { t } = useI18n();
  const menu = [
    [
      {
        key: "home",
        label: t("~home.title"),
        link: "/app",
      },
      {
        key: "contact",
        label: t("~contact.title"),
        link: "/app/admin/contact/list",
      },
      {
        key: "product",
        label: t("~product.title"),
        link: "/app/admin/product/list",
      },
      {
        key: "exchange",
        label: t("~exchange.title"),
        link: "/app/admin/exchange/list",
      },
      {
        key: "translation",
        label: t("~translation.title"),
        link: "/app/admin/translation/list",
      },
      {
        key: "type",
        label: t("~type.title"),
        link: "/app/admin/type/list",
      },
      {
        key: "users",
        label: t("~user.title"),
        link: "/app/admin/user/list",
      },
      {
        key: "setting",
        label: t("~setting.title"),
        link: "/app/admin/setting/list",
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