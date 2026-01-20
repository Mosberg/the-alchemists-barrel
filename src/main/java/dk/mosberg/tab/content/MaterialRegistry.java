package dk.mosberg.tab.content;

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

public class MaterialRegistry {
    public static final String MOD_ID = "tab";
    public static final List<MaterialDef> MATERIALS = new ArrayList<>();
    public static final Map<String, MaterialDef> BY_ID = new HashMap<>();

    public static void load() {
        var resource = MaterialRegistry.class.getClassLoader()
                .getResourceAsStream("data/tab/schemas/materials.json");
        if (resource == null) {
            throw new IllegalStateException("materials.json not found");
        }
        JsonObject root =
                JsonParser.parseReader(new InputStreamReader(resource, StandardCharsets.UTF_8))
                        .getAsJsonObject();
        JsonArray arr = root.getAsJsonArray("materials");
        MATERIALS.clear();
        BY_ID.clear();
        for (JsonElement el : arr) {
            @SuppressWarnings("null")
            MaterialDef def = new Gson().fromJson(el, MaterialDef.class);
            MATERIALS.add(def);
            BY_ID.put(def.id, def);
        }
        MATERIALS.sort(Comparator.comparing(m -> m.id));
    }
}
