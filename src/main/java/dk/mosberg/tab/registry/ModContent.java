package dk.mosberg.tab.registry;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.tab.block.FacingMaterialBlock;
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
            Identifier id = Identifier.of(MaterialRegistry.MOD_ID, def.id);

            if ("item".equals(def.type)) {
                Item item = new Item(new Item.Settings());
                Registry.register(Registries.ITEM, id, item);
                ITEMS.put(def.id, item);
                continue;
            }

            if ("block".equals(def.type)) {
                Block block = new FacingMaterialBlock(Block.Settings.create().strength(1.5f));
                Registry.register(Registries.BLOCK, id, block);
                BLOCKS.put(def.id, block);

                // Register BlockItem for the same id (matches your
                // `assets/tab/items/..._block.json` usage). [file:46]
                Item blockItem = new BlockItem(block, new Item.Settings());
                Registry.register(Registries.ITEM, id, blockItem);
                ITEMS.put(def.id, blockItem);
            }
        }
    }
}
