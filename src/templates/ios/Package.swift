// swift-tools-version: 5.9

import PackageDescription

let package = Package(
    name: "Nucleus",
    platforms: [
        .iOS(.v15),
    ],
    products: [
        .library(
            name: "Nucleus",
            targets: ["Nucleus"]
        ),
    ],
    targets: [
        .target(
            name: "Nucleus",
            path: "Sources/Nucleus"
        ),
    ]
)
