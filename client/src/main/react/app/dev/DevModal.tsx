import type { ModalComponent, ModalProps } from "../../utils/modal/Modal.tsx";
import { useI18n } from "../../context/i18n/useI18n.tsx";
import useModal from "../../utils/modal/useModal.tsx";

interface Args {
  num: number;
}

interface Res {
  text: string;
}

const Temp1: ModalComponent<Res, Args> = (props) => {
  return <form key={"1"} method="dialog"
    onSubmit={(e) => {
      const formData = new FormData(e.currentTarget);
      const submitter = (e.nativeEvent as SubmitEvent).submitter as HTMLButtonElement;
      const action = submitter?.value;
      const data = Object.fromEntries(formData.entries());
      props.close?.({ resolve: {action: action, result: {text: `hello ${props.args?.num} ${JSON.stringify(data)}`}} })
    }}>
    <p key={"1_0"}>{props.args?.num}</p>
    <input key={"1_1"} type={"text"} name={"t1"} className={"input"} />
    <button key={"1_2"} className="btn btn-neutral mt-4" name={"b1"} value="apply">Apply</button>
    <button key={"1_3"} className="btn btn-neutral mt-4" name={"b2"} value="remove">Remove</button>
  </form>
}

export function DevModal() {
  const { t } = useI18n();

  const mp: ModalProps<Res, Args> = //useMemo( () => (
    {
      // open: true,
      // args: {num: 3},
      // header: "test dialog",
      children: (props) => { return <Temp1 close={props.close} {...props} /> },
      // footer: <></>,
      // onClose(resolve, reject): void {
      //   console.log(`onClose ${JSON.stringify(resolve)}|${reject}`);
      // },
    }
  //), []);

  const modal = useModal(mp);

  async function handle1() {
    const res = await modal.value({num: 1});
    console.log("handle1", JSON.stringify(res));
  }

  async function handle2() {
    const res = await modal.value({num: 2});
    console.log("handle2", JSON.stringify(res));
  }

  async function handle3() {
    const res = await modal.value();
    console.log("handle3", JSON.stringify(res));
  }

  return <h3>
    {modal.component}
    <button className={"btn"} onClick={handle1}>modal(1)</button>
    <button className={"btn"} onClick={handle2}>modal(2)</button>
    <button className={"btn"} onClick={handle3}>modal(last)</button>
  </h3>;
  // return <p className={"flex-1 text-center content-around"}>{t("~public.welcome")}</p>
}
