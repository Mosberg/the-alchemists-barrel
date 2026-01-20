package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public final class TABLootTableProvider extends SimpleFabricLootTableProvider {
    public TABLootTableProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter) {
        MaterialRegistry.load();

        for (MaterialDef def : MaterialRegistry.all()) {
            if (!"block".equals(def.type))
                continue;

            var block = ModContent.getBlock(def.id);
            if (block == null)
                continue;

            RegistryKey<LootTable> tableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                    Identifier.of(TheAlchemistsBarrel.MOD_ID, "blocks/" + def.id));

            LootTable.Builder table = LootTable.builder().pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(block)));

            exporter.accept(tableKey, table);
        }
    }

    @Override
    public String getName() {
        return "TABLootTableProvider";
    }
}
