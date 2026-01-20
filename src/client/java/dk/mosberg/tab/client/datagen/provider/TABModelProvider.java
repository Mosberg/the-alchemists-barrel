package dk.mosberg.tab.client.datagen.provider;

import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;

public final class TABModelProvider extends FabricModelProvider {
    public TABModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // Intentionally empty to avoid duplicate files:
        // TABAssetsProvider writes block models + blockstates already.
        MaterialRegistry.load();
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Intentionally empty to avoid duplicate files:
        // TABAssetsProvider writes item definition json + item model json already.
        MaterialRegistry.load();
    }
}
