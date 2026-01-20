package dk.mosberg.tab.client.datagen.provider;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;



// ...existing code...

public final class TABTagProvider extends FabricTagProvider<Item> {

    public TABTagProvider(FabricDataOutput output,
            java.util.concurrent.CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, net.minecraft.registry.Registries.ITEM.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        MaterialRegistry.load();

        Map<String, TagKey<Item>> rarityTags = new HashMap<>();
        Map<String, TagKey<Item>> cultureTags = new HashMap<>();

        for (MaterialDef def : MaterialRegistry.all()) {
            if (!def.has_item)
                continue;

            RegistryKey<Item> itemKey = RegistryKey.of(Registries.ITEM.getKey(),
                    Identifier.of(TheAlchemistsBarrel.MOD_ID, def.id));

            if (def.rarity != null && !def.rarity.isBlank()) {
                TagKey<Item> tag = rarityTags.computeIfAbsent(def.rarity,
                        r -> TagKey.of(Registries.ITEM.getKey(),
                                Identifier.of(TheAlchemistsBarrel.MOD_ID, "rarity/" + r)));
                this.builder(tag).add(itemKey);
            }

            for (String culture : def.cultures) {
                if (culture == null || culture.isBlank())
                    continue;
                TagKey<Item> tag = cultureTags.computeIfAbsent(culture,
                        c -> TagKey.of(Registries.ITEM.getKey(),
                                Identifier.of(TheAlchemistsBarrel.MOD_ID, "culture/" + c)));
                this.builder(tag).add(itemKey);
            }

            for (String t : def.tags) {
                if (t == null || t.isBlank())
                    continue;
                TagKey<Item> tag = TagKey.of(Registries.ITEM.getKey(),
                        Identifier.of(TheAlchemistsBarrel.MOD_ID, t));
                this.builder(tag).add(itemKey);
            }
        }
    }

    @Override
    public String getName() {
        return "TABTagProvider";
    }

    // Removed getName() override as it is not required
}
