# The Alchemist's Barrel

A Minecraft mod that adds barrels, kegs, and alchemical flasks for storing and fermenting liquids.

## Project Structure

```
the-alchemists-barrel
├─ build.gradle
├─ GenerateMaterialsJson.groovy
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
├─ LICENSE
├─ README.md
├─ settings.gradle
└─ src
   ├─ client
   │  ├─ java
   │  │  └─ dk
   │  │     └─ mosberg
   │  │        └─ tab
   │  │           └─ client
   │  │              ├─ datagen
   │  │              │  ├─ provider
   │  │              │  │  ├─ TABAdvancementProvider.java
   │  │              │  │  ├─ TABBlockStateProvider.java
   │  │              │  │  ├─ TABLanguageProvider.java
   │  │              │  │  ├─ TABLootTableProvider.java
   │  │              │  │  ├─ TABModelProvider.java
   │  │              │  │  ├─ TABRecipeProvider.java
   │  │              │  │  └─ TABTagProvider.java
   │  │              │  └─ TheAlchemistsBarrelDataGenerator.java
   │  │              └─ TheAlchemistsBarrelClient.java
   │  └─ resources
   └─ main
      ├─ generated
      │  └─ resources
      │     └─ .cache
      │        ├─ 1f196249b451c60ec2126508f8804d163d173eb7
      │        ├─ 3ebf3cc0cce906c0876a66a3e7a04b685e8665fa
      │        ├─ 47a4128ab7531b36364b1f9cddbfbbc53931474b
      │        ├─ 4ccefd237ac327713182afd2455db78fa4b84901
      │        ├─ 74c5e5305a156039bfca9f6224904712cd515219
      │        ├─ 8e1e067cb6f722d79fbe4472acb035ad935aff22
      │        └─ bbb8c5f91f93ba118ba628f911afbf25e6029f9e
      ├─ java
      │  └─ dk
      │     └─ mosberg
      │        └─ tab
      │           ├─ content
      │           │  ├─ MaterialDef.java
      │           │  └─ MaterialRegistry.java
      │           ├─ registry
      │           │  └─ ModContent.java
      │           ├─ TheAlchemistsBarrel.java
      │           └─ tools
      │              └─ TextureWatcher.java
      └─ resources
         ├─ assets
         │  └─ tab
         │     ├─ blockstates
         │     │  ├─ barrels
         │     │  │  └─ oak_iron_barrel_block.json
         │     │  └─ kegs
         │     │     └─ iron_keg_block.json
         │     ├─ items
         │     │  ├─ barrels
         │     │  │  ├─ oak_iron_barrel.json
         │     │  │  └─ oak_iron_barrel_block.json
         │     │  ├─ flasks
         │     │  │  ├─ fluid
         │     │  │  │  ├─ large_flask_fluid_overlay.json
         │     │  │  │  ├─ medium_flask_fluid_overlay.json
         │     │  │  │  └─ small_flask_fluid_overlay.json
         │     │  │  ├─ large
         │     │  │  │  └─ large_oak_glass_flask.json
         │     │  │  ├─ medium
         │     │  │  │  └─ medium_oak_glass_flask.json
         │     │  │  └─ small
         │     │  │     └─ small_oak_glass_flask.json
         │     │  └─ kegs
         │     │     ├─ iron_keg.json
         │     │     └─ iron_keg_block.json
         │     ├─ lang
         │     │  └─ en_us.json
         │     ├─ models
         │     │  ├─ block
         │     │  │  ├─ barrels
         │     │  │  │  ├─ barrel_block.json
         │     │  │  │  └─ oak_iron_barrel_block.json
         │     │  │  └─ kegs
         │     │  │     ├─ iron_keg_block.json
         │     │  │     └─ keg_block.json
         │     │  └─ item
         │     │     ├─ barrels
         │     │     │  └─ oak_iron_barrel.json
         │     │     ├─ flasks
         │     │     │  ├─ fluid
         │     │     │  │  ├─ large_flask_fluid_overlay.json
         │     │     │  │  ├─ medium_flask_fluid_overlay.json
         │     │     │  │  └─ small_flask_fluid_overlay.json
         │     │     │  ├─ large
         │     │     │  │  └─ large_oak_glass_flask.json
         │     │     │  ├─ medium
         │     │     │  │  └─ medium_oak_glass_flask.json
         │     │     │  └─ small
         │     │     │     └─ small_oak_glass_flask.json
         │     │     └─ kegs
         │     │        └─ iron_keg.json
         │     └─ textures
         │        ├─ block
         │        │  ├─ barrels
         │        │  │  └─ oak_iron_barrel_block.png
         │        │  └─ kegs
         │        │     └─ iron_keg_block.png
         │        └─ item
         │           ├─ barrels
         │           │  └─ oak_iron_barrel.png
         │           ├─ flasks
         │           │  ├─ fluid
         │           │  │  ├─ large_flask_fluid_overlay.png
         │           │  │  ├─ medium_flask_fluid_overlay.png
         │           │  │  └─ small_flask_fluid_overlay.png
         │           │  ├─ large
         │           │  │  └─ large_oak_glass_flask.png
         │           │  ├─ medium
         │           │  │  └─ medium_oak_glass_flask.png
         │           │  └─ small
         │           │     └─ small_oak_glass_flask.png
         │           └─ kegs
         │              └─ iron_keg.png
         ├─ data
         │  └─ tab
         │     └─ schemas
         │        └─ materials.json
         ├─ fabric.mod.json
         └─ icon.png
```

