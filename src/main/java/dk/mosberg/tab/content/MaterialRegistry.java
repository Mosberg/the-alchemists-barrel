package dk.mosberg.tab.content;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.mosberg.tab.TheAlchemistsBarrel;

public final class MaterialRegistry {
    private static final String MATERIALS_PATH =
            "data/" + TheAlchemistsBarrel.MOD_ID + "/schemas/materials.json";

    private static final List<MaterialDef> MATERIALS = new ArrayList<>();
    private static final Map<String, MaterialDef> BY_ID = new HashMap<>();
    private static boolean loaded;

    private MaterialRegistry() {}

    public static synchronized void load() {
        if (loaded) {
            return;
        }

        try (InputStream in =
                MaterialRegistry.class.getClassLoader().getResourceAsStream(MATERIALS_PATH)) {
            if (in == null) {
                throw new IllegalStateException("Missing resource: " + MATERIALS_PATH);
            }

            JsonObject root =
                    JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                            .getAsJsonObject();
            JsonArray arr = root.getAsJsonArray("materials");

            MATERIALS.clear();
            BY_ID.clear();

            Gson gson = new Gson();
            for (int i = 0; i < arr.size(); i++) {
                MaterialDef def = gson.fromJson(arr.get(i), MaterialDef.class);
                if (def == null || def.id == null || def.id.isBlank() || def.type == null
                        || def.type.isBlank()) {
                    continue;
                }

                // Normalize null lists to empty lists to simplify provider logic.
                if (def.cultures == null)
                    def.cultures = List.of();
                if (def.tags == null)
                    def.tags = List.of();

                // Sanity: items should always be treated as having an item representation.
                if ("item".equals(def.type)) {
                    def.has_item = true;
                }

                MATERIALS.add(def);
                BY_ID.put(def.id, def);
            }

            MATERIALS.sort(Comparator.comparing(d -> d.id));
            loaded = true;
        } catch (RuntimeException ex) {
            // Keep loaded=false so callers can retry after fixing data during dev.
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed reading: " + MATERIALS_PATH, ex);
        }
    }

    public static List<MaterialDef> all() {
        load();
        return Collections.unmodifiableList(MATERIALS);
    }

    public static @Nullable MaterialDef byId(String id) {
        load();
        return BY_ID.get(id);
    }
}
