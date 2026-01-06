export const padNumber = (num: number, digits: number) =>
  `${num}`.padStart(digits, '0');

export const toOptionalFixed = (num: number, digits: number) =>
  `${Number.parseFloat(num.toFixed(digits))}`;

export const roundNumber = (num: number, decimals: number = 0) =>
  Number(`${Math.round(+`${num}e${decimals}`)}e-${decimals}`);

const _pattern = /(?<p>#*)(?<i>0*).?(?<f>0*)(?<s>#*)/;
export const formatNumber = (num: number, pattern: string) => {
  const m = _pattern.exec(pattern)?.groups;
  let res = "";
  if (m) {
    if (m.f || m.s) {
      res = num.toFixed((m.f.length ?? 0) + (m.s?.length ?? 0));
      if (m.s) {
        let i = 1;
        while(i <= m.s.length) {
          if (res.charAt(res.length - i) !== "0") {
            break;
          }
          i++;
        }
        if (i > 1) {
          res = res.slice(0, 1 - i - (m.f ? 0 : 1));
        }
      }
    }
    else if (m.i) {
      res = num.toFixed(0);
    }
    else {
      res = num.toString();
    }
    if (m.i || m.p) {
      const d = res.indexOf(".");
      res = res.padStart((m.i?.length ?? 0) + (m.p?.length ?? 0) + res.length - (d >= 0 ? d : res.length), "0");
    }
    return res;
  }
  return null;
}

export const range = (start: number, end: number, step: number = 1) =>
  Array.from(
    { length: Math.floor((end - start) / step) + 1 },
    (_, i) => start + i * step
  );