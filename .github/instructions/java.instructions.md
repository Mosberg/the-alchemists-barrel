---
name: Java (Fabric mod) rules
description: Java-only rules for Minecraft Fabric 1.21.11 modding (Yarn + split sources).
applyTo: "**/*.java"
---

## Java standards

- Use Java 21 features where they improve clarity; avoid cleverness that harms readability.
- Prefer small, focused classes; keep methods short; extract private helpers instead of long procedures.
- Follow Yarn naming and idioms; don’t invent alternate terminology for vanilla concepts (e.g., `BlockState`, `Identifier`).

## Split-source boundaries (hard rule)

- Code in `src/main/java/**` must not reference anything under `src/client/java/**` (renderers, `MinecraftClient`, client events, etc.).
- Client-only hooks must live under `src/client/java/**` and be reached through Fabric client entrypoints or safe indirection (interfaces in main; implementations in client).

## Logging

- Use one mod logger (e.g., `LoggerFactory.getLogger("tab")`).
- Never log in per-tick loops unless behind a debug flag and rate-limited.
- Log actionable messages: what happened, which identifier, and what the user can do next.

## Concurrency & threading

- Assume most gameplay state is not thread-safe.
- Avoid world/entity mutations off-thread; schedule back onto the server thread when needed.
- If caching, prefer immutable data and safe publication; document thread expectations.

## Performance pitfalls

- Avoid allocations in hot paths (ticks, rendering, network handlers).
- Avoid repeated registry scans; cache identifiers/entries when safe.
- Prefer `static final Identifier` constants for frequently used IDs.

## Defensive coding

- Validate external inputs (packets, JSON config, NBT).
- Fail fast with clear errors for invalid IDs, missing required fields, or out-of-range values.
- Prefer explicit nullability: avoid returning null; if unavoidable, document and annotate consistently.

## Tests (when relevant)

- Write JUnit tests for pure logic (parsing, validation, ID normalization, migration, math, rules engines).
- Tests should not require launching Minecraft unless explicitly requested.
