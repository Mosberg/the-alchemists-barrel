// Standalone Groovy script to generate materials.json for The Alchemist's Barrel
// Usage: groovy GenerateMaterialsJson.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

final class GenerateMaterialsJson {
    private static final String MOD_ID = 'tab'
    private static final int SCHEMA_VERSION = 1
    private static final String PNG_EXT = '.png'

    private static final String KEY_SCHEMA = 'schema'
    private static final String KEY_MATERIALS = 'materials'
    private static final String KEY_ID = 'id'
    private static final String KEY_TYPE = 'type'
    private static final String KEY_PATH = 'path'

    static void main(String[] args) {
        final Path projectRoot = Paths.get('.').toAbsolutePath().normalize()
        final Path materialsPath = projectRoot.resolve("src/main/resources/data/${MOD_ID}/schemas/materials.json")

        final Path itemDir = projectRoot.resolve("src/main/resources/assets/${MOD_ID}/textures/item")
        final Path blockDir = projectRoot.resolve("src/main/resources/assets/${MOD_ID}/textures/block")

        final LinkedHashMap<String, Map<String, Object>> byId = new LinkedHashMap<>()
        byId.putAll(readExisting(materialsPath))

        scanDir(itemDir, 'item').each { Map<String, Object> m ->
            byId.put((String) m.get(KEY_ID), m)
        }
        scanDir(blockDir, 'block').each { Map<String, Object> m ->
            byId.put((String) m.get(KEY_ID), m)
        }

        final List<Map<String, Object>> materials = new ArrayList<>(byId.values())
        materials.sort { a, b -> ((String) a.get(KEY_ID)) <=> ((String) b.get(KEY_ID)) }

        final Map<String, Object> result = [
            (KEY_SCHEMA): SCHEMA_VERSION,
            (KEY_MATERIALS): materials,
        ]

        Files.createDirectories(materialsPath.parent)
        final String json = JsonOutput.prettyPrint(JsonOutput.toJson(result)) + System.lineSeparator()
        Files.writeString(materialsPath, json, StandardCharsets.UTF_8)

        println("Generated ${materials.size()} materials into ${projectRoot.relativize(materialsPath)}")
    }

    private static Map<String, Map<String, Object>> readExisting(Path materialsPath) {
        final LinkedHashMap<String, Map<String, Object>> byId = new LinkedHashMap<>()
        if (!Files.exists(materialsPath)) {
            return byId
        }

        try {
            final String text = Files.readString(materialsPath, StandardCharsets.UTF_8)
            final Object parsed = new JsonSlurper().parseText(text)
            if (!(parsed instanceof Map)) return byId

            final Map parsedMap = (Map) parsed
            final Object matsObj = parsedMap.get(KEY_MATERIALS)
            if (!(matsObj instanceof List)) return byId

            for (Object o : (List) matsObj) {
                if (!(o instanceof Map)) continue
                final Map m = (Map) o

                final String id = (String) m.get(KEY_ID)
                if (id == null || id.isBlank()) continue

                final LinkedHashMap<String, Object> entry = new LinkedHashMap<>()
                entry.put(KEY_ID, id)

                if (m.get(KEY_TYPE) != null) entry.put(KEY_TYPE, String.valueOf(m.get(KEY_TYPE)))
                if (m.get(KEY_PATH) != null) entry.put(KEY_PATH, String.valueOf(m.get(KEY_PATH)))

                byId.put(id, entry)
            }
        } catch (Exception ignored) {
            // Treat unreadable/bad JSON as empty for convenience during dev.
        }

        return byId
    }

    private static List<Map<String, Object>> scanDir(Path dir, String type) {
        if (!Files.isDirectory(dir)) return []

        final List<Map<String, Object>> out = []

        try (Stream<Path> s = Files.walk(dir)) {
            s.filter { Path p -> Files.isRegularFile(p) }
             .filter { Path p -> p.fileName.toString().endsWith(PNG_EXT) }
             .forEach { Path p ->
                 final String rel = dir.relativize(p).toString().replace('\\', '/')
                 final String id = rel.substring(0, rel.length() - PNG_EXT.length())
                 final String assetPath = "assets/${MOD_ID}/textures/${type}/${rel}"

                 out.add([
                     (KEY_ID): id,
                     (KEY_TYPE): type,
                     (KEY_PATH): assetPath,
                 ] as Map<String, Object>)
             }
        }

        return out
    }
}

GenerateMaterialsJson.main(args)
