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

public class ModContent {
    public static final Map<String, Item> ITEMS = new HashMap<>();
    public static final Map<String, Block> BLOCKS = new HashMap<>();

    public static void registerAll() {
        MaterialRegistry.load();
        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            Identifier id = Identifier.of(MaterialRegistry.MOD_ID, def.id);
            switch (def.type) {
                case "item" -> {
                    Item.Settings itemSettings = new Item.Settings().maxCount(def.max_stack_size);
                    Item item = new Item(itemSettings);
                    Registry.register(Registries.ITEM, id, item);
                    ITEMS.put(def.id, item);
                }
                case "block" -> {
                    Block.Settings blockSettings = Block.Settings.create()
                            .strength((float) def.hardness, (float) def.resistance);
                    Block block = new Block(blockSettings);
                    Registry.register(Registries.BLOCK, id, block);
                    BLOCKS.put(def.id, block);
                    if (def.has_item) {
                        Item blockItem = new BlockItem(block, new Item.Settings());
                        Registry.register(Registries.ITEM, id, blockItem);
                        ITEMS.put(def.id, blockItem);
                    }
                }
            }
        }
    }
}
