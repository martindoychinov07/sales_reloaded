import * as React from "react";

export interface AuthContextType {
  username: string | null | undefined;
  fullname: string | null | undefined;
  signin: (username: string, password: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

export const AuthContext = React.createContext<AuthContextType>(null!);