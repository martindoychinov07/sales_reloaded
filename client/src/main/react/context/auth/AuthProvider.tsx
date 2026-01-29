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

import * as React from "react";
import {AuthContext, type AuthContextType} from "./AuthContext.tsx";
import {AuthService, OpenAPI} from "../../api/sales";
import { useEffect, useState } from "react";
import { initCsrf } from "../../api/ApiProvider.ts";

interface LoggedUser {
  username?: string | null;
  fullname?: string | null;
}

export function AuthProvider({children}: { children: React.ReactNode }) {
  const [logged, setLogged] = useState(0);
  const [info, setInfo] = useState<LoggedUser| null>();

  useEffect(() => {
    AuthService.info().then(res => {
      setInfo(res);
    }).catch(() => {
      setInfo(null);
    });
  }, [logged]);

  const signin = async (username: string, password: string, callback: VoidFunction) => {
    const res = await fetch(`${OpenAPI.BASE}/login`, {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        "Accept": "application/json",
        ...OpenAPI.HEADERS
      },
      body: new URLSearchParams({
        username,
        password
      })
    });

    if (res.ok) {
      await initCsrf();
      setLogged(new Date().getTime());
    }
    else {
      throw new Error("Login failed");
    }
  };

  const signout = (callback: VoidFunction) => {
    setInfo(null);
    callback();
    fetch(`${OpenAPI.BASE}/logout`, {
      method: 'POST',
      credentials: 'include',
      headers: {
        ...OpenAPI.HEADERS
      }
    }).then(() => location.reload());
  }

  const value = { username: info?.username, fullname: info?.fullname, signin, signout } satisfies AuthContextType;
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}


