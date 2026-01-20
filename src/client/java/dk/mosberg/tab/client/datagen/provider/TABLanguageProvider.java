package dk.mosberg.tab.client.datagen.provider;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.google.gson.JsonObject;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

public final class TABLanguageProvider implements DataProvider {
    private final FabricDataOutput output;

    public TABLanguageProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        MaterialRegistry.load();

        // Stable insertion order for clean diffs.
        Map<String, String> entries = new LinkedHashMap<>();

        for (MaterialDef def : MaterialRegistry.all()) {
            String keyKind = "block".equals(def.type) ? "block" : "item";
            String key =
                    keyKind + "." + TheAlchemistsBarrel.MOD_ID + "." + def.id.replace('/', '.');

            // Simple readable fallback; replace with schema-driven names later if desired.
            String value = toTitle(def.id);

            entries.put(key, value);
        }

        JsonObject root = new JsonObject();
        for (var e : entries.entrySet()) {
            root.addProperty(e.getKey(), e.getValue());
        }

        Path path = output.getPath().resolve("assets").resolve(TheAlchemistsBarrel.MOD_ID)
                .resolve("lang").resolve("en_us.json");

        return DataProvider.writeToPath(writer, root, path);
    }

    @Override
    public String getName() {
        return "TABLanguageProvider";
    }

    private static String toTitle(String id) {
        // barrels/oak_iron_barrel -> Oak Iron Barrel
        String last = id == null ? "" : id.substring(id.lastIndexOf('/') + 1);
        String[] parts = last.split("[_\\-]+");

        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isBlank())
                continue;
            if (sb.length() > 0)
                sb.append(' ');
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
        }
        return sb.length() == 0 ? id : sb.toString();
    }
}
