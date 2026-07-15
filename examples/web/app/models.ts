export interface ColorToken {
  name: string;
  value: string;
}

export interface ColorGroup {
  id: string;
  name: string;
  colors: ColorToken[];
}

export interface FontStyleToken {
  name: string;
  fontName: string;
  size: string;
  weight: number;
  letterSpacing: string;
  lineHeight: number;
}

export type IconVariant = "outline" | "regular" | "solid";

export interface IconEntry {
  name: string;
  svg: Partial<Record<IconVariant, string>>;
}

export interface ButtonSpec {
  variant: string;
  size: number;
  background: string;
  content: string;
  border?: string;
  height: number;
  cornerRadius: number;
  paddingHorizontal: number;
  paddingVertical: number;
  font: string;
  pressedInset: number;
}

export interface DemoTheme {
  background: string;
  surface: string;
  surfaceAlt: string;
  border: string;
  text: string;
  muted: string;
  accent: string;
  accentContent: string;
}

export interface SemanticMode {
  id: "light" | "dark";
  name: string;
  theme: DemoTheme;
  groups: ColorGroup[];
  resolvedTokens: Record<string, string>;
}

export interface AppTheme {
  id: SemanticMode["id"];
  name: string;
  background: string;
  surface: string;
  border: string;
  text: string;
  muted: string;
}

interface DemoSectionBase {
  id: string;
  label: string;
}

export interface ColorPalette {
  id: string;
  label: string;
  groups: ColorGroup[];
}

export interface ColorsSection extends DemoSectionBase {
  kind: "colors";
  palettes: ColorPalette[];
}

export interface TypographySection extends DemoSectionBase {
  kind: "typography";
  styles: FontStyleToken[];
}

export interface IconsSection extends DemoSectionBase {
  kind: "icons";
  icons: IconEntry[];
}

export interface ButtonsSection extends DemoSectionBase {
  kind: "buttons";
  buttons: ButtonSpec[];
}

export type DemoSection =
  | ColorsSection
  | TypographySection
  | IconsSection
  | ButtonsSection;
