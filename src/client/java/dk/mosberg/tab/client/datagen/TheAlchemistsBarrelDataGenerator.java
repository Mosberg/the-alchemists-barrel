package dk.mosberg.tab.client.datagen;

import dk.mosberg.tab.client.datagen.provider.TABAssetsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class TheAlchemistsBarrelDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		gen.createPack().addProvider(TABAssetsProvider::new);
	}
}
