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
            name: "NucleusTokenResolver",
            targets: ["NucleusTokenResolver"]
        ),
    ],
    targets: [
        .target(
            name: "NucleusColors",
            path: "ios/Sources/NucleusColors"
        ),
        .target(
            name: "NucleusFonts",
            path: "ios/Sources/NucleusFonts",
            resources: [
                .process("Resources/Fonts"),
            ]
        ),
        .target(
            name: "NucleusButtons",
            dependencies: ["NucleusColors", "NucleusFonts"],
            path: "ios/Sources/NucleusButtons"
        ),
        .target(
            name: "NucleusIcons",
            path: "ios/Sources/NucleusIcons",
            resources: [
                .process("Resources/Icons.xcassets"),
            ]
        ),
        .target(
            name: "NucleusTokenResolver",
            dependencies: [
                "NucleusColors",
                "NucleusFonts",
                "NucleusButtons",
                "NucleusIcons",
            ],
            path: "ios/Sources/NucleusTokenResolver"
        ),
    ]
)
