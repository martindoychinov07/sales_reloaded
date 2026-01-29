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

import {useEffect, useRef, useState} from "react";
import {formatTimeDiff} from "./async/AsyncUtils.tsx";

export function StopWatch() {
  const start = useRef(new Date().getTime());
  const [, ticking] = useState<boolean>(false);
  useEffect(() => {
    const interval = setInterval(() => ticking(tick => !tick), 100);
    return () => clearInterval(interval)
  }, []);
  const diff = new Date().getTime() - start.current;
  return <>{formatTimeDiff(diff)}</>
}