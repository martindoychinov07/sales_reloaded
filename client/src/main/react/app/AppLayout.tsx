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