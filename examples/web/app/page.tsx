"use client";

import { useState } from "react";
import { primitiveColors } from "./tokens";

function ColorCard({
  name,
  value,
}: {
  name: string;
  value: string;
}) {
  const [copied, setCopied] = useState(false);
  return (
    <button
      onClick={() => {
        navigator.clipboard.writeText(value);
        setCopied(true);
        setTimeout(() => setCopied(false), 1200);
      }}
      className="group flex items-center gap-3 rounded-lg border border-border px-3 py-2.5 hover:bg-surface transition-colors cursor-pointer text-left w-full"
    >
      <div
        className="h-10 w-10 rounded-md border border-black/10 shrink-0"
        style={{ backgroundColor: value }}
      />
      <div className="min-w-0 flex-1">
        <div className="text-sm font-medium truncate">{name}</div>
        <div className="text-xs font-mono text-muted">{value}</div>
      </div>
      <span className="shrink-0 text-xs font-mono text-muted opacity-0 group-hover:opacity-100 transition-opacity">
        {copied ? "Copied!" : "Click to copy"}
      </span>
    </button>
  );
}

function ColorsSection() {
  return (
    <div className="space-y-10">
      <div>
        {primitiveColors.map((group) => (
          <div key={group.name} className="mb-8">
            <div className="mb-3">
              <h3 className="text-sm font-semibold tracking-wider">
                {group.name}
              </h3>
            </div>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2">
              {group.colors.map((c) => (
                <ColorCard
                  key={c.name}
                  name={c.name}
                  value={c.value}
                />
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default function Home() {
  return (
    <div className="min-h-screen">
      <header className="sticky top-0 z-10 bg-background/80 backdrop-blur-lg">
        <div className="mx-auto max-w-5xl px-6 py-4">
          <h1 className="text-2xl font-semibold tracking-tight">
            Nucleus
          </h1>
        </div>
      </header>
      <main className="mx-auto max-w-5xl px-6">
        <ColorsSection />
      </main>
    </div>
  );
}
