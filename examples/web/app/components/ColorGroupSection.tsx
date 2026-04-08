import { ColorCard } from "./ColorCard";
import type { ColorGroup, DemoTheme } from "../models";

export function ColorGroupSection({
  group,
  theme,
}: {
  group: ColorGroup;
  theme: DemoTheme;
}) {
  return (
    <section className="space-y-3">
      <h3
        className="text-sm font-semibold tracking-[0.08em]"
        style={{ color: theme.muted }}
      >
        {group.name}
      </h3>
      <div className="grid grid-cols-1 gap-2 md:grid-cols-2 xl:grid-cols-3">
        {group.colors.map((color) => (
          <ColorCard
            key={color.name}
            name={color.name}
            value={color.value}
            theme={theme}
          />
        ))}
      </div>
    </section>
  );
}
