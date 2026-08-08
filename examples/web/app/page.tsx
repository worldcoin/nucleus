"use client";

import { useEffect, useState } from "react";
import { HeroSection } from "./components/HeroSection";
import { TokenSectionCard } from "./components/TokenSectionCard";
import type { AppTheme } from "./models";
import { appThemes, demoSections, fontStyles, semanticModes } from "./tokens";

const THEME_STORAGE_KEY = "nucleus-theme";
const SECTION_STORAGE_KEY = "nucleus-section";

export default function Home() {
  const [selectedSectionId, setSelectedSectionId] = useState(
    demoSections[0].id,
  );
  // null until the stored theme is restored; the layout's pre-hydration CSS
  // keeps the light-prerendered content hidden in dark mode while it's null
  const [restoredThemeId, setRestoredThemeId] = useState<AppTheme["id"] | null>(
    null,
  );
  const selectedThemeId = restoredThemeId ?? "light";

  useEffect(() => {
    const storedSection = window.localStorage.getItem(SECTION_STORAGE_KEY);
    if (storedSection && demoSections.some((s) => s.id === storedSection)) {
      setSelectedSectionId(storedSection);
    }
    const stored = window.localStorage.getItem(THEME_STORAGE_KEY);
    // suppress transitions so restoring dark doesn't animate from the
    // light-prerendered colors
    const suppressor = document.createElement("style");
    suppressor.textContent = "*{transition:none!important}";
    document.head.appendChild(suppressor);
    setRestoredThemeId(stored === "dark" ? "dark" : "light");
    requestAnimationFrame(() =>
      requestAnimationFrame(() => suppressor.remove()),
    );
  }, []);

  useEffect(() => {
    if (restoredThemeId) {
      document.documentElement.dataset.theme = restoredThemeId;
    }
  }, [restoredThemeId]);

  const selectTheme = (id: AppTheme["id"]) => {
    setRestoredThemeId(id);
    window.localStorage.setItem(THEME_STORAGE_KEY, id);
  };

  const selectSection = (id: string) => {
    setSelectedSectionId(id);
    window.localStorage.setItem(SECTION_STORAGE_KEY, id);
  };

  const selectedSection =
    demoSections.find((section) => section.id === selectedSectionId) ??
    demoSections[0];
  const activeTheme =
    appThemes.find((theme) => theme.id === selectedThemeId) ?? appThemes[0];
  const activeMode =
    semanticModes.find((mode) => mode.id === activeTheme.id) ??
    semanticModes[0];

  return (
    <main
      data-theme-pending={restoredThemeId === null ? "" : undefined}
      className="min-h-screen transition-colors duration-300"
      style={{
        background:
          activeTheme.id === "dark"
            ? `radial-gradient(ellipse 90% 52% at 50% -12%, ${activeTheme.surface} 0%, ${activeTheme.background} 68%)`
            : activeTheme.background,
        color: activeTheme.text,
        colorScheme: activeTheme.id,
      }}
    >
      <div className="mx-auto flex max-w-7xl flex-col gap-8 px-6 py-8 md:py-10">
        <HeroSection
          activeTheme={activeTheme}
          appThemes={appThemes}
          demoSections={demoSections}
          selectedSectionId={selectedSection.id}
          onSelectTheme={selectTheme}
          onSelectSection={selectSection}
        />

        <TokenSectionCard
          section={selectedSection}
          theme={activeMode.theme}
          mode={activeMode}
          fontStyles={fontStyles}
        />
      </div>
    </main>
  );
}
