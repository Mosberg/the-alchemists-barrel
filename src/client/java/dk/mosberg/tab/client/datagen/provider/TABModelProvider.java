package dk.mosberg.tab.client.datagen.provider;

import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;


public class TABModelProvider extends FabricModelProvider {
    public TABModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(
            @SuppressWarnings("null") BlockStateModelGenerator blockStateModelGenerator) {
        MaterialRegistry.load();
        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if (!"block".equals(def.type))
                continue;
            var block = dk.mosberg.tab.registry.ModContent.BLOCKS.get(def.id);
            if (block == null)
                continue;
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }
    }

    @Override
    public void generateItemModels(
            @SuppressWarnings("null") ItemModelGenerator itemModelGenerator) {
        MaterialRegistry.load();
        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if ("item".equals(def.type) || ("block".equals(def.type) && def.has_item)) {
                var item = dk.mosberg.tab.registry.ModContent.ITEMS.get(def.id);
                if (item == null)
                    continue;
                itemModelGenerator.register(item, Models.GENERATED);
            }
        }
    }
}
