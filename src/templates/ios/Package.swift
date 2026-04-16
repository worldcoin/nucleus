// swift-tools-version: 5.9

import PackageDescription

let package = Package(
    name: "NucleusColors",
    platforms: [
        .iOS(.v15),
    ],
    products: [
        .library(
            name: "NucleusColors",
            targets: ["NucleusColors"]
        ),
    ],
    targets: [
        .target(
            name: "NucleusColors",
            path: "Sources/NucleusColors"
        ),
    ]
)
