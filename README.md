# Minecraft Dev

## Introduction

This plugin provides a number of features to help you develop Minecraft plugins. It is currently in a very early stage of development, so expect bugs and missing features.

## Pre-requisites

You need to have Java 17 or higher installed on your system. You can download it from [here](https://www.oracle.com/fr/java/technologies/downloads/#java17).  
You need to have Maven installed on your system. You can download it from [here](https://maven.apache.org/download.cgi).

## How it works

First of all, you need to install the plugin. You can do this by downloading the latest version from the [releases page](https://github.com/Swymn/Minecraft-Dev/releases). Once you have done this, you can open a shell in the directory where you downloaded the plugin and run `java -jar minecraft-dev.jar <version> <plugin/server> <name of your plugin>`. This will ask you a few questions and then generate a template for your plugin. You can then open the project in your IDE of choice.

## Plugin generation

The plugin can generate a template for a plugin. This includes a `plugin.yml` file, a `Main` class, with some code inside. It also generates a `pom.xml` file for Maven, so you can easily build your plugin. Once the plugin is generated, you can build it by running `mvn clean package` in the directory where you generated the plugin. This will generate a `.jar` file in the `target` directory. You can then copy this file to your `plugins` directory and start your server.

## Server generation

The plugin can also generate a template for a server. This includes a `eula.txt` file, a `start.sh` file, and a `spigot.jar` file. You can then start your server by running `sh start.sh` in the directory where you generated the server. This will start your server, and you can then connect to it using your Minecraft client.