# Android Example

Android sample app with two token source variants:

- `local` wraps generated Kotlin sources from `build/android`
- `package` resolves the Maven artifact `com.worldcoin:nucleus`

## Requirements

- Android Studio Koala or newer
- Android SDK 35

## Open

Run the root `npm run build` first so `build/android` exists.

Open `playground/android` in Android Studio.

Use these build variants:

- `localDebug` or `localRelease` for generated local sources
- `packageDebug` or `packageRelease` for the Maven package

The `package` variants resolve `com.worldcoin:nucleus` from `mavenLocal()` first, then GitHub Packages. To use GitHub Packages, set `githubPackagesUser` and `githubPackagesToken` in your Gradle properties or `GITHUB_USER` / `GITHUB_TOKEN` in your environment.
