# OfflineSkins (Ornithe 1.8.9 Port)

An unofficial port of [zlainsama/OfflineSkins](https://github.com/zlainsama/OfflineSkins) (`1.8.9-v4`) to the **Ornithe** mod loader for Minecraft 1.8.9.

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
