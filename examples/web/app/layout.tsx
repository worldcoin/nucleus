import type { Metadata } from "next";
import localFont from "next/font/local";
import "./globals.css";
import { appThemes } from "./tokens";

const worldPro = localFont({
  src: "../../../build/web/fonts/WorldProMVP.ttf",
  variable: "--font-world-pro",
  weight: "100 900",
});

export const metadata: Metadata = {
  title: "Nucleus",
  description:
    "Design tokens for Nucleus — colors, typography, icons, and components across platforms.",
};

const darkTheme = appThemes.find((theme) => theme.id === "dark");

// runs before first paint so a stored dark theme never flashes the
// light-prerendered page
const themeInitScript = `try{document.documentElement.dataset.theme=localStorage.getItem("nucleus-theme")==="dark"?"dark":"light"}catch(e){}`;

// pre-hydration dark styling: paint the document dark right away and keep the
// light-prerendered content hidden until React re-renders it with the stored
// theme (page.tsx drops data-theme-pending once the theme is restored)
const themeInitStyles = `
:root[data-theme="dark"]{color-scheme:dark;--background:${darkTheme?.background};--foreground:${darkTheme?.text};}
:root[data-theme="dark"] main[data-theme-pending]{visibility:hidden;}
`;

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={worldPro.variable} suppressHydrationWarning>
      <head>
        <script dangerouslySetInnerHTML={{ __html: themeInitScript }} />
        <style dangerouslySetInnerHTML={{ __html: themeInitStyles }} />
      </head>
      <body className="font-sans antialiased">{children}</body>
    </html>
  );
}
