<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# CFLint Plugin Changelog

## [Unreleased]
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
