import type { NextConfig } from "next";

// Set by CI when deploying to GitHub Pages, where the site is served from a
// sub-path (https://<owner>.github.io/<repo>/). Unset for local dev.
const basePath = process.env.NEXT_PUBLIC_BASE_PATH;

const nextConfig: NextConfig = {
  output: "export",
  basePath: basePath || undefined,
};

export default nextConfig;
