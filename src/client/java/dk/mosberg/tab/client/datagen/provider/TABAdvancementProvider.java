package dk.mosberg.tab.client.datagen.provider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import dk.mosberg.tab.TheAlchemistsBarrel;
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

public final class TABAdvancementProvider extends FabricAdvancementProvider {
    public TABAdvancementProvider(FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registries,
            Consumer<AdvancementEntry> consumer) {
        MaterialRegistry.load();

        for (MaterialDef def : MaterialRegistry.all()) {
            if (def.advancement == null)
                continue;

            var adv = def.advancement;
            var icon = ModContent.getItem(def.id);
            if (icon == null)
                continue;

            String advId = TheAlchemistsBarrel.MOD_ID + ":materials/" + def.id.replace('/', '_');

            Advancement.Builder builder = Advancement.Builder.create()
                    .display(icon, Text.literal(adv.title == null ? def.id : adv.title),
                            Text.literal(adv.description == null ? "" : adv.description), null,
                            switch (adv.frame) {
                                case "goal" -> AdvancementFrame.GOAL;
                                case "challenge" -> AdvancementFrame.CHALLENGE;
                                default -> AdvancementFrame.TASK;
                            }, true, !adv.hidden, false)
                    .criterion("has_item", InventoryChangedCriterion.Conditions.items(icon));

            if (adv.parent != null && !adv.parent.isBlank()) {
                builder.parent(Identifier.of(adv.parent));
            }

            builder.build(consumer, advId);
        }
    }

    @Override
    public String getName() {
        return "TABAdvancementProvider";
    }
}
