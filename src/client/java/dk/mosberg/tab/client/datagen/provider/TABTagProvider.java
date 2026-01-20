package dk.mosberg.tab.client.datagen.provider;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TABTagProvider extends FabricTagProvider.ItemTagProvider {

    public TABTagProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @SuppressWarnings("null")
    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        MaterialRegistry.load();

        var rarityTags = new HashMap<String, TagKey<Item>>();
        var cultureTags = new HashMap<String, TagKey<Item>>();

        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if (!def.has_item)
                continue;

            // Get the registry key for the item
            Identifier itemId = Identifier.of(MaterialRegistry.MOD_ID, def.id);
            var itemKey = net.minecraft.registry.RegistryKey.of(RegistryKeys.ITEM, itemId);

            if (def.rarity != null) {
                TagKey<Item> tag =
                        rarityTags.computeIfAbsent(def.rarity, r -> TagKey.of(RegistryKeys.ITEM,
                                Identifier.of(MaterialRegistry.MOD_ID, "rarity/" + r)));
                builder(tag).add(itemKey);
            }

            for (String culture : def.cultures) {
                TagKey<Item> tag =
                        cultureTags.computeIfAbsent(culture, c -> TagKey.of(RegistryKeys.ITEM,
                                Identifier.of(MaterialRegistry.MOD_ID, "culture/" + c)));
                builder(tag).add(itemKey);
            }

            for (String t : def.tags) {
                TagKey<Item> tag =
                        TagKey.of(RegistryKeys.ITEM, Identifier.of(MaterialRegistry.MOD_ID, t));
                builder(tag).add(itemKey);
            }
        }
    }
}
