package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public final class TABRecipeProvider extends FabricRecipeProvider {
    public TABRecipeProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup,
            RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                MaterialRegistry.load();

                for (MaterialDef def : MaterialRegistry.all()) {
                    if (def.recipe == null)
                        continue;

                    var recipe = def.recipe;
                    if (!"shaped".equals(recipe.type))
                        continue;

                    Item out = ModContent.getItem(def.id);
                    if (out == null)
                        continue;

                    Identifier outId = Identifier.of(TheAlchemistsBarrel.MOD_ID, def.id);

                    ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder
                            .create(RecipeCategory.MISC, out, Math.max(1, recipe.count));

                    for (String row : recipe.pattern) {
                        builder.pattern(row);
                    }

                    recipe.keys.forEach((symbol, itemIdString) -> {
                        if (symbol == null || symbol.isBlank() || itemIdString == null
                                || itemIdString.isBlank())
                            return;

                        Identifier ingredientId = normalizeToIdentifier(itemIdString);
                        Item ingredient = Registries.ITEM.get(ingredientId);

                        // If unresolved, skip (keeps datagen from hard failing on bad data)
                        if (ingredient == null || ingredient == Registries.ITEM
                                .get(Identifier.of("minecraft", "air"))) {
                            return;
                        }

                        builder.input(symbol.charAt(0), ingredient);
                    });

                    if (recipe.group != null && !recipe.group.isBlank()) {
                        builder.group(recipe.group);
                    }

                    builder.criterion("has_item",
                            net.minecraft.advancement.criterion.InventoryChangedCriterion.Conditions
                                    .items(out));
                    builder.offerTo(exporter, outId);
                }
            }
        };
    }

    private static Identifier normalizeToIdentifier(String s) {
        // If "namespace:path" use it directly; otherwise treat as mod item id.
        Identifier parsed = Identifier.tryParse(s);
        return parsed != null ? parsed : Identifier.of(TheAlchemistsBarrel.MOD_ID, s);
    }

    @Override
    public String getName() {
        return "TABRecipeProvider";
    }
}
