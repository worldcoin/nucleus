"use client";

import { useEffect, useState } from "react";
import { HeroSection } from "./components/HeroSection";
import { TokenSectionCard } from "./components/TokenSectionCard";
import type { AppTheme } from "./models";
import { appThemes, demoSections, fontStyles, semanticModes } from "./tokens";

const THEME_STORAGE_KEY = "nucleus-theme";

export default function Home() {
  const [selectedSectionId, setSelectedSectionId] = useState(
    demoSections[0].id,
  );
  const [selectedThemeId, setSelectedThemeId] =
    useState<AppTheme["id"]>("light");

  useEffect(() => {
    const stored = window.localStorage.getItem(THEME_STORAGE_KEY);
    if (stored === "light" || stored === "dark") {
      const restoreTheme = window.setTimeout(
        () => setSelectedThemeId(stored),
        0,
      );
      return () => window.clearTimeout(restoreTheme);
    }
  }, []);

  const selectTheme = (id: AppTheme["id"]) => {
    setSelectedThemeId(id);
    window.localStorage.setItem(THEME_STORAGE_KEY, id);
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
          onSelectSection={setSelectedSectionId}
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
