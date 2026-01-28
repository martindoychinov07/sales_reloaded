import * as React from "react";
import {AuthContext} from "./AuthContext.tsx";

export function useAuth() {
  return React.useContext(AuthContext);
}