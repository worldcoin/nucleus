"use client";

import { useState } from "react";
import type { DemoTheme } from "../models";

export function ColorCard({
  name,
  value,
  theme,
}: {
  name: string;
  value: string;
  theme: DemoTheme;
}) {
  const [copied, setCopied] = useState(false);

  return (
    <button
      onClick={() => {
        navigator.clipboard.writeText(value);
        setCopied(true);
        setTimeout(() => setCopied(false), 1200);
      }}
      className="group flex w-full items-center gap-3 rounded-2xl border px-3 py-3 text-left transition-colors"
      style={{
        backgroundColor: theme.surfaceAlt,
        borderColor: theme.border,
        color: theme.text,
      }}
    >
      <div
        className="h-11 w-11 shrink-0 rounded-xl border"
        style={{
          backgroundColor: value,
          borderColor: theme.border,
        }}
      />
      <div className="min-w-0 flex-1">
        <div className="truncate text-sm font-semibold">{name}</div>
        <div className="truncate font-mono text-xs" style={{ color: theme.muted }}>
          {value}
        </div>
      </div>
      <span
        className="shrink-0 font-mono text-[11px] opacity-0 transition-opacity group-hover:opacity-100"
        style={{ color: theme.muted }}
      >
        {copied ? "Copied" : "Copy"}
      </span>
    </button>
  );
}
