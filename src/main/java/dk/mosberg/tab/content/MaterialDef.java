package dk.mosberg.tab.content;

import java.util.List;
import java.util.Map;

public final class MaterialDef {

    /** Material identifier (path part), may include slashes (e.g. barrels/oak_iron_barrel). */
    public String id;

    /** "item" | "block". */
    public String type;

    /**
     * Generator output field (stable resource path), e.g. assets/tab/textures/item/barrels/x.png.
     */
    public String path;

    /**
     * Optional item id used when {@link #type} is "block". Allows block id
     * "barrels/oak_iron_barrel_block" to map to item id "barrels/oak_iron_barrel".
     */
    public String item_id;

    public String category;
    public String subtype;
    public String rarity;

    public int tier = 1;

    public List<String> biomes = List.of();
    public List<String> cultures = List.of();
    public List<String> tags = List.of();

    public String texture;
    public String overlay_texture;

    public boolean has_item = true;

    public String loot_table = "self";

    public double hardness = 1.0;
    public double resistance = 1.0;
    public int luminance = 0;
    public double slipperiness = 0.6;

    public int max_stack_size = 64;

    public int fire_spread = 0;
    public int fire_flammability = 0;

    public RecipeDef recipe;
    public AdvancementDef advancement;

    public static final class RecipeDef {
        public String type; // "shaped" | "shapeless" | ...
        public List<String> pattern; // shaped only
        public Map<String, String> keys; // symbol -> item id (path)
        public int count = 1;
        public String group;
    }

    public static final class AdvancementDef {
        public String parent;
        public String title;
        public String description;
        public String frame; // "task" | "goal" | "challenge"
        public boolean hidden = false;
    }
}
