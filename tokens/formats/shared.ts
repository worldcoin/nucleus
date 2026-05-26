import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

export function readTemplate(path: string): string {
  return readFileSync(resolve(ROOT, path), 'utf8');
}

export function camelCasePath(path: string[]): string {
  return path
    .map((segment, index) =>
      index === 0 ? segment : segment.charAt(0).toUpperCase() + segment.slice(1),
    )
    .join('');
}

export function kebabCasePath(path: string[]): string {
  return path.join('-').replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
}

export function kebabCase(value: string): string {
  return value.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
}

export function snakeCase(value: string): string {
  return value
    .replace(/\.[^./]+$/, '')
    .replace(/([a-z0-9])([A-Z])/g, '$1_$2')
    .toLowerCase();
}

export function publicColorPath(path: string[]): string[] {
  if ((path[0] === 'primitive' || path[0] === 'semantic') && path[1] === 'color') {
    return path.slice(2);
  }
  return path;
}

const REFERENCE_RE = /^\{(.+)\}$/;

export function parseReference(rawValue: string): string[] | null {
  const match = rawValue.match(REFERENCE_RE);
  return match ? match[1].split('.') : null;
}
