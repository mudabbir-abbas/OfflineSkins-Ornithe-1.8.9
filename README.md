# OfflineSkins (Ornithe 1.8.9 Port)

An unofficial port of [zlainsama/OfflineSkins](https://github.com/zlainsama/OfflineSkins) (`1.8.9-v4`) to the **Ornithe** mod loader for Minecraft 1.8.9.

## About this port

The port was written with AI assistance on top of the original code. It is a proof of concept. I don't plan to add new features or promise regular updates, though I may fix things when I feel like it. Bug reports are welcome, but please don't expect a quick response.

All credit for the original mod goes to zlainsama. If the original author, or anyone else, wants to take this over, use it as a starting point, or fold it into the upstream project, you're welcome to. It's MIT licensed, so feel free to fork and make it your own.

## Features

- **Local Skins & Capes:** Loads custom PNG skins and capes from `<instance>/.minecraft/cachedImages/skins/` and `cachedImages/capes/` (`<username>.png` or `<uuid>.png`).
- **Tab List & Skull Blocks:** Renders custom skins on player Tab overlays (compatible with HUD/client mods) and placed player skull blocks.
- **Fallback Providers:** Supports Mojang, Crafatar, and optional Custom Server lookups configured via `config/offlineskins.properties`.

## Installation

1. Set up an **Ornithe 1.8.9** instance (e.g., via the Ornithe Installer for Prism Launcher).
2. Download the mod `.jar` from **Releases** (do not use `-sources.jar`) and place it in `.minecraft/mods/`.
3. Launch the game once to generate `.minecraft/cachedImages/skins/` and `.minecraft/config/offlineskins.properties`, then drop your `<YourUsername>.png` skin file into `cachedImages/skins/`.

## Building from Source

Requires Gradle (tested on Gradle 9.x and Java 25):

```bash
gradle build
```

The compiled mod `.jar` will be output to `build/libs/`.

## Credits & License

Original mod by [zlainsama](https://github.com/zlainsama/OfflineSkins). This port is released under the MIT License, see [LICENSE.txt](LICENSE.txt
