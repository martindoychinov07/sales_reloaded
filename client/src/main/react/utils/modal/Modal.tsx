import {type ReactNode, useRef} from "react";

export type ModalReject = "code" | "esc" | "out" | "x";
export interface ModalResolve<R> {
  action?: string;
  result?: R;
}
export type ModalCloseEvent<R> = (modal: { resolve: ModalResolve<R>, reject?: ModalReject }) => void;
export type ModalComponentProps<R, P> = {close: ModalCloseEvent<R>, args?: P};
export type ModalComponent<R, P> = (props: ModalComponentProps<R, P>) => ReactNode;
type ModalElement<R, P> = ReactNode | ModalComponent<R, P>;

export interface ModalProps<R, P> {
  open?: true | undefined;
  args?: P,
  header?: ModalElement<R, P>,
  children: ModalElement<R, P>,
  footer?: ModalElement<R, P>,
  onClose?: ModalCloseEvent<R>,
  noWrapper?: boolean,
  variant?: "full",
}

export function Modal<R, P>(props: ModalProps<R, P>) {
  const dialogRef = useRef<HTMLDialogElement>(null);

  const cancel = useRef<ModalReject>(undefined);
  const result = useRef<R>(undefined);

  cancel.current = undefined;
  result.current = undefined;

  const handleClose: ModalCloseEvent<R> = ({ resolve, reject }) => {
    cancel.current = reject;
    result.current = resolve?.result;
    dialogRef.current?.close(reject ?? resolve?.action ?? dialogRef.current?.returnValue);
  }

  const body = typeof props.children === "function" ? props.children({ close: handleClose, args: props.args }) : props.children;
  return <dialog
    key={"dialog"}
    ref={dialogRef}
    open={props.open}
    className="modal"
    onKeyDown={(e) => { if (e.key === 'Escape') handleClose({ resolve: {}, reject: "esc" }) } }
    onClose={() => props.onClose?.({ resolve: { action: cancel.current ?? dialogRef.current?.returnValue, result: result.current }, reject: cancel.current })}
  >
    <div key={"dialog_container"}
         className={`modal-box w-fit resize max-h-[calc(100%-1em)] max-w-[calc(100%-1em)] flex-1 flex flex-col overflow-hidden ${props.variant === "full" ? "min-h-[calc(100%-1em)] min-w-[calc(100%-1em)]" : ""}`}
    /*div*/>
      <div key={"dialog_header"} className="font-bold text-lg grid gap-1 p-1">
        {typeof props.header === "function" ? props.header({ close: handleClose, args: props.args }) : props.header}
      </div>
      {
        props.noWrapper
        ? body
        : <div key={"dialog_body"} className="py-0 flex-1 overflow-y-auto">{body}</div>
      }
      {props.footer && <div key={"dialog_footer"} className="modal-action grid gap-1 p-1">
        {typeof props.footer === "function" ? props.footer({ close: handleClose, args: props.args }) : props.footer}
      </div>}
      <form method="dialog" key={"dialog_close"}>
        <button formMethod="dialog" className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={() => cancel.current = "x"}>✕</button>
      </form>
    </div>
    <form key={"dialog_backdrop"} method="dialog" className="modal-backdrop" onSubmit={() => cancel.current = "out" }>
      <button formMethod="dialog">close</button>
    </form>
  </dialog>
}