## Usage

### Generating materials.json

```bash
groovy generateMaterialsJson
```

## Resources

### Blockstate Files

#### resources/assets/tab/blockstates/barrels/oak_iron_barrel_block.json

```json
{
  "variants": {
    "normal": { "model": "tab:block/barrels/oak_iron_barrel_block" },
    "facing=north": { "model": "tab:block/barrels/oak_iron_barrel_block" },
    "facing=south": {
      "model": "tab:block/barrels/oak_iron_barrel_block",
      "y": 180
    },
    "facing=west": {
      "model": "tab:block/barrels/oak_iron_barrel_block",
      "y": 270
    },
    "facing=east": {
      "model": "tab:block/barrels/oak_iron_barrel_block",
      "y": 90
    }
  }
}
```

#### resources/assets/tab/blockstates/kegs/iron_keg_block.json

```json
{
  "variants": {
    "normal": { "model": "tab:block/kegs/iron_keg_block" },
    "facing=north": { "model": "tab:block/kegs/iron_keg_block" },
    "facing=south": {
      "model": "tab:block/kegs/iron_keg_block",
      "y": 180
    },
    "facing=west": {
      "model": "tab:block/kegs/iron_keg_block",
      "y": 270
    },
    "facing=east": {
      "model": "tab:block/kegs/iron_keg_block",
      "y": 90
    }
  }
}
```

### Items Files

#### resources/assets/tab/items/barrels/oak_iron_barrel_block.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:block/barrels/oak_iron_barrel_block"
  }
}
```

#### resources/assets/tab/items/barrels/oak_iron_barrel.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/barrels/oak_iron_barrel"
  }
}
```

#### resources/assets/tab/items/flasks/fluid/small_flask_fluid_overlay.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/fluid/small_flask_fluid_overlay"
  }
}
```

#### resources/assets/tab/items/flasks/fluid/medium_flask_fluid_overlay.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/fluid/medium_flask_fluid_overlay"
  }
}
```

#### resources/assets/tab/items/flasks/fluid/large_flask_fluid_overlay.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/fluid/large_flask_fluid_overlay"
  }
}
```

#### resources/assets/tab/items/flasks/large/large_oak_glass_flask.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/large/large_oak_glass_flask"
  }
}
```

#### resources/assets/tab/items/flasks/medium/medium_oak_glass_flask.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/medium/medium_oak_glass_flask"
  }
}
```

#### resources/assets/tab/items/flasks/small/small_oak_glass_flask.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/flasks/small/small_oak_glass_flask"
  }
}
```

#### resources/assets/tab/items/kegs/iron_keg_block.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:block/kegs/iron_keg_block"
  }
}
```

#### resources/assets/tab/items/kegs/iron_keg.json

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "tab:item/kegs/iron_keg"
  }
}
```

### Language Files

#### resources/assets/tab/lang/en_us.json

