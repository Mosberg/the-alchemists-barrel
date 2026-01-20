package dk.mosberg.tab.registry;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import dk.mosberg.tab.TheAlchemistsBarrel;
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

        for (MaterialDef def : MaterialRegistry.all()) {
            Identifier id = Identifier.of(TheAlchemistsBarrel.MOD_ID, def.id);

            if ("item".equals(def.type)) {
                registerItem(def.id, id, new Item(new Item.Settings()));
                continue;
            }

            if ("block".equals(def.type)) {
                Block block = new FacingMaterialBlock(Block.Settings.create().strength(1.5f));
                Registry.register(Registries.BLOCK, id, block);
                BLOCKS.put(def.id, block);

                if (def.has_item) {
                    registerItem(def.id, id, new BlockItem(block, new Item.Settings()));
                }

                continue;
            }

            TheAlchemistsBarrel.LOGGER.warn("Unknown material type '{}' for id '{}'", def.type,
                    def.id);
        }
    }

    private static void registerItem(String defId, Identifier id, Item item) {
        Registry.register(Registries.ITEM, id, item);
        ITEMS.put(defId, item);
    }

    public static @Nullable Item getItem(String id) {
        return ITEMS.get(id);
    }

    public static @Nullable Block getBlock(String id) {
        return BLOCKS.get(id);
    }
}
