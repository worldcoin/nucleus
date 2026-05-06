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
    ]
)