```json
{
  "comment_smallflasks": "Small Flasks Tab + Items + Blocks",
  "itemGroup.tab.smallflasks": "Small Flasks",

  "item.tab.flasks.small.small_oak_glass_flask": "Small Oak Glass Flask",

  "overlay.tab.flasks.fluid.small_flask_fluid_overlay": "Small Flask Fluid Overlay",

  "comment_mediumflasks": "Medium Flasks Tab + Items + Blocks",
  "itemGroup.tab.mediumflasks": "Medium Flasks",

  "item.tab.flasks.medium.medium_oak_glass_flask": "Medium Oak Glass Flask",

  "overlay.tab.flasks.fluid.medium_flask_fluid_overlay": "Medium Flask Fluid Overlay",

  "comment_largeflasks": "Large Flasks Tab + Items + Blocks",
  "itemGroup.tab.largeflasks": "Large Flasks",

  "item.tab.flasks.large.large_oak_glass_flask": "Large Oak Glass Flask",

  "overlay.tab.flasks.fluid.large_flask_fluid_overlay": "Large Flask Fluid Overlay",

  "comment_barrels": "Barrels Tab + Items + Blocks",
  "itemGroup.tab.barrels": "Barrels",

  "item.tab.barrels.oak_iron_barrel": "Oak Iron Barrel",

  "block.tab.barrels.oak_iron_barrel": "Oak Iron Barrel",

  "comment_kegs": "Kegs Tab + Items + Blocks",
  "itemGroup.tab.kegs": "Kegs",

  "item.tab.kegs.iron_keg": "Iron Keg",

  "block.tab.kegs.iron_keg": "Iron Keg",

  "comment_tooltip": "Tooltip texts",
  "tooltip.tab.fluid.fill": "Fluid: %s mB",
  "tooltip.tab.fluid.partial": "Fluid: %s / %s mB",
  "tooltip.tab.fluid.full": "Fluid: %s mB (full)",
  "tooltip.tab.fluid.empty": "Fluid: (empty)"
}
```

### Models Files

#### Block Models

##### resources/assets/tab/models/block/barrels/barrel_block.json

