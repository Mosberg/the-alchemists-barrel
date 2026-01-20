// Standalone Groovy script to generate materials.json for The Alchemist's Barrel
// Usage: groovy GenerateMaterialsJson.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import groovy.transform.CompileStatic

@CompileStatic
class GenerateMaterialsJson {

    // --- Constants ---
    public static final String modId = 'tab'
    public static final int schemaVersion = 1
    public static final String pngExt = '.png'
    public static final String keySchema = 'schema'
    public static final String keyMaterials = 'materials'
    public static final String keyId = 'id'
    public static final String keyType = 'type'
    public static final String keyPath = 'path'

    static void main(String[] args) {
        Path projectRoot = Paths.get('.').toAbsolutePath().normalize()
        Path materialsPath = projectRoot.resolve("src/main/resources/data/${modId}/schemas/materials.json")
        Path itemDir = projectRoot.resolve("src/main/resources/assets/${modId}/textures/item")
        Path blockDir = projectRoot.resolve("src/main/resources/assets/${modId}/textures/block")

        Map<String, Map<String, Object>> byId = new LinkedHashMap<>()
        byId.putAll(readExisting(materialsPath))

        scanDir(itemDir, 'item').each { Map<String, Object> m ->
            byId[m[keyId] as String] = m
        }
        scanDir(blockDir, 'block').each { Map<String, Object> m ->
            byId[m[keyId] as String] = m
        }

        List<Map<String, Object>> materials = new ArrayList<>(byId.values())
        materials.sort { a, b -> (a[keyId] as String) <=> (b[keyId] as String) }

        Map<String, Object> result = [
            (keySchema): schemaVersion,
            (keyMaterials): materials,
        ]

        Files.createDirectories(materialsPath.parent)
        String json = JsonOutput.prettyPrint(JsonOutput.toJson(result)) + System.lineSeparator()
        Files.writeString(materialsPath, json, StandardCharsets.UTF_8)

        println("Generated ${materials.size()} materials into ${projectRoot.relativize(materialsPath)}")
    }

    static Map<String, Map<String, Object>> readExisting(Path materialsPath) {
        Map<String, Map<String, Object>> byId = new LinkedHashMap<>()
        if (!Files.exists(materialsPath)) {
            return byId
        }
        try {
            String text = Files.readString(materialsPath, StandardCharsets.UTF_8)
            Object parsed = new JsonSlurper().parseText(text)
            if (!(parsed instanceof Map)) {
                return byId
            }
            Object matsObj = (parsed as Map)[keyMaterials]
            if (!(matsObj instanceof List)) {
                return byId
            }
            for (Object o : (List)matsObj) {
                if (!(o instanceof Map)) {
                    continue
                }
                Map m = (Map)o
                Object idObj = m[keyId]
                String id = idObj instanceof String ? (String)idObj : null
                if (id == null || id.isBlank()) {
                    continue
                }
                Map<String, Object> entry = new LinkedHashMap<>()
                entry[keyId] = id
                if (m[keyType] != null) {
                    entry[keyType] = String.valueOf(m[keyType])
                }
                if (m[keyPath] != null) {
                    entry[keyPath] = String.valueOf(m[keyPath])
                }
                byId[id] = entry
            }
        } catch (Exception ignored) {
            // Treat unreadable/bad JSON as empty for convenience during dev.
        }
        return byId
    }

    static List<Map<String, Object>> scanDir(Path dir, String type) {
        if (!Files.isDirectory(dir)) {
            return Collections.emptyList()
        }
        List<Map<String, Object>> out = new ArrayList<>()
        Files.walk(dir).filter { Path p -> Files.isRegularFile(p) }
            .filter { Path p -> p.fileName.toString().endsWith(pngExt) }
            .forEach { Path p ->
                String rel = dir.relativize(p).toString().replace('\\', '/')
                String id = rel.substring(0, rel.length() - pngExt.length())
                String assetPath = "assets/${modId}/textures/${type}/${rel}"
                out.add([
                    (keyId): id,
                    (keyType): type,
                    (keyPath): assetPath,
                ])
            }
        return out
    }
}

GenerateMaterialsJson.main(args)
