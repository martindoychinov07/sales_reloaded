import { useI18n } from "@crud-daisyui/utils";

export function Language() {
  const { language, setLanguage, t } = useI18n();

  return (<>
    <div className="dropdown dropdown-bottom dropdown-end">
      <div tabIndex={0} role="button" className={"uppercase w-5"}>{language}</div>
      <ul tabIndex={-1} className="dropdown-content menu bg-base-100 auto shadow-sm -mr-2 mt-2">
        <li><div onClick={() => setLanguage("bg")}>BG</div></li>
        <li><div onClick={() => setLanguage("en")}>EN</div></li>
      </ul>
    </div>
  </>);
}