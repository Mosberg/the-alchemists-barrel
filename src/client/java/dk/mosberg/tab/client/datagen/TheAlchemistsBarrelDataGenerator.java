package dk.mosberg.tab.client.datagen;

import dk.mosberg.tab.client.datagen.provider.TABAdvancementProvider;
import dk.mosberg.tab.client.datagen.provider.TABBlockStateProvider;
import dk.mosberg.tab.client.datagen.provider.TABLanguageProvider;
import dk.mosberg.tab.client.datagen.provider.TABLootTableProvider;
import dk.mosberg.tab.client.datagen.provider.TABModelProvider;
import dk.mosberg.tab.client.datagen.provider.TABRecipeProvider;
import dk.mosberg.tab.client.datagen.provider.TABTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TheAlchemistsBarrelDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(
			@SuppressWarnings("null") FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(TABAdvancementProvider::new);
		pack.addProvider(TABBlockStateProvider::new);
		pack.addProvider(TABLanguageProvider::new);
		pack.addProvider(TABLootTableProvider::new);
		pack.addProvider(TABModelProvider::new);
		pack.addProvider(TABRecipeProvider::new);
		pack.addProvider(TABTagProvider::new);
		// add more providers as needed
	}
}
