import Foundation
import PackagePlugin

@main
struct GenerateColorBindingsPlugin: BuildToolPlugin {
    func createBuildCommands(context: PluginContext, target: Target) async throws -> [Command] {
        guard let target = target as? SourceModuleTarget else { return [] }

        let swiftFiles = target.sourceFiles.filter { $0.url.pathExtension == "swift" }
        let script = context.package.directoryURL.appending(path: "Plugins/GenerateColorBindings/script.swift")
        let output = context.pluginWorkDirectoryURL.appending(path: "WLDColor+Generated.swift")

        return [
            .buildCommand(
                displayName: "Generate Color Bindings",
                executable: URL(fileURLWithPath: "/usr/bin/xcrun"),
                arguments: ["--sdk", "macosx", "swift", script.path(), output.path()]
                    + swiftFiles.map { $0.url.path() },
                inputFiles: [script] + swiftFiles.map { $0.url },
                outputFiles: [output]
            ),
        ]
    }
}
