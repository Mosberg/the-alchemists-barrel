---
name: Resource & data conventions
description: Naming and layout rules for assets, datapack JSON, and tags in the tab namespace.
applyTo: "src/main/resources/**/*"
---

## Namespaces & roots

- Mod namespace is `tab`.
- Runtime assets: `src/main/resources/assets/tab/**`
- Runtime datapack: `src/main/resources/data/tab/**`
- Generated resources: `src/main/generated/resources/**` (never edit by hand)

## Naming (strict)

- Use lowercase paths with `/` separators.
- Prefer underscores in filenames (e.g., `alchemy_barrel.json`); avoid spaces.
- IDs and file paths must correspond.
  - `tab:my_item` → `assets/tab/models/item/my_item.json` (+ matching texture path)

## Language keys

- Use consistent, discoverable keys:
  - Items: `item.tab.<name>`
  - Blocks: `block.tab.<name>`
  - Item groups: `itemGroup.tab.<name>`
  - Tooltips: `tooltip.tab.<name>.<purpose>`
- New content must include at least `assets/tab/lang/en_us.json` entries.

## Tags

- Prefer tags for compatibility and balancing rather than hardcoding item lists.
- Tag locations:
  - Items: `data/tab/tags/item/**`
  - Blocks: `data/tab/tags/block/**`
- When integrating with vanilla systems, consider using vanilla namespaces (e.g., `minecraft:`) and document why.

## JSON hygiene

- Keep JSON minimal and readable; avoid unnecessary fields.
- Use consistent formatting (indentation) and always end files with a trailing newline.
- Don’t duplicate generated data in runtime resources unless intentionally overriding (and document the override).

## Don’t do

- Don’t edit `fabric.mod.json` if it is templated by Gradle.
- Don’t commit `run/`, `out/`, or `build/` artifacts.
