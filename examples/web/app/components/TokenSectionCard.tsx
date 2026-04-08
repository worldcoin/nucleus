import { ColorGroupSection } from "./ColorGroupSection";
import type { DemoSection, DemoTheme } from "../models";

export function TokenSectionCard({
  section,
  theme,
}: {
  section: DemoSection;
  theme: DemoTheme;
}) {
  return (
    <div className="space-y-8" style={{ color: theme.text }}>
      {section.groups.map((group) => (
        <ColorGroupSection key={group.id} group={group} theme={theme} />
      ))}
    </div>
  );
}
