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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class MaterialRegistry {

    public static final String MOD_ID = "tab";

    public static final List<MaterialDef> MATERIALS = new ArrayList<>();
    public static final Map<String, MaterialDef> BY_ID = new HashMap<>();

    private static boolean loaded;

    private MaterialRegistry() {}

    public static synchronized void load() {
        if (loaded)
            return;

        InputStream in = MaterialRegistry.class.getClassLoader()
                .getResourceAsStream("data/tab/schemas/materials.json");

        if (in == null) {
            throw new IllegalStateException("Missing resource: data/tab/schemas/materials.json");
        }

        JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                .getAsJsonObject();

        JsonArray arr = root.getAsJsonArray("materials");

        MATERIALS.clear();
        BY_ID.clear();

        Gson gson = new Gson();
        for (int i = 0; i < arr.size(); i++) {
            MaterialDef def = gson.fromJson(arr.get(i), MaterialDef.class);
            if (def == null || def.id == null || def.id.isBlank() || def.type == null)
                continue;
            MATERIALS.add(def);
            BY_ID.put(def.id, def);
        }

        MATERIALS.sort(Comparator.comparing(d -> d.id));
        loaded = true;
    }
}
