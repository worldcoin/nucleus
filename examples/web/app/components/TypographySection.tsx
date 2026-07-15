import type { DemoTheme, FontStyleToken } from "../models";

const SAMPLE_TEXT = "The real human network";

export function TypographySection({
  styles,
  theme,
}: {
  styles: FontStyleToken[];
  theme: DemoTheme;
}) {
  return (
    <div className="space-y-2">
      {styles.map((style) => (
        <div
          key={style.name}
          className="flex flex-col gap-3 rounded-2xl border px-5 py-4 md:flex-row md:items-center"
          style={{
            backgroundColor: theme.surfaceAlt,
            borderColor: theme.border,
          }}
        >
          <div className="w-28 shrink-0">
            <div className="font-mono text-sm font-semibold">{style.name}</div>
            <div
              className="mt-1 font-mono text-[11px] leading-relaxed"
              style={{ color: theme.muted }}
            >
              {style.size} · {style.weight}
              <br />
              {style.letterSpacing} · {style.lineHeight}
            </div>
          </div>
          <div
            className="min-w-0 flex-1 truncate"
            style={{
              fontFamily: "var(--font-world-pro)",
              fontSize: style.size,
              fontWeight: style.weight,
              letterSpacing: style.letterSpacing,
              lineHeight: style.lineHeight,
            }}
          >
            {SAMPLE_TEXT}
          </div>
        </div>
      ))}
    </div>
  );
}
