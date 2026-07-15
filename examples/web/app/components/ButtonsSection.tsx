import type {
  ButtonSpec,
  DemoTheme,
  FontStyleToken,
  SemanticMode,
} from "../models";
import { resolveSemanticReference } from "../tokens";

function buttonLabel(variant: string, size: number): string {
  return `${variant.charAt(0).toUpperCase()}${variant.slice(1)} ${size}`;
}

function DemoButton({
  spec,
  mode,
  fontsByName,
}: {
  spec: ButtonSpec;
  mode: SemanticMode;
  fontsByName: Map<string, FontStyleToken>;
}) {
  const background = resolveSemanticReference(spec.background, mode);
  const content = resolveSemanticReference(spec.content, mode);
  const border = spec.border
    ? resolveSemanticReference(spec.border, mode)
    : undefined;
  const font = fontsByName.get(spec.font);

  return (
    <button
      className="shrink-0 transition-transform active:translate-y-px"
      style={{
        backgroundColor: background,
        color: content,
        height: spec.height,
        borderRadius: spec.cornerRadius,
        padding: `${spec.paddingVertical}px ${spec.paddingHorizontal}px`,
        border: border ? `1px solid ${border}` : "none",
        fontFamily: "var(--font-world-pro)",
        fontSize: font?.size,
        fontWeight: font?.weight,
        letterSpacing: font?.letterSpacing,
        lineHeight: font?.lineHeight,
      }}
    >
      {buttonLabel(spec.variant, spec.size)}
    </button>
  );
}

export function ButtonsSection({
  buttons,
  mode,
  fontStyles,
  theme,
}: {
  buttons: ButtonSpec[];
  mode: SemanticMode;
  fontStyles: FontStyleToken[];
  theme: DemoTheme;
}) {
  const fontsByName = new Map(fontStyles.map((style) => [style.name, style]));
  const variants = [...new Set(buttons.map((spec) => spec.variant))];

  return (
    <div className="space-y-8">
      {variants.map((variant) => {
        const specs = buttons.filter((spec) => spec.variant === variant);
        const sample = specs[0];

        return (
          <section key={variant} className="space-y-3">
            <h3
              className="text-sm font-semibold capitalize tracking-[0.08em]"
              style={{ color: theme.muted }}
            >
              {variant}
            </h3>
            <div
              className="flex flex-col gap-4 rounded-2xl border px-5 py-5 md:flex-row md:items-center md:justify-between"
              style={{
                backgroundColor: theme.surfaceAlt,
                borderColor: theme.border,
              }}
            >
              <div className="flex flex-wrap items-center gap-3">
                {specs.map((spec) => (
                  <DemoButton
                    key={`${spec.variant}-${spec.size}`}
                    spec={spec}
                    mode={mode}
                    fontsByName={fontsByName}
                  />
                ))}
              </div>
              <div
                className="shrink-0 font-mono text-[11px] leading-relaxed"
                style={{ color: theme.muted }}
              >
                {sample.background}
                <br />
                {sample.content}
                {sample.border ? (
                  <>
                    <br />
                    {sample.border}
                  </>
                ) : null}
              </div>
            </div>
          </section>
        );
      })}
    </div>
  );
}
