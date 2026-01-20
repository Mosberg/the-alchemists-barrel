package dk.mosberg.tab.client.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import java.util.concurrent.CompletableFuture;

public class TABBlockStateProvider implements net.minecraft.data.DataProvider {
    public TABBlockStateProvider(FabricDataOutput output) {
        // Stub
    }

    @Override
    public CompletableFuture<?> run(net.minecraft.data.DataWriter writer) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "TABBlockStateProvider (stub)";
    }
}
