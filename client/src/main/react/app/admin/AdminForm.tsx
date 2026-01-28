import ListForm, {type ListFormProps} from "../../utils/ListForm.tsx";
import type {ListMetadata} from "../../utils/ListFormModel.ts";

export function AdminForm<F extends ListMetadata, D>(props: ListFormProps<F, D>) {
  return <ListForm model={props.model} />
}