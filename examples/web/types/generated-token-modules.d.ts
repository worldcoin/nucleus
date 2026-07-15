interface GeneratedFontStyle {
  fontName: string;
  size: string;
  weight: number;
  letterSpacing: string;
  lineHeight: number;
}

interface GeneratedIcon {
  files: Record<string, string>;
  svg: Record<string, string>;
}

interface GeneratedButtonConfig {
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

declare module "../../../build/web/nucleus-primitive-colors.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "../../../build/web/nucleus-semantic-colors-light.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "../../../build/web/nucleus-semantic-colors-dark.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "../../../build/web/nucleus-fonts.json" {
  const tokens: Record<string, GeneratedFontStyle>;
  export default tokens;
}

declare module "../../../build/web/nucleus-icons.json" {
  const tokens: Record<string, GeneratedIcon>;
  export default tokens;
}

declare module "../../../build/web/nucleus-button.json" {
  const tokens: Record<string, GeneratedButtonConfig>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-primitive-colors.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-semantic-colors-light.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-semantic-colors-dark.json" {
  const tokens: Record<string, string>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-fonts.json" {
  const tokens: Record<string, GeneratedFontStyle>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-icons.json" {
  const tokens: Record<string, GeneratedIcon>;
  export default tokens;
}

declare module "@worldcoin/nucleus/nucleus-button.json" {
  const tokens: Record<string, GeneratedButtonConfig>;
  export default tokens;
}
