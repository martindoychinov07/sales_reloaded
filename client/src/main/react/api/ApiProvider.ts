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
