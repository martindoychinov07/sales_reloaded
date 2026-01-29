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

import {Modal, type ModalCloseEvent, type ModalProps, type ModalResolve} from "./Modal.tsx";
import { type ReactElement, useCallback, useEffect, useMemo, useState } from "react";
import { createPortal } from "react-dom";

export type SelectionType = { selection?: "one" | "many" };
type SelectAction<R, P> = (options?: P) => Promise<ModalResolve<R, P>>;

interface ModalInput<R, P> {
  component: ReactElement;
  value: SelectAction<R, P>;
}

const modalRoot = document.getElementById("modal-root") ?? document.body;

function useModal<R, P>(props: ModalProps<R, P>): ModalInput<R, P> {
  const [args, update] = useState(props.args);
  const [open, show] = useState(props.open);
  const [resolver, setResolver] = useState<((resolve: ModalResolve<R, P> | undefined) => void) | null>(null);

  const handleClose: ModalCloseEvent<R, P> = useCallback(
    ({ resolve, reject }) => {
      setResolver(null);
      show(undefined);
      resolver?.(reject ? { action: reject } : resolve);
      props.onClose?.({ resolve, reject });
    },
    [resolver, props]
  );

  const value = useCallback((args?: P): Promise<ModalResolve<R, P>> => {
    if (args !== undefined) {
      update(args);
    }
    show(true);
    return new Promise((resolve) => {
      setResolver(() => resolve);
    });
  }, []);

  const component = useMemo(() => (
    createPortal(<Modal {...props} open={open} args={args} onClose={handleClose} />, modalRoot)

  ), [props, open, args, handleClose]);

  return useMemo(() => ({ component, value }), [component, value]);
}

export default useModal