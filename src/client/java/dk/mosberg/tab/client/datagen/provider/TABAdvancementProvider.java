package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import dk.mosberg.tab.content.MaterialDef;
import dk.mosberg.tab.content.MaterialRegistry;
import dk.mosberg.tab.registry.ModContent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TABAdvancementProvider extends FabricAdvancementProvider {

    public TABAdvancementProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @SuppressWarnings("removal")
    @Override
    public void generateAdvancement(
            @SuppressWarnings("null") RegistryWrapper.WrapperLookup registries,
            @SuppressWarnings("null") Consumer<AdvancementEntry> consumer) {
        MaterialRegistry.load();

        for (MaterialDef def : MaterialRegistry.MATERIALS) {
            if (def.advancement == null)
                continue;

            var adv = def.advancement;

            // Newer API style: build(consumer, idString)
            String advId = MaterialRegistry.MOD_ID + ":materials/" + def.id;

            Advancement.Builder builder = Advancement.Builder.create()
                    .display(ModContent.ITEMS.get(def.id), Text.literal(adv.title),
                            Text.literal(adv.description), null, switch (adv.frame) {
                                case "goal" -> AdvancementFrame.GOAL;
                                case "challenge" -> AdvancementFrame.CHALLENGE;
                                default -> AdvancementFrame.TASK;
                            }, true, !adv.hidden, false)
                    .criterion("has_item", InventoryChangedCriterion.Conditions
                            .items(ModContent.ITEMS.get(def.id)));

            if (adv.parent != null) {
                builder.parent(Identifier.of(adv.parent));
            }

            builder.build(consumer, advId);
        }
    }
}
