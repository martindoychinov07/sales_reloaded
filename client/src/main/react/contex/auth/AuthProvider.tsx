import * as React from "react";
import {AuthContext, type AuthContextType} from "./AuthContext.tsx";
import {AuthService, OpenAPI} from "../../api/sales";
import { useEffect, useState } from "react";

interface LoggedUser {
  username?: string | null;
  fullname?: string | null;
}

export function AuthProvider({children}: { children: React.ReactNode }) {
  const [logged, setLogged] = useState(0);
  const [info, setInfo] = useState<LoggedUser| null>();

  useEffect(() => {
    AuthService.info().then(res => {
      console.log("(2)", {res});
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

    console.log("(1)", {res});
    if (res.ok) {
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
  console.log("!!!", {value});
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}


