package dk.mosberg.tab.content;

import java.util.List;
import java.util.Map;

public class MaterialDef {
    public String id;
    public String type; // "item" | "block"
    public String category; // "wood", "metal", "gem", etc.
    public String subtype; // "plank", "ore", "ingot", etc.

    public String rarity; // "common", "uncommon", "rare", "epic"
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

    public static class RecipeDef {
        public String type; // "shaped" | "shapeless" | "smelting" | etc.
        public List<String> pattern;
        public Map<String, String> keys;
        public int count = 1;
        public String group;
    }

    public static class AdvancementDef {
        public String parent;
        public String title;
        public String description;
        public String frame; // "task" | "goal" | "challenge"
        public boolean hidden = false;
    }
}