```json
{
  "ambientocclusion": true,
  "textures": {
    "all": "tab:block/barrels/oak_iron_barrel_block",
    "particle": "#all"
  },
  "elements": [
    {
      "from": [4, 0, 4],
      "to": [12, 1, 12],
      "faces": {
        "north": { "uv": [11.5, 4, 15.5, 4.5], "texture": "#all" },
        "east": { "uv": [11.5, 0.5, 15.5, 1], "texture": "#all" },
        "south": { "uv": [11.5, 0.5, 15.5, 1], "texture": "#all" },
        "west": { "uv": [11.5, 4, 15.5, 4.5], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" },
        "down": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [5, 0, 3],
      "to": [11, 1, 4],
      "faces": {
        "north": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "east": { "uv": [14.5, 4.5, 15, 5], "texture": "#all" },
        "south": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "west": { "uv": [12, 4.5, 12.5, 5], "texture": "#all" },
        "up": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "down": { "uv": [12, 4.5, 15, 5], "texture": "#all" }
      }
    },
    {
      "from": [5, 0, 12],
      "to": [11, 1, 13],
      "faces": {
        "north": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "east": { "uv": [14.5, 0, 15, 0.5], "texture": "#all" },
        "south": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "west": { "uv": [12, 0, 12.5, 0.5], "texture": "#all" },
        "up": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "down": { "uv": [12, 0, 15, 0.5], "texture": "#all" }
      }
    },
    {
      "from": [12, 0, 5],
      "to": [13, 1, 11],
      "faces": {
        "north": { "uv": [15.5, 1, 16, 1.5], "texture": "#all" },
        "east": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "south": { "uv": [15.5, 3.5, 16, 4], "texture": "#all" },
        "west": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "up": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "down": { "uv": [15.5, 1, 16, 4], "texture": "#all" }
      }
    },
    {
      "from": [3, 0, 5],
      "to": [4, 1, 11],
      "faces": {
        "north": { "uv": [11, 1, 11.5, 1.5], "texture": "#all" },
        "east": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "south": { "uv": [11, 3.5, 11.5, 4], "texture": "#all" },
        "west": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "up": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "down": { "uv": [11, 1, 11.5, 4], "texture": "#all" }
      }
    },
    {
      "from": [3, 1, 3],
      "to": [13, 15, 13],
      "faces": {
        "north": { "uv": [5.5, 0.5, 10.5, 7.5], "texture": "#all" },
        "east": { "uv": [5.5, 0.5, 10.5, 7.5], "texture": "#all" },
        "south": { "uv": [5.5, 0.5, 10.5, 7.5], "texture": "#all" },
        "west": { "uv": [5.5, 0.5, 10.5, 7.5], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" },
        "down": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [4, 1, 2],
      "to": [12, 15, 3],
      "faces": {
        "north": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "east": { "uv": [10.5, 0.5, 11, 7.5], "texture": "#all" },
        "south": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "west": { "uv": [5, 0.5, 5.5, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [4, 1, 13],
      "to": [12, 15, 14],
      "faces": {
        "north": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "east": { "uv": [10.5, 0.5, 11, 7.5], "texture": "#all" },
        "south": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "west": { "uv": [5, 0.5, 5.5, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [13, 1, 4],
      "to": [14, 15, 12],
      "faces": {
        "north": { "uv": [10.5, 0.5, 11, 7.5], "texture": "#all" },
        "east": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "south": { "uv": [5, 0.5, 5.5, 7.5], "texture": "#all" },
        "west": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 12, 4.5], "texture": "#all" },
        "down": { "uv": [15, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [2, 1, 4],
      "to": [3, 15, 12],
      "faces": {
        "north": { "uv": [5, 0.5, 5.5, 7.5], "texture": "#all" },
        "east": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "south": { "uv": [10.5, 0.5, 11, 7.5], "texture": "#all" },
        "west": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 12, 4.5], "texture": "#all" },
        "down": { "uv": [15, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [4, 15, 4],
      "to": [12, 15.99, 12],
      "rotation": { "angle": 0, "axis": "y", "origin": [0, 1, 0] },
      "faces": {
        "north": { "uv": [0.5, 0.5, 4.5, 1], "texture": "#all" },
        "east": { "uv": [0.5, 4, 4.5, 4.5], "texture": "#all" },
        "south": { "uv": [0.5, 4, 4.5, 4.5], "texture": "#all" },
        "west": { "uv": [0.5, 0.5, 4.5, 1], "texture": "#all" },
        "up": { "uv": [0.5, 0.5, 4.5, 4.5], "texture": "#all" },
        "down": { "uv": [0.5, 0.5, 4.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [5, 15, 3],
      "to": [11, 15.99, 4],
      "rotation": { "angle": 0, "axis": "y", "origin": [0, 1, 0] },
      "faces": {
        "north": { "uv": [1, 4.5, 4, 5], "texture": "#all" },
        "east": { "uv": [3.5, 0, 4, 0.5], "texture": "#all" },
        "south": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "west": { "uv": [1, 0, 1.5, 0.5], "texture": "#all" },
        "up": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "down": { "uv": [1, 4.5, 4, 5], "texture": "#all" }
      }
    },
    {
      "from": [5, 15, 12],
      "to": [11, 15.99, 13],
      "rotation": { "angle": 0, "axis": "y", "origin": [0, 1, 0] },
      "faces": {
        "north": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "east": { "uv": [3.5, 0, 4, 0.5], "texture": "#all" },
        "south": { "uv": [1, 4.5, 4, 5], "texture": "#all" },
        "west": { "uv": [1, 0, 1.5, 0.5], "texture": "#all" },
        "up": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "down": { "uv": [1, 4.5, 4, 5], "texture": "#all" }
      }
    },
    {
      "from": [12, 15, 5],
      "to": [13, 15.99, 11],
      "rotation": { "angle": 0, "axis": "y", "origin": [0, 1, 0] },
      "faces": {
        "north": { "uv": [3.5, 0, 4, 0.5], "texture": "#all" },
        "east": { "uv": [1, 4.5, 4, 5], "texture": "#all" },
        "south": { "uv": [1, 0, 1.5, 0.5], "texture": "#all" },
        "west": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "up": { "uv": [0, 1, 0.5, 4], "texture": "#all" },
        "down": { "uv": [4.5, 1, 5, 4], "texture": "#all" }
      }
    },
    {
      "from": [3, 15, 5],
      "to": [4, 15.99, 11],
      "rotation": { "angle": 0, "axis": "y", "origin": [0, 1, 0] },
      "faces": {
        "north": { "uv": [1, 0, 1.5, 0.5], "texture": "#all" },
        "east": { "uv": [1, 4.5, 4, 5], "texture": "#all" },
        "south": { "uv": [3.5, 0, 4, 0.5], "texture": "#all" },
        "west": { "uv": [1, 0, 4, 0.5], "texture": "#all" },
        "up": { "uv": [4.5, 1, 5, 4], "texture": "#all" },
        "down": { "uv": [0, 1, 0.5, 4], "texture": "#all" }
      }
    }
  ],
  "display": {
    "firstperson_righthand": {
      "rotation": [0, 135, 0],
      "scale": [0.4, 0.4, 0.4]
    },
    "gui": {
      "rotation": [30, -135, 0],
      "scale": [0.6, 0.6, 0.6]
    }
  }
}
```

