import type {LayoutModel, LayoutModelItem} from "./LayoutModel.ts";
import { type ReactNode, useEffect, useRef, useState } from "react";
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
  onClick?: (entry?: T, index?: number | undefined) => void;
  onDoubleClick?: (entry?: T, index?: number | undefined) => void;
}

export function Table<T>(props: TableParams<T>) {
  const { t } = useI18n();
  const tableRef = useRef<HTMLTableElement>(null);
  const [widths, setWidths] = useState(props.layout?.items?.map(item => {
    let res: number;
    const width = localStorage.getItem(`${item.name}.width`);
    if (width === null) {
      if (item.size !== undefined) {
        res = parseInt(item.size ?? "") * 11;
      }
      else {
        res = 100;
      }
    }
    else {
      res = parseInt(width);
    }
    return res;
  }));

  useEffect(() => {
    props.layout?.items?.forEach((item, index) => {
      localStorage.setItem(`${item.name}.width`, `${widths?.[index]}`);
    });
  }, [widths]);

  const startResize = (index: number, e: any) => {
    e.preventDefault();
    const startX = e.clientX;
    const startWidth = widths?.[index] ?? 0;

    const onMouseMove = (e: MouseEvent) => {
      const newWidth = Math.max(startWidth + e.clientX - startX, 0); // min width
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

  return ( /* w-max */
    <table key={"table"} className="table" ref={tableRef}>
      <thead>
        <tr>
          <th key={"_"} className={"text-center"}>
            {props.selector?.()}
          </th>
          {props.layout.items?.map((item, index) => {
            const width = widths?.[index] !== undefined ? `${widths?.[index]}px` : "100px";
            return (
            <th
              key={item.name ?? index}
              className={`${(item.type === "hidden" || item.mode === "hidden") ? "hidden" : ""}`}
              style={{ width: `${width}`, maxWidth: `${width}` }}
            /*th*/>
              <span className={"cursor-pointer"} onClick={(e) => { props.onSort?.(item.name); e.preventDefault(); e.stopPropagation() }}>{t(item.label)}</span>
              <div
                className="resize-handle"
                onMouseDown={(e) => startResize(index, e)}
              ></div>
            </th>)})
          }
        </tr>
      </thead>
      <tbody>
      {props.data.map((entry, row) => {
        return <tr
          key={`${entry[props.dataKey] ?? `row${row}`}`}
          onClick={() => props.onClick?.(entry, row)}
          onDoubleClick={() => props.onDoubleClick?.(entry, row)}
        /*tr*/>
          <td key={"_"} className={"text-center"}>
            {props.selector?.(entry, row)}
          </td>
          {props.layout.items?.map((item, index) =>
            <td key={item.name ?? index} className={`${(item.type === "hidden" || item.mode === "hidden") ? "hidden" : ""}`}>
              {
                typeof props.children === "function"
                ? props.children({ entry: entry, formatter: props.formatters[item.type ?? "none"], item: item, index: row })
                : props.children
              }
            </td>)
          }
        </tr>
      })}
      </tbody>
    </table>
  )
}