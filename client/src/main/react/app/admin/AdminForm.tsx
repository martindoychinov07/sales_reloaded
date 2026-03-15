import type { ListMetadata } from "@crud-daisyui/utils";
import { CrudForm, type ListFormProps } from "@crud-daisyui/utils";
import { UtilityService } from "../../api/sales";

export function AdminForm<F extends ListMetadata, D>(props: ListFormProps<F, D>) {
  return <CrudForm model={props.model} onExport={UtilityService.requestXlsx} />
}