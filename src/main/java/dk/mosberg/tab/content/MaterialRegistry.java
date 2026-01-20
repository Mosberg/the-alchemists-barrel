package dk.mosberg.tab.content;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class MaterialRegistry {

    public static final String MOD_ID = "tab";

    public static final List<MaterialDef> MATERIALS = new ArrayList<>();
    public static final Map<String, MaterialDef> BY_ID = new HashMap<>();

    private static boolean loaded;

    private MaterialRegistry() {}

    public static synchronized void load() {
        if (loaded) {
            return;
        }

        final InputStream resource = MaterialRegistry.class.getClassLoader()
                .getResourceAsStream("data/tab/schemas/materials.json");

        if (resource == null) {
            throw new IllegalStateException(
                    "data/tab/schemas/materials.json not found on classpath");
        }

        final JsonObject root =
                JsonParser.parseReader(new InputStreamReader(resource, StandardCharsets.UTF_8))
                        .getAsJsonObject();

        final JsonArray arr = root.getAsJsonArray("materials");
        MATERIALS.clear();
        BY_ID.clear();

        final Gson gson = new Gson();
        for (JsonElement el : arr) {
            final MaterialDef def = gson.fromJson(el, MaterialDef.class);
            if (def == null || def.id == null || def.id.isBlank()) {
                continue;
            }
            MATERIALS.add(def);
            BY_ID.put(def.id, def);
        }

        MATERIALS.sort(Comparator.comparing(d -> d.id));
        loaded = true;
    }
}
