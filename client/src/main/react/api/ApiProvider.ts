import { OpenAPI } from "./sales";

type CsrfResponse = {
  headerName: string;
  token: string;
};

export async function initCsrf() {
  const res = await fetch(`${OpenAPI.BASE}/auth/csrf`, {
    credentials: "include"
  });

  const csrf = (await res.json()) as CsrfResponse;

  OpenAPI.HEADERS = {
    [csrf.headerName]: csrf.token
  };
}

export async function initClient() {
  OpenAPI.BASE = import.meta.env.VITE_API_BASE;
  OpenAPI.WITH_CREDENTIALS = !!import.meta.env.VITE_CREDENTIALS;
  OpenAPI.CREDENTIALS = import.meta.env.VITE_CREDENTIALS;

  await initCsrf();
}
