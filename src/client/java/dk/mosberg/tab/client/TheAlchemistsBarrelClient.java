package dk.mosberg.tab.client;

import dk.mosberg.tab.TheAlchemistsBarrel;
import net.fabricmc.api.ClientModInitializer;

public final class TheAlchemistsBarrelClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TheAlchemistsBarrel.LOGGER.info("The Alchemist's Barrel client initialized.");
	}
}