##### resources/assets/tab/models/block/barrels/oak_iron_barrel_block.json

```json
{
  "parent": "tab:block/barrels/barrel_block",
  "textures": {
    "all": "tab:block/barrels/oak_iron_barrel_block",
    "particle": "#all"
  }
}
```

##### resources/assets/tab/models/block/kegs/keg_block.json

```json
{
  "ambientocclusion": true,
  "textures": {
    "all": "tab:block/kegs/iron_keg_block",
    "particle": "#all"
  },
  "elements": [
    {
      "from": [4, 0, 4],
      "to": [12, 1, 12],
      "faces": {
        "north": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "east": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "south": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "west": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" },
        "down": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [5, 0, 3],
      "to": [11, 1, 4],
      "faces": {
        "north": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "east": { "uv": [14.5, 4.5, 15, 5], "texture": "#all" },
        "south": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "west": { "uv": [12, 4.5, 12.5, 5], "texture": "#all" },
        "up": { "uv": [12, 4.5, 15, 5], "texture": "#all" },
        "down": { "uv": [12, 4.5, 15, 5], "texture": "#all" }
      }
    },
    {
      "from": [5, 0, 12],
      "to": [11, 1, 13],
      "faces": {
        "north": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "east": { "uv": [12, 0, 12.5, 0.5], "texture": "#all" },
        "south": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "west": { "uv": [14.5, 0, 15, 0.5], "texture": "#all" },
        "up": { "uv": [12, 0, 15, 0.5], "texture": "#all" },
        "down": { "uv": [12, 0, 15, 0.5], "texture": "#all" }
      }
    },
    {
      "from": [12, 0, 5],
      "to": [13, 1, 11],
      "faces": {
        "north": { "uv": [11, 3.5, 11.5, 4], "texture": "#all" },
        "east": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "south": { "uv": [11, 1, 11.5, 1.5], "texture": "#all" },
        "west": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "up": { "uv": [11, 1, 11.5, 4], "texture": "#all" },
        "down": { "uv": [11, 1, 11.5, 4], "texture": "#all" }
      }
    },
    {
      "from": [3, 0, 5],
      "to": [4, 1, 11],
      "faces": {
        "north": { "uv": [15.5, 3.5, 16, 4], "texture": "#all" },
        "east": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "south": { "uv": [15.5, 1, 16, 1.5], "texture": "#all" },
        "west": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "up": { "uv": [15.5, 1, 16, 4], "texture": "#all" },
        "down": { "uv": [15.5, 1, 16, 4], "texture": "#all" }
      }
    },
    {
      "from": [3, 1, 3],
      "to": [13, 15, 13],
      "faces": {
        "north": { "uv": [5, 0.5, 11, 7.5], "texture": "#all" },
        "east": { "uv": [5, 0.5, 11, 7.5], "texture": "#all" },
        "south": { "uv": [5, 0.5, 11, 7.5], "texture": "#all" },
        "west": { "uv": [5, 0.5, 11, 7.5], "texture": "#all" },
        "up": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" },
        "down": { "uv": [11.5, 0.5, 15.5, 4.5], "texture": "#all" }
      }
    },
    {
      "from": [4, 1, 2],
      "to": [12, 15, 3],
      "faces": {
        "north": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "east": { "uv": [10, 0.5, 10.5, 7.5], "texture": "#all" },
        "south": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "west": { "uv": [5.5, 0.5, 6, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [4, 1, 13],
      "to": [12, 15, 14],
      "faces": {
        "north": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "east": { "uv": [10, 0.5, 10.5, 7.5], "texture": "#all" },
        "south": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "west": { "uv": [5.5, 0.5, 6, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [13, 1, 4],
      "to": [14, 15, 12],
      "faces": {
        "north": { "uv": [10, 0.5, 10.5, 7.5], "texture": "#all" },
        "east": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "south": { "uv": [5.5, 0.5, 6, 7.5], "texture": "#all" },
        "west": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [2, 1, 4],
      "to": [3, 15, 12],
      "faces": {
        "north": { "uv": [10, 0.5, 10.5, 7.5], "texture": "#all" },
        "east": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "south": { "uv": [5.5, 0.5, 6, 7.5], "texture": "#all" },
        "west": { "uv": [6, 0.5, 10, 7.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 7.5, 10, 8], "texture": "#all" }
      }
    },
    {
      "from": [7, 15, 7],
      "to": [9, 15.99, 9],
      "faces": {
        "north": { "uv": [2, 2.5, 3, 3], "texture": "#all" },
        "east": { "uv": [2, 2, 3, 2.5], "texture": "#all" },
        "south": { "uv": [2, 2.5, 3, 3], "texture": "#all" },
        "west": { "uv": [2, 2, 3, 2.5], "texture": "#all" },
        "up": { "uv": [2, 2, 3, 3], "texture": "#all" },
        "down": { "uv": [2, 2, 3, 3], "texture": "#all" }
      }
    },
    {
      "from": [4, 15, 3],
      "to": [12, 15.99, 4],
      "faces": {
        "north": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "east": { "uv": [10, 0, 10.5, 0.5], "texture": "#all" },
        "south": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "west": { "uv": [5.5, 0, 6, 0.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 0, 10, 0.5], "texture": "#all" }
      }
    },
    {
      "from": [4, 15, 12],
      "to": [12, 15.99, 13],
      "faces": {
        "north": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "east": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "south": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "west": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 0, 10, 0.5], "texture": "#all" }
      }
    },
    {
      "from": [12, 15, 4],
      "to": [13, 15.99, 12],
      "faces": {
        "north": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "east": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "south": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "west": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 0, 10, 0.5], "texture": "#all" }
      }
    },
    {
      "from": [3, 15, 4],
      "to": [4, 15.99, 12],
      "faces": {
        "north": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "east": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "south": { "uv": [6, 0, 6.5, 0.5], "texture": "#all" },
        "west": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "up": { "uv": [6, 0, 10, 0.5], "texture": "#all" },
        "down": { "uv": [6, 0, 10, 0.5], "texture": "#all" }
      }
    }
  ],
  "display": {
    "firstperson_righthand": {
      "rotation": [0, 135, 0],
      "scale": [0.4, 0.4, 0.4]
    },
    "gui": {
      "rotation": [30, -135, 0],
      "scale": [0.6, 0.6, 0.6]
    }
  }
}
```

