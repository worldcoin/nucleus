"use client";

import { useMemo, useState } from "react";
import type { DemoTheme, IconEntry, IconVariant } from "../models";

const VARIANTS: IconVariant[] = ["outline", "regular", "solid"];

function IconCard({
  icon,
  variant,
  theme,
}: {
  icon: IconEntry;
  variant: IconVariant;
  theme: DemoTheme;
}) {
  const [copied, setCopied] = useState(false);
  const markup = icon.svg[variant];

  if (!markup) {
    return (
      <div
        className="flex flex-col items-center gap-2 rounded-2xl border border-dashed px-2 py-4 opacity-40"
        style={{ borderColor: theme.border }}
      >
        <div className="h-6 w-6" />
        <div
          className="w-full truncate text-center font-mono text-[10px]"
          style={{ color: theme.muted }}
        >
          {icon.name}
        </div>
      </div>
    );
  }

  return (
    <button
      title={`${icon.name} (${variant}) — click to copy SVG`}
      onClick={() => {
        navigator.clipboard.writeText(markup);
        setCopied(true);
        setTimeout(() => setCopied(false), 1200);
      }}
      className="flex flex-col items-center gap-2 rounded-2xl border px-2 py-4 transition-colors"
      style={{
        backgroundColor: theme.surfaceAlt,
        borderColor: theme.border,
        color: theme.text,
      }}
    >
      <span
        className="[&_svg]:h-6 [&_svg]:w-6"
        dangerouslySetInnerHTML={{ __html: markup }}
      />
      <span
        className="w-full truncate text-center font-mono text-[10px]"
        style={{ color: copied ? theme.text : theme.muted }}
      >
        {copied ? "Copied SVG" : icon.name}
      </span>
    </button>
  );
}

export function IconsSection({
  icons,
  theme,
}: {
  icons: IconEntry[];
  theme: DemoTheme;
}) {
  const [variant, setVariant] = useState<IconVariant>("regular");
  const [query, setQuery] = useState("");

  const visibleIcons = useMemo(() => {
    const normalized = query.trim().toLowerCase();
    if (!normalized) {
      return icons;
    }
    return icons.filter((icon) => icon.name.includes(normalized));
  }, [icons, query]);

  return (
    <div className="space-y-4">
      <div className="flex flex-wrap items-center gap-3">
        <input
          type="search"
          placeholder={`Search ${icons.length} icons…`}
          value={query}
          onChange={(event) => setQuery(event.target.value)}
          className="w-64 rounded-full border px-4 py-2 text-sm outline-none"
          style={{
            backgroundColor: theme.surfaceAlt,
            borderColor: theme.border,
            color: theme.text,
          }}
        />
        <div
          className="inline-flex rounded-full border p-1"
          style={{ borderColor: theme.border }}
        >
          {VARIANTS.map((option) => {
            const isActive = option === variant;
            return (
              <button
                key={option}
                className="rounded-full px-3 py-1.5 text-sm font-medium capitalize transition-colors"
                onClick={() => setVariant(option)}
                style={{
                  backgroundColor: isActive ? theme.text : "transparent",
                  color: isActive ? theme.background : theme.text,
                }}
              >
                {option}
              </button>
            );
          })}
        </div>
      </div>

      {visibleIcons.length === 0 ? (
        <p className="text-sm" style={{ color: theme.muted }}>
          No icons match “{query}”.
        </p>
      ) : (
        <div className="grid grid-cols-3 gap-2 sm:grid-cols-4 md:grid-cols-6 xl:grid-cols-8">
          {visibleIcons.map((icon) => (
            <IconCard
              key={icon.name}
              icon={icon}
              variant={variant}
              theme={theme}
            />
          ))}
        </div>
      )}
    </div>
  );
}
