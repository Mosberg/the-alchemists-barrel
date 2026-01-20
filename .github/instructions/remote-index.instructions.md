# Remote Index — The Alchemist’s Barrel (tab)

This file is a compact, high-signal index for Copilot/agents to understand the repository layout and where to look first.
For behavior rules and conventions, see `.github/copilot-instructions.md`.

## What this repo is

- Minecraft mod for **Fabric** on **Minecraft 1.21.11**, using **Yarn mappings** and a split-source layout (main vs client).
- Strong emphasis on data-driven content and data generation.

## Repo layout (canonical)

### Java sources

- `src/main/java/dk/mosberg/tab/` — server + common logic (must not reference client-only code)
- `src/client/java/dk/mosberg/tab/client/` — client-only logic (rendering, client init, keybinds, HUD, client events)
- `src/client/java/dk/mosberg/tab/client/datagen/` — datagen bootstrap and glue code
- `src/client/java/dk/mosberg/tab/client/datagen/provider/` — providers (recipes, loot, tags, translations, models, etc.)

### Resources

- `src/main/resources/assets/tab/` — runtime assets (textures, lang, models)
- `src/main/resources/data/tab/` — runtime datapack JSON
- `src/main/generated/resources/` — datagen outputs (generated; never edit by hand)

## Start-here files

- `build.gradle` — dependencies, Loom config, resource processing, datagen tasks
- `settings.gradle` — project naming and Gradle settings
- `gradle.properties` — mod metadata + version pins
- `fabric.mod.json` — descriptor (often templated/processed; don’t edit if generated)

Suggested key classes (if present):

- `TheAlchemistsBarrel.java` — entrypoint / initializer
- `ModContent.java` — central registration hub
- `MaterialRegistry.java`, `MaterialDef.java` — material loading/registry model

## Common tasks

- Build: `./gradlew build`
- Run client: `./gradlew runClient`
- Run datagen: `./gradlew runDatagen` → outputs `src/main/generated/resources`
- Tests: `./gradlew test`

## External references

- Fabric docs — datagen setup: https://docs.fabricmc.net/develop/data-generation/setup
- Datagen: advancements: https://docs.fabricmc.net/develop/data-generation/advancements
- Datagen: loot tables: https://docs.fabricmc.net/develop/data-generation/loot-tables
- Datagen: recipes: https://docs.fabricmc.net/develop/data-generation/recipes
- Datagen: tags: https://docs.fabricmc.net/develop/data-generation/tags
- Datagen: translations: https://docs.fabricmc.net/develop/data-generation/translations
- Datagen: block models: https://docs.fabricmc.net/develop/data-generation/block-models
- Datagen: item models: https://docs.fabricmc.net/develop/data-generation/item-models
- Fabric API Javadoc: https://maven.fabricmc.net/docs/fabric-api-0.141.1+1.21.11/
- Yarn mappings Javadoc: https://maven.fabricmc.net/docs/yarn-1.21.11+build.4/

## Source references (exact versions)

Reference only; never edit directly.

- Fabric API: `../../.sources/fabric-api-1.21.11/`
- Fabric Loader: `../../.sources/fabric-loader-0.18.4/`
- Fabric Loom: `../../.sources/fabric-loom-dev-1.14/`
- Yarn: `../../.sources/yarn-1.21.11/`
- Minecraft assets: `../../.sources/minecraft-assets-1.21.11/`
- Minecraft: `../../.sources/minecraft-1.21.11/`