##### resources/assets/tab/models/block/kegs/iron_keg_block.json

```json
{
  "parent": "tab:block/kegs/keg_block",
  "textures": {
    "all": "tab:block/kegs/iron_keg_block",
    "particle": "#all"
  }
}
```

#### Item Models

##### resources/assets/tab/models/item/barrels/oak_iron_barrel.json

```json
{
  "parent": "item/generated",
  "textures": {
    "all": "tab:item/barrels/oak_iron_barrel"
  }
}
```

##### resources/assets/tab/models/item/kegs/iron_keg.json

```json
{
  "parent": "item/generated",
  "textures": {
    "all": "tab:item/kegs/iron_keg"
  }
}
```

##### resources/assets/tab/models/item/flasks/large/large_oak_glass_flask.json

```json
{
  "parent": "item/generated",
  "textures": {
    "all": "tab:item/flasks/large/large_oak_glass_flask",
    "overlay": "tab:item/flasks/fluid/large_flask_fluid_overlay"
  }
}
```

##### resources/assets/tab/models/item/flasks/medium/medium_oak_glass_flask.json

```json
{
  "parent": "item/generated",
  "textures": {
    "all": "tab:item/flasks/medium/medium_oak_glass_flask",
    "overlay": "tab:item/flasks/fluid/medium_flask_fluid_overlay"
  }
}
```

