import type { Metadata } from "next";
import localFont from "next/font/local";
import "./globals.css";

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

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={worldPro.variable}>
      <body className="font-sans antialiased">{children}</body>
    </html>
  );
}
