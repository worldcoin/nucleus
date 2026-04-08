"use client";

import { useState } from "react";
import type { AppTheme, DemoSection } from "../models";

function HamburgerIcon({ color }: { color: string }) {
  return (
    <span
      className="inline-flex h-10 w-10 flex-col items-center justify-center gap-1.5 rounded-full border"
      style={{ borderColor: color }}
      aria-hidden="true"
    >
      {[0, 1, 2].map((bar) => (
        <span
          key={bar}
          className="block h-0.5 w-4 rounded-full"
          style={{ backgroundColor: color }}
        />
      ))}
    </span>
  );
}

export function HeroSection({
  activeTheme,
  appThemes,
  demoSections,
  selectedSectionId,
  onSelectTheme,
  onSelectSection,
}: {
  activeTheme: AppTheme;
  appThemes: AppTheme[];
  demoSections: DemoSection[];
  selectedSectionId: string;
  onSelectTheme: (id: AppTheme["id"]) => void;
  onSelectSection: (id: string) => void;
}) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <>
      {isMenuOpen ? (
        <button
          aria-label="Close token set menu"
          className="fixed inset-0 z-20 bg-black/20"
          onClick={() => setIsMenuOpen(false)}
        />
      ) : null}

      <section className="relative flex flex-wrap items-center justify-between gap-3">
        <h1 className="text-3xl font-semibold tracking-tight">Nucleus</h1>

        <div className="flex flex-wrap items-center gap-3">
        <div
          className="inline-flex rounded-full border p-1"
          style={{ borderColor: activeTheme.border }}
        >
          {appThemes.map((theme) => {
            const isActive = theme.id === activeTheme.id;
            return (
              <button
                key={theme.id}
                className="rounded-full px-3 py-1.5 text-sm font-medium transition-colors"
                onClick={() => onSelectTheme(theme.id)}
                style={{
                  backgroundColor: isActive ? activeTheme.text : "transparent",
                  color: isActive ? activeTheme.background : activeTheme.text,
                }}
              >
                {theme.name}
              </button>
            );
          })}
        </div>

          <div className="relative">
            <button
              aria-label="Open token set menu"
              onClick={() => setIsMenuOpen((open) => !open)}
            >
              <HamburgerIcon color={activeTheme.text} />
            </button>

            {isMenuOpen ? (
              <div
                className="absolute right-0 top-14 z-30 w-56 rounded-3xl border p-2 shadow-xl"
                style={{
                  backgroundColor: activeTheme.surface,
                  borderColor: activeTheme.border,
                  color: activeTheme.text,
                }}
              >
                {demoSections.map((section) => {
                  const isActive = section.id === selectedSectionId;
                  return (
                    <button
                      key={section.id}
                      className="w-full rounded-2xl px-4 py-3 text-left text-sm font-medium transition-colors"
                      onClick={() => {
                        onSelectSection(section.id);
                        setIsMenuOpen(false);
                      }}
                      style={{
                        backgroundColor: isActive
                          ? activeTheme.text
                          : activeTheme.surface,
                        color: isActive
                          ? activeTheme.background
                          : activeTheme.text,
                      }}
                    >
                      {section.label}
                    </button>
                  );
                })}
              </div>
            ) : null}
          </div>
        </div>
      </section>
    </>
  );
}
