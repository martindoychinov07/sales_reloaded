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

import { type DependencyList, useEffect } from "react";
import type { KeyCode } from "./KeyCode.ts";

export type FocusableField =
  | HTMLInputElement
  | HTMLSelectElement;

export interface FocusContext {
  current: FocusableField;
  key: KeyCode;
  event: KeyboardEvent;
}

export type NextFocus = (
  ctx: FocusContext
) => void;

const ARROW_KEYS: KeyCode[] = [
  "Enter",
  "ArrowUp",
  "ArrowDown",
  "ArrowLeft",
  "ArrowRight",
];

function shouldNavigate(
  elem: FocusableField,
  key: KeyCode
): boolean {
  // INPUT
  if (elem instanceof HTMLInputElement) {
    const { selectionStart, selectionEnd, value } = elem;
    if (
      selectionStart === null ||
      selectionEnd === null
    ) {
      return false;
    }

    const allSelected =
      selectionStart === 0 &&
      selectionEnd === value.length;

    const atStart =
      selectionStart === 0 &&
      selectionEnd === 0;

    const atEnd =
      selectionStart === value.length &&
      selectionEnd === value.length;

    return (
      allSelected ||
        key === "Enter" ||
      (atStart &&
        (key === "ArrowLeft" || key === "ArrowUp")) ||
      (atEnd &&
        (key === "ArrowRight" || key === "ArrowDown"))
    );
  }

  // SELECT
  if (elem instanceof HTMLSelectElement) {
    // Never steal Up / Down (option navigation)
    if (key === "ArrowUp" || key === "ArrowDown") {
      return false;
    }

    // Left / Right move focus
    return key === "Enter" || key === "ArrowLeft" || key === "ArrowRight";
  }

  return false;
}


export function useKeyboardNavigation(
  nextFocus: NextFocus,
  deps: DependencyList
) {
  useEffect(() => {
    function onKeyDown(e: KeyboardEvent) {
      if (!ARROW_KEYS.includes(e.key as KeyCode)) return;

      const target = e.target;
      if (
        !(
          target instanceof HTMLInputElement ||
          target instanceof HTMLSelectElement
        )
      ) {
        return;
      }

      const key = e.key as KeyCode;

      if (!shouldNavigate(target, key)) return;

      nextFocus({
        current: target,
        key,
        event: e,
      });
    }

    window.addEventListener("keydown", onKeyDown);
    return () => window.removeEventListener("keydown", onKeyDown);
  }, [...deps, nextFocus]);
}

