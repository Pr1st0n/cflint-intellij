<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# CFLint Plugin Changelog

## [Unreleased]

## [0.3.2]
### Changed
- Update `pluginUntilBuild` to include `213.*` (2021.3.*)

## [0.3.1]
### Changed
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `1.6.10`
- Dependencies - upgrade `org.jetbrains.changelog` to `1.3.1`
- Dependencies - upgrade `org.jetbrains.intellij` to `1.3.0`
- Dependencies - upgrade `log4j-api` to `2.16.0`
- Dependencies - upgrade `log4j-core` to `2.16.0`
- Dependencies (GitHub Actions) - upgrade `actions/cache` to `v2.1.7`
- Dependencies (GitHub Actions) - upgrade `jtalk/url-health-check-action` to `v2.1`
- Dependencies (GitHub Actions) - upgrade `actions/checkout` to `v2.4.0`
- Dependencies (GitHub Actions) - upgrade `actions/upload-artifact` to `v2.3.0`

## [0.3.0]
### Added
- Introduced `next` branch in the root repository to make `main` always a stable one
- Dependabot check for GitHub Actions used in [workflow files](.github/workflows)
- Plugin Signing run configuration
- GitHub Actions: UI Tests workflow
- Suppress `UnusedProperty` inspection for the `kotlin.stdlib.default.dependency` in `gradle.properties`
- Use Gradle `wrapper` task to handle Gradle updates
- JVM compatibility version extracted to `gradle.properties` file
- `Publish Plugin` run configuration

### Changed
- GitHub Actions: Use Java 11
- Update `pluginVerifierIdeVersions` to `IU-2020.3.4`, `IU-2021.1.3`, `IU-212.4535.15`
- Change since/until build to `203-212.*`
- Upgrade Gradle Wrapper to `7.1.1`
- Gradle – Changelog plugin configuration update
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `1.5.21`
- Dependencies - upgrade `org.jetbrains.changelog` to `1.2.1`
- Dependencies - upgrade `org.jetbrains.intellij` to `1.1.4`
- Dependencies (GitHub Actions) - upgrade `actions/upload-artifact` to `v2.2.4`
- Dependencies (GitHub Actions) - upgrade `actions/cache` to `v2.1.6`
- Trigger GitHub Actions `Build` workflows only on pushes to `main` branch or pull request to avoid duplicated checks
- Remove reference to the `jcenter()` from Gradle configuration file
- Dependencies (GitHub Actions) - upgrade `actions/checkout` to `v2.3.4`
- Dependencies (GitHub Actions) - upgrade `actions/upload-release-asset` to `v1.0.2`
- Dependencies (GitHub Actions) - upgrade `actions/create-release` to `v1.1.4`

### Removed
- Removed `detekt`/`ktlint` integration

## [0.2.2]
### Changed
- Upgraded `org.jetbrains.kotlin.jvm` to `1.4.32`
- Deprecated support for `IU-2020.2`
- Improved logging for CFLint configuration load and file scanning failures
- *GitHub Actions*: changed release draft creation condition to use only `main` branch
- Updated plugin build version to support `IU-2021.1`

## [0.2.1]
### Added
- `properties` shorthand function for accessing `gradle.properties` in a cleaner way

### Changed
- Upgraded `org.jetbrains.intellij` to `0.7.2`
- Upgraded `org.jetbrains.changelog` to `1.1.2`
- Upgraded `Gradle Wrapper` to `6.8.3-bin`
- Upgraded `org.jetbrains.kotlin.jvm` to `1.4.30`
- Upgraded `org.jlleitschuh.gradle.ktlint` to `10.0.0`
- Upgraded `log4j-api` to `2.14.0`
- Upgraded `log4j-core` to `2.14.0`

## [0.2.0]
### Added
- New highlighting tests

### Changed
- Improved highlighting for most of the CFLint default rules
- Synced changes from [JetBrains/intellij-platform-plugin-template](https://github.com/JetBrains/intellij-platform-plugin-template)

## [0.1.2]
### Fixed
- Logger compatibility issue

## [0.1.1]
### Added
- Plugin ID

### Changed
- Plugin icon

## [0.1.0]
### Added
- Base [CFLint](https://github.com/cflint/CFLint) configuration support
- Base Coldfusion problems highlight logic in IntelliJ IDEA Ultimate
- Tests for limited set of [CFLint](https://github.com/cflint/CFLint) rules
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)