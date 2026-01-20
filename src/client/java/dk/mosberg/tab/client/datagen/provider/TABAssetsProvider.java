package dk.mosberg.tab.client.datagen.provider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.google.gson.JsonObject;
import dk.mosberg.tab.TheAlchemistsBarrel;
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

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (MaterialDef def : MaterialRegistry.all()) {
            if ("item".equals(def.type)) {
                futures.add(writeItemDefinition(writer, def, false));
                futures.add(writeItemModel(writer, def));
            } else if ("block".equals(def.type)) {
                futures.add(writeBlockState(writer, def));
                futures.add(writeBlockModel(writer, def));

                if (def.has_item) {
                    futures.add(writeItemDefinition(writer, def, true));
                }
            }
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "TABAssetsProvider";
    }

    private CompletableFuture<?> writeItemDefinition(DataWriter writer, MaterialDef def,
            boolean isBlock) {
        // assets/<modid>/items/<id>.json -> item model definition (1.21+ style)
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();

        model.addProperty("type", "minecraft:model");
        model.addProperty("model", (isBlock ? TheAlchemistsBarrel.MOD_ID + ":block/"
                : TheAlchemistsBarrel.MOD_ID + ":item/") + def.id);

        root.add("model", model);

        Path path = output.getPath().resolve("assets").resolve(TheAlchemistsBarrel.MOD_ID)
                .resolve("items").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private CompletableFuture<?> writeItemModel(DataWriter writer, MaterialDef def) {
        // assets/<modid>/models/item/<id>.json
        JsonObject root = new JsonObject();
        root.addProperty("parent", "item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", TheAlchemistsBarrel.MOD_ID + ":item/" + def.id);

        String overlayId = flaskOverlayId(def.id);
        if (overlayId != null) {
            textures.addProperty("layer1", TheAlchemistsBarrel.MOD_ID + ":item/" + overlayId);
        }

        root.add("textures", textures);

        Path path = output.getPath().resolve("assets").resolve(TheAlchemistsBarrel.MOD_ID)
                .resolve("models").resolve("item").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private static String flaskOverlayId(String itemId) {
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
        // assets/<modid>/blockstates/<id>.json -> facing variants
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        String model = TheAlchemistsBarrel.MOD_ID + ":block/" + def.id;

        variants.add("facing=north", variant(model, null));
        variants.add("facing=south", variant(model, 180));
        variants.add("facing=west", variant(model, 270));
        variants.add("facing=east", variant(model, 90));

        root.add("variants", variants);

        Path path = output.getPath().resolve("assets").resolve(TheAlchemistsBarrel.MOD_ID)
                .resolve("blockstates").resolve(def.id + ".json");

        return DataProvider.writeToPath(writer, root, path);
    }

    private CompletableFuture<?> writeBlockModel(DataWriter writer, MaterialDef def) {
        // assets/<modid>/models/block/<id>.json
        String parent;
        if (def.id.startsWith("barrels/"))
            parent = TheAlchemistsBarrel.MOD_ID + ":block/barrels/barrel_block";
        else if (def.id.startsWith("kegs/"))
            parent = TheAlchemistsBarrel.MOD_ID + ":block/kegs/keg_block";
        else
            parent = "block/cube_all";

        JsonObject root = new JsonObject();
        root.addProperty("parent", parent);

        JsonObject textures = new JsonObject();
        textures.addProperty("all", TheAlchemistsBarrel.MOD_ID + ":block/" + def.id);
        textures.addProperty("particle", "#all");
        root.add("textures", textures);

        Path path = output.getPath().resolve("assets").resolve(TheAlchemistsBarrel.MOD_ID)
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
