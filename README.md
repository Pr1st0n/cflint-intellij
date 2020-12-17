# CFLint Plugin for IntelliJ IDEA Ultimate

![Build](https://github.com/Pr1st0n/cflint-intellij/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/15567.svg)](https://plugins.jetbrains.com/plugin/15567)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/15567.svg)](https://plugins.jetbrains.com/plugin/15567)

<!-- Plugin description -->
This plugin provides support for Coldfusion static code analyzer [CFLint](https://github.com/cflint/CFLint) in IntelliJ IDEA Ultimate.

The implementation relies on base [CFML Support](https://github.com/JetBrains/intellij-plugins/tree/master/CFML) plugin, which is only available for IntelliJ IDEA Ultimate, so no other JetBrains products are supported.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "cflint"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/Pr1st0n/cflint-intellij/releases/latest) and install it manually using
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Usage

Make sure that plugin is enabled:

- <kbd>Preferences</kbd> > <kbd>Editor</kbd> > <kbd>Inspections</kbd> > <kbd>CFML</kbd> > <kbd>Code quality</kbd> > <kbd>CFLint</kbd>
- <kbd>Preferences</kbd> > <kbd>Languages & Frameworks</kbd> > <kbd>Coldfusion</kbd> > <kbd>CFLint</kbd>

There are 2 ways of configuring CFLint rules at the moment:

- [Default set of rules](https://github.com/cflint/CFLint/blob/master/src/main/resources/cflint.definition.json) if configuration file is not provided
- Custom *.cflintrc* configuration file in the root of the project (refer to [CFLint#configuration](https://github.com/cflint/CFLint#configuration) for details)

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
