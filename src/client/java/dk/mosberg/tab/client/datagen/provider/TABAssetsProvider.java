package dk.mosberg.tab.client.datagen.provider;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import com.google.gson.JsonObject;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

public final class TABAssetsProvider implements DataProvider {

    private final FabricDataOutput output;

    public TABAssetsProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        MaterialRegistry.load();

        CompletableFuture<?> all = CompletableFuture.completedFuture(null);

        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if ("item".equals(def.type)) {
                all = all.thenCompose(v -> writeItemDefinition(writer, def, false))
                        .thenCompose(v -> writeItemModel(writer, def));
            } else if ("block".equals(def.type)) {
                all = all.thenCompose(v -> writeBlockState(writer, def))
                        .thenCompose(v -> writeBlockModel(writer, def))
                        .thenCompose(v -> writeItemDefinition(writer, def, true));
            }
        }

        return all;
    }

    @Override
    public String getName() {
        return "TABAssetsProvider";
    }

    private CompletableFuture<?> writeItemDefinition(DataWriter writer, MaterialDef def,
            boolean isBlock) {
        // assets/tab/items/<id>.json -> { "model": { "type": "minecraft:model", "model":
        // "tab:item/..." } }
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", (isBlock ? "tab:block/" : "tab:item/") + def.id);
        root.add("model", model);

        Path path = output.getPath().resolve("assets").resolve(MaterialRegistry.MOD_ID)
                .resolve("items").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private CompletableFuture<?> writeItemModel(DataWriter writer, MaterialDef def) {
        // assets/tab/models/item/<id>.json
        // - Always write layer0
        // - If this is a flask (large/medium/small), also write layer1 overlay
        JsonObject root = new JsonObject();
        root.addProperty("parent", "item/generated");

        JsonObject textures = new JsonObject();

        // Base texture is always layer0 (not "all")
        textures.addProperty("layer0", "tab:item/" + def.id);

        // Optional: flask fluid overlay as layer1
        String overlayId = flaskOverlayId(def.id);
        if (overlayId != null) {
            textures.addProperty("layer1", "tab:item/" + overlayId);
        }

        root.add("textures", textures);

        Path path = output.getPath().resolve("assets").resolve(MaterialRegistry.MOD_ID)
                .resolve("models").resolve("item").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private static String flaskOverlayId(String itemId) {
        // Expected ids from materials.json:
        // flasks/large/large_oak_glass_flask -> flasks/fluid/large_flask_fluid_overlay
        // flasks/medium/medium_oak_glass_flask -> flasks/fluid/medium_flask_fluid_overlay
        // flasks/small/small_oak_glass_flask -> flasks/fluid/small_flask_fluid_overlay
        if (itemId == null)
            return null;

        if (itemId.startsWith("flasks/large/"))
            return "flasks/fluid/large_flask_fluid_overlay";
        if (itemId.startsWith("flasks/medium/"))
            return "flasks/fluid/medium_flask_fluid_overlay";
        if (itemId.startsWith("flasks/small/"))
            return "flasks/fluid/small_flask_fluid_overlay";

        return null;
    }


    private CompletableFuture<?> writeBlockState(DataWriter writer, MaterialDef def) {
        // assets/tab/blockstates/<id>.json -> facing variants (matches your existing blockstate
        // files). [file:43][file:44]
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        variants.add("normal", variant("tab:block/" + def.id, null));
        variants.add("facing=north", variant("tab:block/" + def.id, null));
        variants.add("facing=south", variant("tab:block/" + def.id, 180));
        variants.add("facing=west", variant("tab:block/" + def.id, 270));
        variants.add("facing=east", variant("tab:block/" + def.id, 90));

        root.add("variants", variants);

        Path path = output.getPath().resolve("assets").resolve(MaterialRegistry.MOD_ID)
                .resolve("blockstates").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private CompletableFuture<?> writeBlockModel(DataWriter writer, MaterialDef def) {
        // assets/tab/models/block/<id>.json -> parent template + texture map (matches your
        // pattern). [file:53][file:55]
        String parent;
        if (def.id.startsWith("barrels/"))
            parent = "tab:block/barrels/barrel_block";
        else if (def.id.startsWith("kegs/"))
            parent = "tab:block/kegs/keg_block";
        else
            parent = "block/cube_all";

        JsonObject root = new JsonObject();
        root.addProperty("parent", parent);

        JsonObject textures = new JsonObject();
        textures.addProperty("all", "tab:block/" + def.id);
        textures.addProperty("particle", "#all");
        root.add("textures", textures);

        Path path = output.getPath().resolve("assets").resolve(MaterialRegistry.MOD_ID)
                .resolve("models").resolve("block").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private static JsonObject variant(String model, Integer y) {
        JsonObject v = new JsonObject();
        v.addProperty("model", model);
        if (y != null)
            v.addProperty("y", y);
        return v;
    }
}
