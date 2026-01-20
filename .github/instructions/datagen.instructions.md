---
name: Datagen rules
description: Rules for data generation (providers, IDs, paths, determinism).
applyTo: "src/client/java/**/datagen/**/*.java"
---

## Prime directive

- Never hand-edit files under `src/main/generated/resources/**`.
- If generated JSON needs changes, update/add datagen providers and re-run `./gradlew runDatagen`.

## Determinism & stability

- Output must be deterministic: stable ordering, stable formatting, stable IDs.
- Avoid relying on hash iteration order; sort keys/entries where relevant.
- Don’t read runtime resources to generate outputs unless explicitly required and stable.

## IDs, namespaces, and paths

- All identifiers should use the `tab` namespace unless intentionally targeting vanilla/other mods.
- Validate every generated `Identifier` and ensure it matches file paths.
- Ensure provider outputs land in the correct roots:
  - Assets → `assets/tab/**`
  - Datapack → `data/tab/**`

## Provider expectations

- New registries/content types should ship with:
  - A provider class in `datagen/provider/**`.
  - Wiring in the datagen entrypoint/bootstrap.
  - Minimal validation when feasible (duplicate IDs, missing textures/models/lang keys).

## Validation checklist

- Run `./gradlew runDatagen` and confirm outputs update as expected.
- Ensure no dangling references:
  - Recipes reference existing items/tags.
  - Loot tables reference existing blocks/items.
  - Models reference existing textures.
  - Language keys exist for new content.
