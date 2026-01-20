package dk.mosberg.tab.client.datagen.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TABTagProvider extends FabricTagProvider.ItemTagProvider {
    public TABTagProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        MaterialRegistry.load();

        Map<String, TagKey<Item>> rarityTags = new HashMap<>();
        Map<String, TagKey<Item>> cultureTags = new HashMap<>();

        for (MaterialDef def : MaterialRegistry.all()) {
            if (!def.has_item)
                continue;

            Identifier itemId = Identifier.of(TheAlchemistsBarrel.MOD_ID, def.id);
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, itemId);

            if (def.rarity != null && !def.rarity.isBlank()) {
                TagKey<Item> tag =
                        rarityTags.computeIfAbsent(def.rarity, r -> TagKey.of(RegistryKeys.ITEM,
                                Identifier.of(TheAlchemistsBarrel.MOD_ID, "rarity/" + r)));
                getOrCreateTagBuilder(tag).add(itemKey);
            }

            for (String culture : def.cultures) {
                if (culture == null || culture.isBlank())
                    continue;

                TagKey<Item> tag =
                        cultureTags.computeIfAbsent(culture, c -> TagKey.of(RegistryKeys.ITEM,
                                Identifier.of(TheAlchemistsBarrel.MOD_ID, "culture/" + c)));
                getOrCreateTagBuilder(tag).add(itemKey);
            }

            for (String t : def.tags) {
                if (t == null || t.isBlank())
                    continue;

                TagKey<Item> tag =
                        TagKey.of(RegistryKeys.ITEM, Identifier.of(TheAlchemistsBarrel.MOD_ID, t));
                getOrCreateTagBuilder(tag).add(itemKey);
            }
        }
    }

    @Override
    public String getName() {
        return "TABTagProvider";
    }
}
