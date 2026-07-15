import { ButtonsSection } from "./ButtonsSection";
import { ColorPalettes } from "./ColorPalettes";
import { IconsSection } from "./IconsSection";
import { TypographySection } from "./TypographySection";
import type {
  DemoSection,
  DemoTheme,
  FontStyleToken,
  SemanticMode,
} from "../models";

export function TokenSectionCard({
  section,
  theme,
  mode,
  fontStyles,
}: {
  section: DemoSection;
  theme: DemoTheme;
  mode: SemanticMode;
  fontStyles: FontStyleToken[];
}) {
  switch (section.kind) {
    case "colors":
      return <ColorPalettes palettes={section.palettes} theme={theme} />;
    case "typography":
      return <TypographySection styles={section.styles} theme={theme} />;
    case "icons":
      return <IconsSection icons={section.icons} theme={theme} />;
    case "buttons":
      return (
        <ButtonsSection
          buttons={section.buttons}
          mode={mode}
          fontStyles={fontStyles}
          theme={theme}
        />
      );
  }
}