##### resources/assets/tab/models/item/flasks/small/small_oak_glass_flask.json

```json
{
  "parent": "item/generated",
  "textures": {
    "all": "tab:item/flasks/small/small_oak_glass_flask",
    "overlay": "tab:item/flasks/fluid/small_flask_fluid_overlay"
  }
}
```

##### resources/assets/tab/models/item/flasks/fluid/large_flask_fluid_overlay.json

```json
{
  "parent": "item/generated",
  "textures": {
    "overlay": "tab:item/flasks/fluid/large_flask_fluid_overlay"
  }
}
```

##### resources/assets/tab/models/item/flasks/fluid/medium_flask_fluid_overlay.json

```json
{
  "parent": "item/generated",
  "textures": {
    "overlay": "tab:item/flasks/fluid/medium_flask_fluid_overlay"
  }
}
```

##### resources/assets/tab/models/item/flasks/fluid/small_flask_fluid_overlay.json

```json
{
  "parent": "item/generated",
  "textures": {
    "overlay": "tab:item/flasks/fluid/small_flask_fluid_overlay"
  }
}
```

### Texture Files

The texture files for the barrels, kegs, and flasks are located in the following directories:

- `resources/assets/tab/textures/block/barrels/`
- `resources/assets/tab/textures/block/kegs/`
- `resources/assets/tab/textures/item/barrels/`
- `resources/assets/tab/textures/item/kegs/`
- `resources/assets/tab/textures/item/flasks/large/`
- `resources/assets/tab/textures/item/flasks/medium/`
- `resources/assets/tab/textures/item/flasks/small/`
- `resources/assets/tab/textures/item/flasks/fluid/`

## Data Files

The data files for the barrels and kegs are located in the following directories:

- `resources/data/tab/recipes/`
- `resources/data/tab/loot_tables/`
- `resources/data/tab/tags/blocks/`
- `resources/data/tab/tags/items/`
- `resources/data/tab/tags/fluid_tags/`
- `resources/data/tab/tags/block_tags/`
- `resources/data/tab/tags/item_tags/`

### Schemas

#### resources/data/tab/schemas/materials.json

```json
{
  "schema": 1,
  "materials": [
    {
      "id": "barrels/oak_iron_barrel",
      "type": "item",
      "path": "assets/tab/textures/item/barrels/oak_iron_barrel.png"
    },
    {
      "id": "barrels/oak_iron_barrel_block",
      "type": "block",
      "path": "assets/tab/textures/block/barrels/oak_iron_barrel_block.png"
    },
    {
      "id": "flasks/fluid/large_flask_fluid_overlay",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/fluid/large_flask_fluid_overlay.png"
    },
    {
      "id": "flasks/fluid/medium_flask_fluid_overlay",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/fluid/medium_flask_fluid_overlay.png"
    },
    {
      "id": "flasks/fluid/small_flask_fluid_overlay",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/fluid/small_flask_fluid_overlay.png"
    },
    {
      "id": "flasks/large/large_oak_glass_flask",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/large/large_oak_glass_flask.png"
    },
    {
      "id": "flasks/medium/medium_oak_glass_flask",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/medium/medium_oak_glass_flask.png"
    },
    {
      "id": "flasks/small/small_oak_glass_flask",
      "type": "item",
      "path": "assets/tab/textures/item/flasks/small/small_oak_glass_flask.png"
    },
    {
      "id": "kegs/iron_keg",
      "type": "item",
      "path": "assets/tab/textures/item/kegs/iron_keg.png"
    },
    {
      "id": "kegs/iron_keg_block",
      "type": "block",
      "path": "assets/tab/textures/block/kegs/iron_keg_block.png"
    }
  ]
}
```
