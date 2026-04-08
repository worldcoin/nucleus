import localTokenSet from "./token-source.local";
import packageTokenSet from "./token-source.package";
import type {
  AppTheme,
  ColorGroup,
  DemoSection,
  DemoTheme,
  SemanticMode,
} from "./models";

const tokenSet =
  process.env.NEXT_PUBLIC_NUCLEUS_WEB_SOURCE === "package"
    ? packageTokenSet
    : localTokenSet;

type TokenMap = Record<string, string>;
type TokenRoot = "primitive" | "semantic";

interface ResolvedToken {
  name: string;
  value: string;
  family: string;
  tone: number | null;
}

const primitiveTokens: TokenMap = tokenSet.primitive;
const semanticLightTokens: TokenMap = tokenSet.semanticLight;
const semanticDarkTokens: TokenMap = tokenSet.semanticDark;

function referenceTokenName(value: string): { root: TokenRoot; key: string } | null {
  const match = value.match(/^\{(primitive|semantic)\.color\.(.+)\}$/);
  if (!match) {
    return null;
  }

  const [, root, path] = match;
  const key = path
    .split(".")
    .map((segment, index) =>
      index === 0 ? segment : segment.charAt(0).toUpperCase() + segment.slice(1),
    )
    .join("");

  return { root: root as TokenRoot, key };
}

function resolveColorValue(value: string, semanticTokens?: TokenMap): string {
  const reference = referenceTokenName(value);
  if (!reference) {
    return value;
  }

  const sourceTokens =
    reference.root === "primitive" ? primitiveTokens : semanticTokens;
  const resolved = sourceTokens?.[reference.key];

  if (!resolved || resolved === value) {
    return value;
  }

  return resolveColorValue(resolved, semanticTokens);
}

function tokenFamilyKey(name: string): string {
  if (name === "white" || name === "black") {
    return "base";
  }

  const match = name.match(/^[a-z]+/);
  return match?.[0] ?? name;
}

function tokenTone(name: string): number | null {
  const match = name.match(/(\d+)$/);
  return match ? Number(match[1]) : null;
}

function familyTitle(key: string): string {
  return key.charAt(0).toUpperCase() + key.slice(1);
}

function resolveTokenEntries(tokens: TokenMap, semanticTokens?: TokenMap): ResolvedToken[] {
  return Object.entries(tokens).map(([name, rawValue]) => ({
    name,
    value: resolveColorValue(rawValue, semanticTokens),
    family: tokenFamilyKey(name),
    tone: tokenTone(name),
  }));
}

function buildGroups(entries: ResolvedToken[]): ColorGroup[] {
  const groups = new Map<string, ColorGroup>();

  for (const entry of entries) {
    const key = entry.family;
    const group = groups.get(key) ?? {
      id: key,
      name: familyTitle(key),
      colors: [],
    };

    group.colors.push({
      name: entry.name,
      value: entry.value,
    });

    groups.set(key, group);
  }

  return [...groups.values()];
}

function groupEntriesByFamily(entries: ResolvedToken[]): Map<string, ResolvedToken[]> {
  const groups = new Map<string, ResolvedToken[]>();

  for (const entry of entries) {
    const familyEntries = groups.get(entry.family) ?? [];
    familyEntries.push(entry);
    groups.set(entry.family, familyEntries);
  }

  return groups;
}

function isOpaque(value: string): boolean {
  const normalized = value.replace("#", "");
  return normalized.length !== 8 || normalized.endsWith("FF");
}

function pickOpaqueFamilyToken(
  entries: ResolvedToken[] | undefined,
  index = 0,
): string | undefined {
  if (!entries?.length) {
    return undefined;
  }

  const opaqueEntries = entries.filter((entry) => isOpaque(entry.value));
  return opaqueEntries[index]?.value ?? opaqueEntries[0]?.value;
}

function pickLastDistinctOpaqueToken(
  entries: ResolvedToken[] | undefined,
  excluded: string[],
): string | undefined {
  if (!entries?.length) {
    return undefined;
  }

  const opaqueEntries = entries.filter((entry) => isOpaque(entry.value));
  return [...opaqueEntries]
    .reverse()
    .find((entry) => !excluded.includes(entry.value))
    ?.value;
}

function pickTone(entries: ResolvedToken[] | undefined, targets: number[]): string | undefined {
  const tonedEntries = entries?.filter(
    (entry): entry is ResolvedToken & { tone: number } => entry.tone !== null,
  );

  if (!tonedEntries?.length) {
    return entries?.[0]?.value;
  }

  for (const target of targets) {
    const exact = tonedEntries.find((entry) => entry.tone === target);
    if (exact) {
      return exact.value;
    }
  }

  return tonedEntries.reduce((best, entry) => {
    return Math.abs(entry.tone - targets[0]) < Math.abs(best.tone - targets[0])
      ? entry
      : best;
  }).value;
}

function pickNeutralFamily(groups: Map<string, ResolvedToken[]>): ResolvedToken[] {
  const candidates = ["grey", "gray", "neutral", "stone", "slate"];

  for (const family of candidates) {
    const entries = groups.get(family);
    if (entries?.some((entry) => entry.tone !== null)) {
      return entries;
    }
  }

  return [...groups.values()]
    .filter((entries) => entries.some((entry) => entry.tone !== null))
    .sort((left, right) => right.length - left.length)[0] ?? [];
}

