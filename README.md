# Nucleus

A cross-platform design system for the World ecosystem.

## Examples

Runnable demo apps for each platform live in [`examples/`](examples/). Our live web example can be found [here](https://worldcoin.github.io/nucleus/).

## Usage

### Android

Add the GitHub Packages Maven repository to `settings.gradle.kts`:

```kotlin
maven {
    url = uri("https://maven.pkg.github.com/worldcoin/nucleus")
    credentials {
        username = System.getenv("GITHUB_USER")
        password = System.getenv("GITHUB_TOKEN")
    }
}
```

```kotlin
implementation("com.worldcoin:nucleus:<version>")
```

### iOS

Add the Swift Package Manager dependency:

```swift
.package(url: "https://github.com/worldcoin/nucleus.git", branch: "generated/ios")
```

Or pin to a release tag (e.g. `vX.Y.Z-ios`), then add the products you need:

```swift
.product(name: "NucleusColors", package: "nucleus"),
.product(name: "NucleusFonts", package: "nucleus"),
```

### Web

```bash
npm install @worldcoin/nucleus
```

## Contributing

Tokens are authored as JSON and compiled to platform-native sources. See [CONTRIBUTING.md](CONTRIBUTING.md) for the architecture, build setup, and release process.
