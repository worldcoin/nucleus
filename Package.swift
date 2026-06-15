// swift-tools-version: 6.2

import PackageDescription

let package = Package(
    name: "Nucleus",
    platforms: [
        .iOS(.v15),
    ],
    products: [
        .library(
            name: "NucleusColors",
            targets: ["NucleusColors"]
        ),
        .library(
            name: "NucleusFonts",
            targets: ["NucleusFonts"]
        ),
        .library(
            name: "NucleusButtons",
            targets: ["NucleusButtons"]
        ),
        .library(
            name: "NucleusIcons",
            targets: ["NucleusIcons"]
        ),
        .library(
            name: "NucleusTokens",
            targets: ["NucleusTokens"]
        ),
    ],
    targets: [
        // Zero-dependency protocol target. Each primitive conforms in its own module so consumers
        // only link what they import — no target that depends on every module.
        .target(
            name: "NucleusTokens",
            path: "ios/Sources/NucleusTokens"
        ),
        .target(
            name: "NucleusColors",
            dependencies: ["NucleusTokens"],
            path: "ios/Sources/NucleusColors"
        ),
        .target(
            name: "NucleusFonts",
            dependencies: ["NucleusTokens"],
            path: "ios/Sources/NucleusFonts",
            resources: [
                .process("Resources/Fonts"),
            ]
        ),
        .target(
            name: "NucleusButtons",
            dependencies: ["NucleusTokens", "NucleusColors", "NucleusFonts"],
            path: "ios/Sources/NucleusButtons"
        ),
        .target(
            name: "NucleusIcons",
            dependencies: ["NucleusTokens"],
            path: "ios/Sources/NucleusIcons",
            resources: [
                .process("Resources/Icons.xcassets"),
            ]
        ),
    ]
)
