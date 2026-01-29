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

import {useI18n} from "../../context/i18n/useI18n.tsx";
import type {ModalComponent, ModalProps} from "./Modal.tsx";
import useModal from "./useModal.tsx";
import {Calendar} from "../Calendar.tsx";

interface CalendarArgs {
  title?: string;
  date?: Date | undefined | null;
}

interface CalendarResult {
  date?: Date;
}

export function useCalendar() {
  const { t } = useI18n();

  const Content: ModalComponent<CalendarResult, CalendarArgs> = (props) => {
    return <form key={"form"} method="dialog"
      onSubmit={(e) => {
        const submitter = (e.nativeEvent as SubmitEvent).submitter as HTMLButtonElement;
        const action = submitter?.value;
        const date = new Date(action);
        if (date) {
          // date.setMinutes( + new Date().getTimezoneOffset());
          props.close?.({ resolve: { action: "select", result: { date: date } } } );
        }
        else {
          props.close?.({ resolve: {action: "select", result: { date: undefined } }, reject: "code" })
        }
      }}>
      <div className={"grid grid-cols-2 gap-1 p-1 min-w-[20em]"}>
        <div key={"content"} className={"col-span-2"}>
          <Calendar date={props.args?.date} />
        </div>
      </div>
    </form>
  }

  const modalProps: ModalProps<CalendarResult, CalendarArgs> = //useMemo( () => (
    {
      header: (props) => {
        return <>{props.args?.title ?? t("~calendar.title")}</>
      },
      children: (props) => {
        return <Content close={props.close} {...props} />
      },
    }
  //), []);

  return useModal(modalProps);
}