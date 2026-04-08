# iOS Example

iOS sample app with two token source schemes:

- `NucleusApp Local` compiles the generated Swift token file from `build/ios`
- `NucleusApp Package` imports the local Swift package artifact from `build/ios`

## Open

1. Run the root `npm run build` so `build/ios` exists.
2. Open `examples/ios/NucleusApp/NucleusApp.xcodeproj` in Xcode.
3. Choose either the `NucleusApp Local` or `NucleusApp Package` shared scheme.
4. Choose a simulator and run.

The sample app renders primitive swatches through a shared token alias, so both schemes render the same UI.
