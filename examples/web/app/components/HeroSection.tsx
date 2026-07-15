"use client";

import type { AppTheme, DemoSection } from "../models";

const GITHUB_REPO_URL = "https://github.com/worldcoin/nucleus";

function GitHubIcon() {
  return (
    <svg viewBox="0 0 16 16" className="h-5 w-5" fill="currentColor" aria-hidden="true">
      <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27s1.36.09 2 .27c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.01 8.01 0 0 0 16 8c0-4.42-3.58-8-8-8Z" />
    </svg>
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
  return (
    <section className="flex flex-wrap items-center justify-between gap-4">
      <div className="flex flex-wrap items-center gap-4">
        <h1 className="text-3xl font-semibold tracking-tight">Nucleus</h1>

        <nav
          className="inline-flex flex-wrap rounded-full border p-1"
          style={{ borderColor: activeTheme.border }}
        >
          {demoSections.map((section) => {
            const isActive = section.id === selectedSectionId;
            return (
              <button
                key={section.id}
                className="rounded-full px-3 py-1.5 text-sm font-medium transition-colors"
                onClick={() => onSelectSection(section.id)}
                style={{
                  backgroundColor: isActive ? activeTheme.text : "transparent",
                  color: isActive ? activeTheme.background : activeTheme.text,
                }}
              >
                {section.label}
              </button>
            );
          })}
        </nav>
      </div>

      <div className="flex items-center gap-3">
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

        <a
          href={GITHUB_REPO_URL}
          target="_blank"
          rel="noopener noreferrer"
          aria-label="Nucleus on GitHub"
          className="inline-flex h-10 w-10 items-center justify-center rounded-full border transition-opacity hover:opacity-70"
          style={{ borderColor: activeTheme.border, color: activeTheme.text }}
        >
          <GitHubIcon />
        </a>
      </div>
    </section>
  );
}
