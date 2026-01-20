# Copilot Instructions — The Alchemist’s Barrel (Fabric 1.21.11 · Yarn · Split Sources)

These instructions apply to Copilot Chat and agent-style changes in this repository (VS Code reads `.github/copilot-instructions.md`).
Treat them as repository conventions and guardrails.

## Prime directives

- Prefer **data-driven** content (JSON + datagen) over hardcoded definitions when feasible.
- Keep strict **split-source boundaries**:
  - `src/main/java/**` must not reference client-only code.
  - `src/client/java/**` is for rendering, client init, client events, and datagen.
- Use **Yarn** names (1.21.11+build.4) and Fabric API idioms.
- Keep changes **minimal and explicit**: smallest public surface area, smallest diff, clearest behavior.
- If requirements are ambiguous, ask 1–3 targeted questions before implementing.

## Project map

- Mod id / namespace: `tab`
- Java packages:
  - Main/common: `src/main/java/dk/mosberg/tab/`
  - Client-only: `src/client/java/dk/mosberg/tab/client/`
  - Datagen: `src/client/java/dk/mosberg/tab/client/datagen/`
- Resources:
  - Runtime assets: `src/main/resources/assets/tab/**`
  - Runtime data: `src/main/resources/data/tab/**`
  - Generated outputs: `src/main/generated/resources/**` (never edit by hand)

Key code locations (if present):

- `TheAlchemistsBarrel.java` — mod initializer / entrypoint
- `ModContent.java` — canonical registration hub (items/blocks/etc.)
- `MaterialDef.java`, `MaterialRegistry.java` — material model + load/parse/registry

## Preferred workflows

Use the existing Gradle tasks (avoid inventing new workflows unless needed):

- Build: `./gradlew build`
- Run client: `./gradlew runClient`
- Run datagen: `./gradlew runDatagen` → outputs `src/main/generated/resources`
- Tests: `./gradlew test`

If a change affects resources, also update/add datagen providers and re-run datagen.

## Feature implementation checklist

When implementing features/content, follow this sequence:

1. Decide: JSON vs code.
   - Recipes, loot tables, tags, language, models → JSON via datagen.
   - Behavior/state/machines/entities → code, with data-driven definitions where practical.
2. Registration.
   - Register in one canonical place (usually `ModContent.java`).
   - Use `tab:<path>` identifiers and ensure IDs match file paths.
3. Resources.
   - Ensure assets and data exist for new entries (models, textures, lang keys, tags, recipes/loot as needed).
4. Networking/sync.
   - If server state must be visible on client, use explicit sync packets; avoid client polling.
5. Performance.
   - Avoid per-tick allocations and repeated registry scans; cache safely.

## Coding standards (review expectations)

- Java 21; prefer clarity over cleverness.
- Prefer composition over inheritance.
- Keep methods small; extract private helpers instead of long procedures.
- Use Yarn-aligned naming: don’t rename vanilla concepts unnecessarily.
- Null-safety: avoid returning null; if unavoidable, document and annotate consistently.
- Logging:
  - Use one mod logger (e.g., `LoggerFactory.getLogger("tab")`).
  - No tick spam; guard debug logs behind config and rate-limit where relevant.

## Datagen rules (critical)

- Providers live under `src/client/java/dk/mosberg/tab/client/datagen/provider/`.
- Never hand-edit `src/main/generated/resources/**`.
- When adding a new generated content type, also add:
  - A provider class.
  - Wiring in the datagen entrypoint/bootstrap.
  - Minimal validation when practical (ID uniqueness, required keys, schema checks).

## Config (design contract)

When adding config/options:

- Prefer a single versioned config root with defaults and comments.
- Config must be:
  - Validatable (ranges, enums, required fields).
  - Backward compatible when feasible (migrate on load).
  - Reloadable only when safe; document restart requirements.

If the repository uses a config library, use it consistently; otherwise propose one with tradeoffs before implementing.

## Hard constraints

- Don’t edit `fabric.mod.json` if Gradle templates it.
- Don’t commit build artifacts (`build/`, `out/`, `run/`).
- Don’t reference client-only classes from main/common code.
- Avoid reflection for routine logic unless required by an integration.

## Where to look next

Before large changes, inspect:

- `build.gradle` (Loom config, resource processing, datagen wiring)
- `gradle.properties` (metadata + version pins)
- Existing providers in `datagen/provider/` (patterns and style)

- Remote index: `./instructions/remote-index.instructions.md`
- Datagen rules: `./instructions/datagen.instructions.md`
- Java rules: `./instructions/java.instructions.md`
- Resources rules: `./instructions/resources.instructions.md`

## Source references (exact versions)

These are reference-only; do not edit.

- Fabric API: `../.sources/fabric-api-1.21.11/`
- Fabric Loader: `../.sources/fabric-loader-0.18.4/`
- Fabric Loom: `../.sources/fabric-loom-dev-1.14/`
- Yarn: `../.sources/yarn-1.21.11/`
- Minecraft assets: `../.sources/minecraft-assets-1.21.11/`
