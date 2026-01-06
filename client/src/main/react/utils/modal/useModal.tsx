import {Modal, type ModalCloseEvent, type ModalProps, type ModalResolve} from "./Modal.tsx";
import {type ReactElement, useCallback, useEffect, useMemo, useState} from "react";

type SelectAction<R, P> = (options?: P) => Promise<ModalResolve<R>>;

interface ModalInput<R, P> {
  component: ReactElement;
  value: SelectAction<R, P>;
}

function useModal<R, P>(props: ModalProps<R, P>): ModalInput<R, P> {
  const [args, update] = useState(props.args);
  const [open, show] = useState(props.open);
  const [resolver, setResolver] = useState<((resolve: ModalResolve<R> | undefined) => void) | null>(null);

  const value = useCallback((args?: P): Promise<ModalResolve<R>> => {
    // console.log("modal", args);
    if (args !== undefined) {
      update(args);
    }
    show(true);
    return new Promise((resolve) => {
      setResolver(() => resolve);
    });
  }, []);

  const handleClose: ModalCloseEvent<R> = useCallback(
    ({ resolve, reject }) => {
      setResolver(null);
      show(undefined);
      resolver?.(reject ? { action: reject } : resolve);
      props.onClose?.({ resolve, reject });
    },
    [resolver, props]
  );

  const component = useMemo(() => (
    <Modal {...props} open={open} args={args} onClose={handleClose} />
  ), [props, open, args, handleClose]);

  return { component, value };
}

export default useModal