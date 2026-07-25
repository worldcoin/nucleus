"use client";

import { useState } from "react";
import { ColorGroupSection } from "./ColorGroupSection";
import type { ColorPalette, DemoTheme } from "../models";

export function ColorPalettes({
  palettes,
  theme,
}: {
  palettes: ColorPalette[];
  theme: DemoTheme;
}) {
  const [selectedPaletteId, setSelectedPaletteId] = useState(palettes[0]?.id);
  const selectedPalette =
    palettes.find((palette) => palette.id === selectedPaletteId) ?? palettes[0];

  return (
    <div className="space-y-6" style={{ color: theme.text }}>
      <div
        className="inline-flex flex-wrap rounded-full border p-1 shadow-sm"
        style={{
          backgroundColor: theme.surfaceAlt,
          borderColor: theme.border,
        }}
      >
        {palettes.map((palette) => {
          const isActive = palette.id === selectedPalette.id;
          return (
            <button
              key={palette.id}
              className="rounded-full px-3 py-1.5 text-sm font-medium transition-[background-color,color,opacity] hover:opacity-75 focus-visible:outline-2 focus-visible:outline-offset-2"
              onClick={() => setSelectedPaletteId(palette.id)}
              style={{
                backgroundColor: isActive ? theme.text : "transparent",
                color: isActive ? theme.background : theme.text,
                outlineColor: theme.accent,
              }}
            >
              {palette.label}
            </button>
          );
        })}
      </div>

      <div className="space-y-8">
        {selectedPalette.groups.map((group) => (
          <ColorGroupSection key={group.id} group={group} theme={theme} />
        ))}
      </div>
    </div>
  );
}
