import type {LayoutModel, LayoutModelItem} from "./LayoutModel.ts";
import { type ReactNode, useRef, useState } from "react";
import type {InputFormatter} from "./Input.tsx";
import {useI18n} from "../context/i18n/useI18n.tsx";

export type TableComponent<T> = (props: {entry: T, formatter: InputFormatter, item: LayoutModelItem<T>, index: number}) => ReactNode;
type TableElement<T> = ReactNode | TableComponent<T>;

interface TableParams<T> {
  data: T[];
  dataKey: keyof T;
  formatters: { [p: string]: InputFormatter };
  layout: LayoutModel<T>;
  children: TableElement<T>;
  onSort?: (name?: keyof T | string) => void;
  selector?: (entry?: T, index?: number | undefined) => ReactNode;
  onDoubleClick?: (entry?: T, index?: number | undefined) => void;
}

export function TableTemplate<T>(props: TableParams<T>) {
  const { t } = useI18n();

  const [widths, setWidths] = useState(props.layout?.items?.map(item => parseInt(item.size ?? "0")));
  const tableRef = useRef<HTMLDivElement>(null);

  const startResize = (index: number, e: any) => {
    e.preventDefault();
    const startX = e.clientX;
    const startWidth = widths?.[index] ?? 0;

    const onMouseMove = (e: MouseEvent) => {
      const newWidth = Math.max(startWidth + e.clientX - startX, 50); // min width
      setWidths((cols) => {
        const newCols = [...cols ?? []];
        newCols[index] = newWidth;
        return newCols;
      });
    };

    const onMouseUp = () => {
      document.removeEventListener("mousemove", onMouseMove);
      document.removeEventListener("mouseup", onMouseUp);
    };

    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);
  };

  const gridTemplate = widths?.map((width) => `${width}px`).join(" ");

  return ( /* w-max */
    <div key={"table"} className="table" ref={tableRef}>
      <div key={"thead"} className={"table-header-group"}>
        <div key={"header"} className={"table-row"} style={{ gridTemplateColumns: gridTemplate }}>
          <div key={"_"} className={"table-cell resize-none w-0"}>
            {props.selector?.()}
          </div>
          {props.layout.items?.map((item, index) =>
            <div
              key={item.name ?? index}
              className={`table-cell ${(item.type === "hidden" || item.mode === "hidden") ? "hidden" : undefined}`}
            /*div*/>
              <span className={"cursor-pointer"} onClick={(e) => { props.onSort?.(item.name); e.preventDefault(); e.stopPropagation() }}>{t(item.label)}</span>
              <div
                className="resize-handle"
                onMouseDown={(e) => startResize(index, e)}
              />
            </div>)
          }
        </div>
      </div>
      <div className={"table-row-group"}>
      {props.data.map((entry, row) => {
        return <label
          key={`${entry[props.dataKey] ?? `row${row}`}`}
          className={"table-row hover:bg-gray-100"}
          style={{ gridTemplateColumns: gridTemplate }}
          onDoubleClick={e => props.onDoubleClick?.(entry, row)}
        /*label*/>
          <div key={"_"} className={"table-cell"}>
            {props.selector?.(entry, row)}
          </div>
          {props.layout.items?.map((item, index) =>
            <div key={item.name ?? index} className={`table-cell peer-checked:bg-gray-200 ${(item.type === "hidden" || item.mode === "hidden") ? "hidden" : ""}`}>
              {
                typeof props.children === "function"
                ? props.children({ entry: entry, formatter: props.formatters[item.type ?? "none"], item: item, index: row })
                : props.children
              }
            </div>)
          }
        </label>
      })}
      </div>
      {/*<div className={"table-footer-group"}>*/}
      {/*  <div className={"table-row"}>*/}
      {/*    <div key={"_"} className={"table-cell resize-none"}>*/}
      {/*      <span></span>*/}
      {/*    </div>*/}
      {/*    <div key={"tfoot"} className={`table-cell col-span-${props.layout.items?.length ?? 2 - 1} resize-none`}>pagination</div>*/}
      {/*  </div>*/}
      {/*</div>*/}
    </div>
  )
}