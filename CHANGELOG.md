# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- SpigotHandler -> SpigotDownloader
- ServerHandler -> MinecraftServerCreator
- MavenHandler -> MavenProjectCreator
- FileManager -> FileCreator.
- DirectoryManager -> DirectoryCreator.
- The way to download spigot.
- MavenHandler class now uses the FileManager class to create the files.
- MavenHandler class now uses the DirectoryManager class to create the directories.

### Added

- FileManager class to handle the creation of the files.
- DirectoryManager class to handle the creation of the directories.
- The possibility to generate a server folder with the needed files (EULA.txt, start script) to run a Spigot server in the version of your choice.
- Changelog file.

## [0.1] - 2022-01-09

### Added

- The basic functionality of the plugin, namely the ability to generate a Maven template to develop a Spigot plugin in the version of your choice.
