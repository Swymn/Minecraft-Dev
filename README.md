# Minecraft Dev

## Introduction

This plugin provides a number of features to help you develop Minecraft plugins. It is currently in a very early stage of development, so expect bugs and missing features.

## How it works

First of all, you need to install the plugin. You can do this by downloading the latest version from the [releases page](https://github.com/Swymn/Minecraft-Dev). Once you have done this, you can open a shell in the directory where you downloaded the plugin and run `java -jar minecraft-dev.jar <version> <plugin/server> <name of your plugin>`. This will ask you a few questions and then generate a template for your plugin. You can then open the project in your IDE of choice.

## Plugin generation

The plugin can generate a template for a plugin. This includes a `plugin.yml` file, a `Main` class, with some code inside. It also generates a `pom.xml` file for Maven, so you can easily build your plugin. Once the plugin is generated, you can build it by running `mvn clean package` in the directory where you generated the plugin. This will generate a `.jar` file in the `target` directory. You can then copy this file to your `plugins` directory and start your server.
