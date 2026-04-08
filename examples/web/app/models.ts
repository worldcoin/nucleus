export interface ColorToken {
  name: string;
  value: string;
}

export interface ColorGroup {
  id: string;
  name: string;
  colors: ColorToken[];
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

export interface DemoSection {
  id: string;
  label: string;
  groups: ColorGroup[];
}
