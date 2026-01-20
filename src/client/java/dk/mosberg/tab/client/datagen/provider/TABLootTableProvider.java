
package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public class TABLootTableProvider extends SimpleFabricLootTableProvider {
    public TABLootTableProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(
            java.util.function.BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter) {
        MaterialRegistry.load();
        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if (!"block".equals(def.type))
                continue;
            Block block = dk.mosberg.tab.registry.ModContent.BLOCKS.get(def.id);
            if (block == null)
                continue;

            RegistryKey<LootTable> id = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                    Identifier.of(MaterialRegistry.MOD_ID, def.id));
            LootTable.Builder builder = LootTable.builder().pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(block)));
            exporter.accept(id, builder);
        }
    }
}
