// Standalone Groovy script to generate materials.json for The Alchemist's Barrel
// Usage: groovy GenerateMaterialsJson.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * Generates (or updates) src/main/resources/data/tab/schemas/materials.json
 * by scanning texture folders under assets/tab/textures/{item,block} recursively.
 *
 * IDs include subfolders to prevent collisions, e.g. "barrels/oak_iron_barrel".
 */
@CompileStatic
final class GenerateMaterialsJson {

    private static final String MOD_ID = 'tab'
    private static final int SCHEMA_VERSION = 1
    private static final String PNG_EXT = '.png'

    private static final String KEY_SCHEMA = 'schema'
    private static final String KEY_MATERIALS = 'materials'
    private static final String KEY_ID = 'id'
    private static final String KEY_TYPE = 'type'
    private static final String KEY_PATH = 'path'

    static void main(final String[] args) {
        final Path projectRoot = Paths.get('.').toAbsolutePath().normalize()

        final Path materialsPath = projectRoot.resolve('src/main/resources/data/tab/schemas/materials.json')
        final Path itemDir = projectRoot.resolve('src/main/resources/assets/tab/textures/item')
        final Path blockDir = projectRoot.resolve('src/main/resources/assets/tab/textures/block')

        // Use the concrete type to avoid map-to-interface coercion warnings.
        final LinkedHashMap<String, LinkedHashMap<String, String>> materialsById = [:]

        materialsById.putAll(readExisting(materialsPath))

        for (final LinkedHashMap<String, String> m : scanDir(itemDir, 'item')) {
            materialsById.put(m.get(KEY_ID), m)
        }
        for (final LinkedHashMap<String, String> m : scanDir(blockDir, 'block')) {
            materialsById.put(m.get(KEY_ID), m)
        }

        final ArrayList<LinkedHashMap<String, String>> materials = []
        materials.addAll(materialsById.values())
        materials.sort { final LinkedHashMap<String, String> a, final LinkedHashMap<String, String> b ->
            a.get(KEY_ID) <=> b.get(KEY_ID)
        }

        final LinkedHashMap<String, Object> result = [
            (KEY_SCHEMA): SCHEMA_VERSION,
            (KEY_MATERIALS): materials,
        ]

        Files.createDirectories(materialsPath.parent)

        final String json = JsonOutput.prettyPrint(JsonOutput.toJson(result)) + System.lineSeparator()
        Files.writeString(materialsPath, json, StandardCharsets.UTF_8)

        println("Generated ${materials.size()} materials into ${projectRoot.relativize(materialsPath)}")
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static LinkedHashMap<String, LinkedHashMap<String, String>> readExisting(final Path materialsPath) {
        final LinkedHashMap<String, LinkedHashMap<String, String>> byId = [:]
        if (!Files.exists(materialsPath)) {
            return byId
        }

        try {
            final String text = Files.readString(materialsPath, StandardCharsets.UTF_8)
            final Object parsed = new JsonSlurper().parseText(text)

            final Map parsedMap = (Map) parsed
            final List mats = (List) parsedMap.get(KEY_MATERIALS)

            for (final Object o : mats) {
                final Map m = (Map) o
                final String id = (String) m.get(KEY_ID)

                if (id == null || id.blank) {
                    continue
                }

                final LinkedHashMap<String, String> entry = [
                    (KEY_ID): id,
                ]

                final Object typeObj = m.get(KEY_TYPE)
                final Object pathObj = m.get(KEY_PATH)

                if (typeObj != null) {
                    entry.put(KEY_TYPE, String.valueOf(typeObj))
                }
                if (pathObj != null) {
                    entry.put(KEY_PATH, String.valueOf(pathObj))
                }

                byId.put(id, entry)
            }
        } catch (final IOException ignored) {
            // Treat unreadable file as empty.
        } catch (final RuntimeException ignored) {
            // Covers JSON parse errors / unexpected structure.
        }

        return byId
    }

    private static ArrayList<LinkedHashMap<String, String>> scanDir(final Path dir, final String type) {
        if (!Files.isDirectory(dir)) {
            return []
        }

        final ArrayList<LinkedHashMap<String, String>> out = []

        try (final Stream<Path> s = Files.walk(dir)) {
            s.filter { final Path p -> Files.isRegularFile(p) }
             .filter { final Path p -> (p.fileName as String).endsWith(PNG_EXT) }
             .forEach { final Path p ->
                 final String rel = dir.relativize(p).toString().replace('\\', '/')

                 // ID includes subfolders, without extension (e.g. barrels/oak_iron_barrel).
                 final String id = rel.substring(0, rel.length() - PNG_EXT.length())

                 // Stable resource path
                 final String assetPath = "assets/${MOD_ID}/textures/${type}/${rel}"

                 out.add([
                     (KEY_ID): id,
                     (KEY_TYPE): type,
                     (KEY_PATH): assetPath,
                 ])
             }
        }

        return out
    }

}
