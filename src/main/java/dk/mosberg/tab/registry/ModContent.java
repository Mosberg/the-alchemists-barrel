package dk.mosberg.tab.registry;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModContent {

    public static final Map<String, Item> ITEMS = new HashMap<>();
    public static final Map<String, Block> BLOCKS = new HashMap<>();

    private ModContent() {}

    public static void registerAll() {
        MaterialRegistry.load();

        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if (def == null || def.id == null || def.id.isBlank() || def.type == null) {
                continue;
            }

            switch (def.type) {
                case "item" -> registerItem(def);
                case "block" -> registerBlock(def);
                default -> {
                    // ignore unknown types
                }
            }
        }
    }

    public static Item itemFor(MaterialDef def) {
        final String itemId = itemIdFor(def);
        return itemId == null ? null : ITEMS.get(itemId);
    }

    public static String itemIdFor(MaterialDef def) {
        if (def == null)
            return null;

        if ("item".equals(def.type)) {
            return def.id;
        }

        if ("block".equals(def.type)) {
            if (!def.has_item) {
                return null;
            }
            if (def.item_id != null && !def.item_id.isBlank()) {
                return def.item_id;
            }
            // Heuristic: if the block id ends with "_block", strip it for the item id.
            if (def.id.endsWith("_block")) {
                return def.id.substring(0, def.id.length() - "_block".length());
            }
            return def.id;
        }

        return null;
    }

    private static void registerItem(MaterialDef def) {
        final Identifier id = Identifier.of(MaterialRegistry.MOD_ID, def.id);

        final Item.Settings settings =
                new Item.Settings().maxCount(Math.max(1, def.max_stack_size));

        final Item item = new Item(settings);
        Registry.register(Registries.ITEM, id, item);
        ITEMS.put(def.id, item);
    }

    private static void registerBlock(MaterialDef def) {
        final Identifier blockId = Identifier.of(MaterialRegistry.MOD_ID, def.id);

        final Block.Settings blockSettings =
                Block.Settings.create().strength((float) def.hardness, (float) def.resistance);

        final Block block = new Block(blockSettings);
        Registry.register(Registries.BLOCK, blockId, block);
        BLOCKS.put(def.id, block);

        final String itemIdPath = itemIdFor(def);
        if (itemIdPath == null) {
            return;
        }

        // Register the BlockItem under itemIdPath (which may differ from the block id).
        final Identifier itemId = Identifier.of(MaterialRegistry.MOD_ID, itemIdPath);
        final Item blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, itemId, blockItem);
        ITEMS.put(itemIdPath, blockItem);
    }
}
