
package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public class TABRecipeProvider extends FabricRecipeProvider {
    public TABRecipeProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @SuppressWarnings("null")
    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup,
            RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                var itemLookup =
                        registryLookup.getOrThrow(net.minecraft.registry.RegistryKeys.ITEM);
                MaterialRegistry.load();
                for (MaterialDef def : MaterialRegistry.MATERIALS) {
                    if (def.recipe == null)
                        continue;
                    Identifier id = Identifier.of(MaterialRegistry.MOD_ID, def.id);
                    if ("shaped".equals(def.recipe.type)) {
                        var r = def.recipe;
                        var outputItem = ModContent.ITEMS.get(def.id);
                        if (outputItem == null)
                            continue;
                        ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(itemLookup,
                                RecipeCategory.MISC, outputItem, r.count);
                        for (String row : r.pattern) {
                            builder.pattern(row);
                        }
                        r.keys.forEach((symbol, itemId) -> builder.input(symbol.charAt(0),
                                ModContent.ITEMS.get(itemId)));
                        if (r.group != null) {
                            builder.group(r.group);
                        }
                        builder.criterion("has_item",
                                net.minecraft.advancement.criterion.InventoryChangedCriterion.Conditions
                                        .items(outputItem));
                        builder.offerTo(this.exporter, id.toString());
                    }
                    // Add shapeless/smelting/etc. as needed
                }
            }
        };
    }

    @Override
    public String getName() {
        return "TABRecipeProvider";
    }
}
