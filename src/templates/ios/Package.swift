// swift-tools-version: 6.1

import PackageDescription

// MARK: - Products

let products: [Product] = [
    .library(
        name: "WLDColor",
        targets: ["WLDColor"]
    ),
    .plugin(
        name: "GenerateColorBindings",
        targets: ["GenerateColorBindings"]
    ),
]

// MARK: - Targets

let targets: [Target] = [
    .target(
        name: "WLDColor",
        plugins: [.plugin(name: "GenerateColorBindings")]
    ),
]

// MARK: - Plugins

let plugins: [Target] = [
    .plugin(
        name: "GenerateColorBindings",
        capability: .buildTool(),
        exclude: ["script.swift"]
    ),
]

// MARK: - Package

let package = Package(
    name: "Nucleus",
    platforms: [
        .iOS(.v15),
    ],
    products: products,
    targets: targets + plugins
)
