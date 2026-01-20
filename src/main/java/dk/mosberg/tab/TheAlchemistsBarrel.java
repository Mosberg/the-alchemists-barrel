package dk.mosberg.tab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.api.ModInitializer;

public final class TheAlchemistsBarrel implements ModInitializer {

	public static final String MOD_ID = "tab";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModContent.registerAll();
		LOGGER.info("The Alchemist's Barrel initialized.");
	}
}