function pickAccentFamily(
  groups: Map<string, ResolvedToken[]>,
  neutralFamily: string,
): ResolvedToken[] {
  const priorities = ["info", "accent", "brand", "blue"];

  for (const family of priorities) {
    const entries = groups.get(family);
    if (entries?.length) {
      return entries;
    }
  }

  return [...groups.entries()]
    .filter(([family]) => family !== "base" && family !== neutralFamily)
    .map(([, entries]) => entries)[0] ?? [];
}

function contrastColor(color: string, light = "#FFFFFF", dark = "#17181A"): string {
  const normalized = color.replace("#", "");
  const rgb = normalized.slice(0, 6);
  const red = Number.parseInt(rgb.slice(0, 2), 16);
  const green = Number.parseInt(rgb.slice(2, 4), 16);
  const blue = Number.parseInt(rgb.slice(4, 6), 16);
  const luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255;

  return luminance > 0.6 ? dark : light;
}

function buildNeutralPalette(entries: ResolvedToken[]): DemoTheme {
  const groups = groupEntriesByFamily(entries);
  const neutralEntries = pickNeutralFamily(groups);
  const neutralFamily = neutralEntries[0]?.family ?? "grey";
  const baseEntries = groups.get("base");
  const white = baseEntries?.find((entry) => entry.name === "white")?.value ?? "#FFFFFF";
  const black = baseEntries?.find((entry) => entry.name === "black")?.value ?? "#17181A";
  const accent = pickTone(pickAccentFamily(groups, neutralFamily), [600, 500, 400]) ?? "#0064EE";

  return {
    background: pickTone(neutralEntries, [100, 200]) ?? white,
    surface: white,
    surfaceAlt: white,
    border: pickTone(neutralEntries, [300, 400]) ?? "#D7D9DC",
    text: pickTone(neutralEntries, [950, 900, 800]) ?? black,
    muted: pickTone(neutralEntries, [700, 600, 500]) ?? black,
    accent,
    accentContent: contrastColor(accent, white, black),
  };
}

function buildSemanticTheme(entries: ResolvedToken[], fallback: DemoTheme): DemoTheme {
  const groups = groupEntriesByFamily(entries);
  const surfaceEntries = groups.get("surface");
  const textEntries = groups.get("text");
  const iconEntries = groups.get("icon");
  const borderEntries = groups.get("border");
  const accentEntries = groups.get("accent");
  const actionEntries = groups.get("action");
  const statusEntries = groups.get("status");
  const surface = pickOpaqueFamilyToken(surfaceEntries, 0) ?? fallback.surface;
  const background = pickOpaqueFamilyToken(surfaceEntries, 1) ?? surface;
  const surfaceAlt =
    pickLastDistinctOpaqueToken(surfaceEntries, [surface, background]) ??
    pickOpaqueFamilyToken(surfaceEntries, 0) ??
    fallback.surfaceAlt;
  const accent =
    pickOpaqueFamilyToken(accentEntries, 0) ??
    pickOpaqueFamilyToken(actionEntries, 0) ??
    pickOpaqueFamilyToken(statusEntries, 0) ??
    fallback.accent;
  const accentContent =
    pickOpaqueFamilyToken(accentEntries, 1) ??
    pickOpaqueFamilyToken(actionEntries, 1) ??
    contrastColor(accent);

  return {
    background,
    surface,
    surfaceAlt,
    border: pickOpaqueFamilyToken(borderEntries, 1) ?? pickOpaqueFamilyToken(borderEntries, 0) ?? fallback.border,
    text: pickOpaqueFamilyToken(textEntries, 0) ?? pickOpaqueFamilyToken(iconEntries, 0) ?? fallback.text,
    muted: pickOpaqueFamilyToken(textEntries, 1) ?? pickOpaqueFamilyToken(iconEntries, 1) ?? fallback.muted,
    accent,
    accentContent,
  };
}

function buildSemanticMode(
  id: SemanticMode["id"],
  name: SemanticMode["name"],
  tokens: TokenMap,
  fallback: DemoTheme,
): SemanticMode {
  const entries = resolveTokenEntries(tokens, tokens);
  const groups = buildGroups(entries);

  return {
    id,
    name,
    theme: buildSemanticTheme(entries, fallback),
    groups,
  };
}

const primitiveEntries = resolveTokenEntries(primitiveTokens);
const neutralPalette = buildNeutralPalette(primitiveEntries);

export const semanticModes: SemanticMode[] = [
  buildSemanticMode("light", "Light", semanticLightTokens, neutralPalette),
  buildSemanticMode("dark", "Dark", semanticDarkTokens, neutralPalette),
];

export const appThemes: AppTheme[] = semanticModes.map((mode) => ({
  id: mode.id,
  name: mode.name,
  background: mode.theme.background,
  surface: mode.theme.surface,
  border: mode.theme.border,
  text: mode.theme.text,
  muted: mode.theme.muted,
}));

const primitiveGroups = buildGroups(primitiveEntries);

export const demoSections: DemoSection[] = [
  {
    id: "primitive",
    label: "Primitive",
    groups: primitiveGroups,
  },
  ...semanticModes.map((mode) => ({
    id: `semantic-${mode.id}`,
    label: mode.name,
    groups: mode.groups,
  })),
];
