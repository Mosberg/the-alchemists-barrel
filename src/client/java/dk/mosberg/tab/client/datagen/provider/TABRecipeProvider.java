package dk.mosberg.tab.client.datagen.provider;


import java.util.concurrent.CompletableFuture;
import dk.mosberg.tab.TheAlchemistsBarrel;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.RecipeOutput;
import net.minecraft.data.recipe.RecipeGenerator.RecipeProvider;
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
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup,
            RecipeOutput exporter) {
        return new RecipeProvider() {
            @Override
            public void buildRecipes() {
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
                    var outId = Identifier.of(TheAlchemistsBarrel.MOD_ID, def.id);
                    var builder = ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, out,
                            Math.max(1, recipe.count));
                    for (String row : recipe.pattern)
                        builder.pattern(row);
                    recipe.keys.forEach((symbol, itemIdString) -> {
                        if (symbol == null || symbol.isBlank() || itemIdString == null
                                || itemIdString.isBlank())
                            return;
                        Identifier ingredientId = normalizeToIdentifier(itemIdString);
                        Item ingredient = Registries.ITEM.get(ingredientId);
                        if (ingredient == null || ingredient == Registries.ITEM
                                .get(Identifier.of("minecraft", "air")))
                            return;
                        builder.input(symbol.charAt(0), ingredient);
                    });
                    if (recipe.group != null && !recipe.group.isBlank())
                        builder.group(recipe.group);
                    builder.criterion("has_item", InventoryChangedCriterion.Conditions.items(out));
                    builder.offerTo(exporter, outId);
                }
            }
        };
    }


    @Override
    public String getName() {
        return "TABRecipeProvider";
    }

    private static Identifier normalizeToIdentifier(String s) {
        Identifier parsed = Identifier.tryParse(s);
        return parsed != null ? parsed : Identifier.of(TheAlchemistsBarrel.MOD_ID, s);
    }
}
