import {Route, Routes,} from "react-router-dom";
import './App.css'
import {AuthProvider} from "./context/auth/AuthProvider.tsx";
import {AuthLayout} from "./context/auth/AuthLayout.tsx";
import {OrderForm} from "./app/OrderForm.tsx";
import {PublicLayout} from "./app/PublicLayout.tsx";
import {AppLayout} from "./app/AppLayout.tsx";
import {PublicHome} from "./app/PublicHome.tsx";
import {AdminLayout} from "./app/admin/AdminLayout.tsx";
import {AdminHome} from "./app/admin/AdminHome.tsx";
import {AppHome} from "./app/AppHome.tsx";
import {ContactAdmin} from "./app/admin/ContactAdmin.tsx";
import {ProductAdmin} from "./app/admin/ProductAdmin.tsx";
import {ExchangeAdmin} from "./app/admin/ExchangeAdmin.tsx";
import {TranslationAdmin} from "./app/admin/TranslationAdmin.tsx";
import {SettingAdmin} from "./app/admin/SettingAdmin.tsx";
import {TypeAdmin} from "./app/admin/TypeAdmin.tsx";
import {I18nProvider} from "./context/i18n/I18nProvider.tsx";
import {OrderFormReport} from "./app/OrderFormReport.tsx";
import { Tooltip } from "react-tooltip";
import { AppUserAdmin } from "./app/admin/AppUserAdmin.tsx";

export default function App() {
  return (<>
    <I18nProvider>
      <AuthProvider>
        <Routes>
          <Route element={<PublicLayout />}>
            <Route path="/" element={<PublicHome />} />
          </Route>
          <Route path="/app" element={<AuthLayout />}>
            <Route element={<AppLayout />}>
              <Route index element={<AppHome />} />
              <Route path="/app/order" element={<OrderForm />} />
              <Route path="/app/report" element={<OrderFormReport />} />
            </Route>
          </Route>
          <Route path="/app/admin" element={<AuthLayout />}>
            <Route element={<AdminLayout />}>
              <Route index element={<AdminHome />} />
              <Route path="/app/admin/contact/list" element={<ContactAdmin />} />
              <Route path="/app/admin/product/list" element={<ProductAdmin />} />
              <Route path="/app/admin/exchange/list" element={<ExchangeAdmin />} />
              <Route path="/app/admin/translation/list" element={<TranslationAdmin />} />
              <Route path="/app/admin/type/list" element={<TypeAdmin />} />
              <Route path="/app/admin/user/list" element={<AppUserAdmin />} />
              <Route path="/app/admin/setting/list" element={<SettingAdmin />} />
            </Route>
          </Route>
        </Routes>
      </AuthProvider>
    </I18nProvider>
    <Tooltip id="tooltip" />
  </>);
}

