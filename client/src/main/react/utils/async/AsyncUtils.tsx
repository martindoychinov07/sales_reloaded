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

import {StopWatch} from "../StopWatch.tsx";
import type {AsyncState} from "./useAsyncState.tsx";

export function formatTimeDiff(diff: number) {
  const S = Math.floor(diff / 100) % 10;
  diff = Math.floor(diff / 1000);
  const s = diff % 60;
  diff = Math.floor( diff / 60);
  const m = diff;
  return (m ? `${m.toString().padStart(2, " ")}m ` : "")
      + `${s.toString().padStart(2, " ")}.${S}s`;
}

export function Loading() {
  return <div>Loading... <StopWatch /></div>
}

export function statsOnFinish<T, P>(state: AsyncState<T, P>, children: any) {
  let text = "";
  if (state.started && state.finished) {
    text = text + ` fetch in ${formatTimeDiff(state.finished - state.started)}`;
  }
  if (state.finished) {
    text = text + ` updated on ${new Date(state.finished).toLocaleString()}`;
  }

  return <div title={text}>{children}</div>
}

export async function delay(ms: number){
  let timeout: number | undefined;
  return new Promise(
      (r) => { timeout = setTimeout(r, ms); }
  ).finally(
      () => clearTimeout(timeout)
  );
}