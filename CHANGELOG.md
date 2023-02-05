# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- The name now can not contain spaces and slashes.

## [0.2.2]

### Fixed

- Fixed the generated pom.xml file, sourceEncoding and repository was missing.
- Fixed the name of the generated spigot jar file.
- Correction of spelling mistakes in the logs.

## [0.2.1] - 2023-01-29

### Fixed

- Some logs were not displayed correctly.
- The spigot download wasn't move to the right directory.

### Changed

- The README.md file.

## [0.2] - 2023-01-29

### Changed

- Edit the name of the projet (lowercase, and without spaces).
- Rename SpigotHandler into SpigotDownloader
- Rename ServerHandler into MinecraftServerCreator
- Rename MavenHandler into MavenProjectCreator
- Rename FileManager into FileCreator.
- Rename DirectoryManager into DirectoryCreator.
- The way to download spigot.
- MavenHandler class now uses the FileManager class to create the files.
- MavenHandler class now uses the DirectoryManager class to create the directories.

### Added

- The log when the code is running
- The possibility to generate only the minecraft server files.
- State and color enum, to handle the prefix and the color of the logs.
- Logger class to handle all the logs, and inputs.
- FileManager class to handle the creation of the files.
- DirectoryManager class to handle the creation of the directories.
- The possibility to generate a server folder with the needed files (EULA.txt, start script) to run a Spigot server in the version of your choice.
- Changelog file.

## [0.1] - 2023-01-09

### Added

- The basic functionality of the plugin, namely the ability to generate a Maven template to develop a Spigot plugin in the version of your choice.
