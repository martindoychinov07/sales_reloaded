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

import { type Dispatch, type SetStateAction, useEffect, useMemo, useState } from "react";

export interface AsyncState<T, P> {
  reload: Dispatch<SetStateAction<P | undefined>>;
  update: Dispatch<SetStateAction<T | undefined>>;
  args: P | undefined;
  started?: number;
  finished?: number;
  result: T | undefined;
  error: Error | undefined
}

export function useAsyncState<T, P>(fn: (args: P) => Promise<T>, initArgs?: P | (() => P)): AsyncState<T, P> {
  const [args, reload] = useState<P | undefined>(initArgs);
  const [started, setStarted] = useState<number | undefined>();
  const [finished, setFinished] = useState<number | undefined>( initArgs ? undefined : new Date().getTime());
  const [error, setError] = useState<Error | undefined>();
  const [result, update] = useState<T | undefined>();
  useEffect(() => {
    let cancel = false;
    if (args) {
      setStarted(new Date().getTime());
      setFinished(undefined);
      setError(undefined);
      fn(args).then(
        res => {
          if (cancel) return;
          update(res)
          setError(undefined);
          setFinished(new Date().getTime());
        },
        error => {
          if (cancel) return;
          update(undefined)
          setError(error);
          setFinished(new Date().getTime());
        }
      );
    }
    return () => {
      cancel = true;
    }
  }, [fn, args])
  // console.log({reload, update, args, started, finished, result, error})
  return useMemo(
    () => {
      return { reload, update, args, started, finished, result, error };
    },
    [finished, args, result]
  );
}