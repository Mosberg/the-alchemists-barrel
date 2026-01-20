// Standalone Groovy script to generate materials.json for The Alchemist's Barrel
// Usage: groovy GenerateMaterialsJson.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream


// --- Constants ---
def MOD_ID = 'tab'
def SCHEMA_VERSION = 1
def PNG_EXT = '.png'
def KEY_SCHEMA = 'schema'
def KEY_MATERIALS = 'materials'
def KEY_ID = 'id'
def KEY_TYPE = 'type'
def KEY_PATH = 'path'

// --- Main logic ---
def projectRoot = Paths.get('.').toAbsolutePath().normalize()
def materialsPath = projectRoot.resolve("src/main/resources/data/${MOD_ID}/schemas/materials.json")
def itemDir = projectRoot.resolve("src/main/resources/assets/${MOD_ID}/textures/item")
def blockDir = projectRoot.resolve("src/main/resources/assets/${MOD_ID}/textures/block")

def byId = [:]
byId.putAll(readExisting(materialsPath))

scanDir(itemDir, 'item').each { m ->
    byId[m[KEY_ID]] = m
}
scanDir(blockDir, 'block').each { m ->
    byId[m[KEY_ID]] = m
}

def materials = byId.values().toList()
materials.sort { a, b -> a[KEY_ID] <=> b[KEY_ID] }

def result = [
    (KEY_SCHEMA): SCHEMA_VERSION,
    (KEY_MATERIALS): materials,
]

Files.createDirectories(materialsPath.parent)
def json = JsonOutput.prettyPrint(JsonOutput.toJson(result)) + System.lineSeparator()
Files.writeString(materialsPath, json, StandardCharsets.UTF_8)

println("Generated ${materials.size()} materials into ${projectRoot.relativize(materialsPath)}")

// --- Functions ---
def readExisting(materialsPath) {
    def byId = [:]
    if (!Files.exists(materialsPath)) {
        return byId
    }
    try {
        def text = Files.readString(materialsPath, StandardCharsets.UTF_8)
        def parsed = new JsonSlurper().parseText(text)
        if (!(parsed instanceof Map)) {
            return byId
        }
        def matsObj = parsed[KEY_MATERIALS]
        if (!(matsObj instanceof List)) {
            return byId
        }
        for (o in matsObj) {
            if (!(o instanceof Map)) {
                continue
            }
            def m = o
            def id = m[KEY_ID]
            if (id == null || (id instanceof String && id.isBlank())) {
                continue
            }
            def entry = [:]
            entry[KEY_ID] = id
            if (m[KEY_TYPE] != null) {
                entry[KEY_TYPE] = String.valueOf(m[KEY_TYPE])
            }
            if (m[KEY_PATH] != null) {
                entry[KEY_PATH] = String.valueOf(m[KEY_PATH])
            }
            byId[id] = entry
        }
    } catch (ignored) {
        // Treat unreadable/bad JSON as empty for convenience during dev.
    }
    return byId
}

def scanDir(dir, type) {
    if (!Files.isDirectory(dir)) {
        return []
    }
    def out = []
    Files.walk(dir).filter { p -> Files.isRegularFile(p) }
        .filter { p -> p.fileName.toString().endsWith(PNG_EXT) }
        .forEach { p ->
            def rel = dir.relativize(p).toString().replace('\\', '/')
            def id = rel.substring(0, rel.length() - PNG_EXT.length())
            def assetPath = "assets/${MOD_ID}/textures/${type}/${rel}"
            out.add([
                (KEY_ID): id,
                (KEY_TYPE): type,
                (KEY_PATH): assetPath,
            ])
        }
    return out
}
