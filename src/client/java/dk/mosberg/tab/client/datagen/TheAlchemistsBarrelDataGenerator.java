package dk.mosberg.tab.client.datagen;

import dk.mosberg.tab.client.datagen.provider.TABAdvancementProvider;
import dk.mosberg.tab.client.datagen.provider.TABAssetsProvider;
import dk.mosberg.tab.client.datagen.provider.TABLanguageProvider;
import dk.mosberg.tab.client.datagen.provider.TABLootTableProvider;
import dk.mosberg.tab.client.datagen.provider.TABTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class TheAlchemistsBarrelDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		var pack = gen.createPack();

		// Custom provider that writes item-definitions + models + blockstates + block models.
		pack.addProvider(TABAssetsProvider::new);

		// Gameplay data
		pack.addProvider(TABAdvancementProvider::new);
		pack.addProvider(TABLootTableProvider::new);
		pack.addProvider(TABTagProvider::new);

		// Translations
		pack.addProvider(TABLanguageProvider::new);
	}
}
