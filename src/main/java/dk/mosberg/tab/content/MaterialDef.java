package dk.mosberg.tab.content;

import java.util.List;
import java.util.Map;

/**
 * Schema-driven definition for a generated material entry. Populated via Gson from
 * data/tab/schemas/materials.json.
 */
public final class MaterialDef {
    /** e.g. "barrels/oak_iron_barrel" or "kegs/iron_keg_block" */
    public String id;

    /** "item" | "block" */
    public String type;

    /** e.g. "assets/tab/textures/item/barrels/oak_iron_barrel.png" */
    public String path;

    /**
     * Whether this material has an item form. - For type=item, this should be true (or omitted). -
     * For type=block, this controls BlockItem generation.
     */
    public boolean has_item = true;

    /** Optional metadata used by TABTagProvider */
    public String rarity;
    public List<String> cultures = List.of();
    public List<String> tags = List.of();

    /** Optional metadata used by TABAdvancementProvider */
    public AdvancementDef advancement;

    /** Optional metadata used by TABRecipeProvider */
    public RecipeDef recipe;

    public static final class AdvancementDef {
        public String title;
        public String description;
        /** "task" | "goal" | "challenge" */
        public String frame = "task";
        /** Optional: full identifier string, e.g. "minecraft:story/root" */
        public String parent;
        public boolean hidden;
    }

    public static final class RecipeDef {
        /** "shaped" (extend later: "shapeless", "smelting", etc.) */
        public String type = "shaped";

        public int count = 1;
        public String group;

        /** Shaped recipe pattern rows. */
        public List<String> pattern = List.of();

        /**
         * Key mapping of symbol -> item id. Item id can be "minecraft:iron_ingot" or
         * "tab:some_item" or plain "some_item" (treated as mod item id).
         */
        public Map<String, String> keys = Map.of();
    }
}
