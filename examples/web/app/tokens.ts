import localPrimitiveTokens from "./token-source.local";
import packagePrimitiveTokens from "./token-source.package";

const primitiveTokens =
  process.env.NEXT_PUBLIC_NUCLEUS_WEB_SOURCE === "package"
    ? packagePrimitiveTokens
    : localPrimitiveTokens;

export interface ColorGroup {
  name: string;
  colors: { name: string; value: string }[];
}

const primitiveGroupConfigs = [
  { name: "Grey", matcher: /^grey(\d+)$/ },
  { name: "Error", matcher: /^error(\d+)$/ },
  { name: "Warning", matcher: /^warning(\d+)$/ },
  { name: "Success", matcher: /^success(\d+)$/ },
  { name: "Info", matcher: /^info(\d+)$/ },
  { name: "World Blue", matcher: /^worldBlue(Primary|Secondary)$/ },
  { name: "Carrot Orange", matcher: /^carrotOrange(Primary|Secondary)$/ },
  { name: "Purple", matcher: /^purple(Primary|Secondary)$/ },
  { name: "Green", matcher: /^green(Primary|Secondary)$/ },
  { name: "Blue", matcher: /^blue(Primary|Secondary)$/ },
] as const;

function buildPrimitiveColors(): ColorGroup[] {
  return primitiveGroupConfigs
    .map(({ name, matcher }) => {
      const colors = Object.entries(primitiveTokens)
        .filter(([key]) => matcher.test(key))
        .map(([key, value]) => ({ name: key, value }));

      return { name, colors };
    })
    .filter((group) => group.colors.length > 0);
}

export const primitiveColors: ColorGroup[] = buildPrimitiveColors();
