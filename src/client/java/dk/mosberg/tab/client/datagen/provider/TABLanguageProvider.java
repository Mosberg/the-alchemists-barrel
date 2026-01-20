package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class TABLanguageProvider implements net.minecraft.data.DataProvider {
    public TABLanguageProvider(FabricDataOutput output) {
        // Stub
    }

    @Override
    public CompletableFuture<?> run(net.minecraft.data.DataWriter writer) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "TABLanguageProvider (stub)";
    }
}
