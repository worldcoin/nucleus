"use client";

import { useState } from "react";
import { HeroSection } from "./components/HeroSection";
import { TokenSectionCard } from "./components/TokenSectionCard";
import type { AppTheme, DemoTheme } from "./models";
import { appThemes, demoSections } from "./tokens";

function fallbackSectionTheme(theme: AppTheme): DemoTheme {
  return {
    background: theme.surface,
    surface: theme.surface,
    surfaceAlt: theme.surface,
    border: theme.border,
    text: theme.text,
    muted: theme.muted,
    accent: theme.text,
    accentContent: theme.background,
  };
}

export default function Home() {
  const [selectedSectionId, setSelectedSectionId] = useState(demoSections[0].id);
  const [selectedThemeId, setSelectedThemeId] = useState<AppTheme["id"]>("dark");

  const selectedSection =
    demoSections.find((section) => section.id === selectedSectionId) ?? demoSections[0];
  const activeTheme =
    appThemes.find((theme) => theme.id === selectedThemeId) ?? appThemes[0];
  const chromeTheme = fallbackSectionTheme(activeTheme);

  return (
    <main
      className="min-h-screen"
      style={{
        backgroundColor: activeTheme.background,
        color: activeTheme.text,
      }}
    >
      <div className="mx-auto flex max-w-7xl flex-col gap-8 px-6 py-8 md:py-10">
        <HeroSection
          activeTheme={activeTheme}
          appThemes={appThemes}
          demoSections={demoSections}
          selectedSectionId={selectedSection.id}
          onSelectTheme={setSelectedThemeId}
          onSelectSection={setSelectedSectionId}
        />

        <TokenSectionCard section={selectedSection} theme={chromeTheme} />
      </div>
    </main>
  );
}
