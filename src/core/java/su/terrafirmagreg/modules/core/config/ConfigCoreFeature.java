package su.terrafirmagreg.modules.core.config;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public final class ConfigCoreFeature {

  @Comment("Advanced Data settings")
  public final ConfigFeatureAdvancedData ADVANCED_DATA = new ConfigFeatureAdvancedData();

  @Comment("Sink or Swim settings")
  public final ConfigFeatureSinkOrSwim SINK_OR_SWIM = new ConfigFeatureSinkOrSwim();

  @Comment("Hot Or Not settings")
  public final ConfigFeatureHotOrNot HOT_OR_NOT = new ConfigFeatureHotOrNot();

  @Comment("Size And Weight settings")
  public final ConfigFeatureSizeAndWeight SIZE_OR_WEIGHT = new ConfigFeatureSizeAndWeight();


  public static final class ConfigFeatureAdvancedData {

    @Comment({
      "If true, advanced data enable ",
      "Default = true"
    })
    public boolean enable = true;

    @Comment({
      "Is the control key needed to show the advanced data?",
      "Default = true"
    })
    public boolean requireCTRL = true;

    @Comment({
      "Number of characters for NBT data",
      "Default = 40"
    })
    @RangeInt(min = 1, max = 500)
    public int charLimitNBT = 40;

    @Comment({
      "Show ItemStack tool classes when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showToolClass = true;

    @Comment({
      "Show ItemStack OreDictionary matches when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showOreDictionary = true;

    @Comment({
      "Show ItemStack Code Name when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showCodeName = true;

    @Comment({
      "Show ItemStack Unlocalized Name when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showUnlocalizedName = true;

    @Comment({
      "Show ItemStack Metadata when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showMetaData = true;

    @Comment({
      "Show ItemStack Meta's Unlocalized Name when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showMetaUnlocalizedName = true;

    @Comment({
      "Show ItemStack NBT on the tooltip when advanced tooltips are enabled. (F3+H)",
      "Default = true"
    })
    public boolean showNBT = true;
  }

  public static final class ConfigFeatureSinkOrSwim {

    @Comment({
      "If true, sink or swim enable ",
      "Default = true"
    })
    public boolean enable = true;

    @Comment({
      "Armor what will not weigh you down in water."
    })
    public String[] armorWhiteList = new String[]{
      "minecraft:leather_boots",
      "minecraft:leather_leggings",
      "minecraft:leather_chestplate",
      "minecraft:leather_helmet"
    };

    @Comment({
      "Potion effects that will allow you to swim."
    })
    public String[] potionWhiteList = new String[]{
      "waterBreathing",
      "levitation"
    };

    @Comment({
      "Armor enchantments that will allow you to swim."
    })
    public String[] enchantWhiteList = new String[]{
      "minecraft:depth_strider",
      "minecraft:respiration"
    };

    @Comment({
      "Baubles that will allow you to swim."
    })
    public String[] baublesWhiteList = new String[]{
      "botania:waterring"
    };

    @Comment({
      "Biomes in which you can't swim.  Simply put down 'All' to disable swimming in all biomes."
    })
    public String[] biomeBlackList = new String[]{
      "Ocean",
      "Beach",
      "River",
      "Swamp",
      };
  }

  public static final class ConfigFeatureHotOrNot {

    @Comment({
      "If true, hot or not enable ",
      "Default = true"
    })
    public boolean enable = true;

    @Comment({
      "If true, hot/cold/gaseous effects for items will be enabled.",
      "Default = true"
    })
    public boolean enableItemEffect = true;

    @Comment({
      "If true, hot/cold/gaseous effects for fluids will be enabled.",
      "Default = true"
    })
    public boolean enableFluidEffect = true;


    @Comment({
      "If true, hot items make the player yeet them.",
      "Default = true"
    })
    public boolean yeet = true;

    @Comment({
      "How hot a fluid should be to start burning the player (in Celsius).",
      "Default = 480"
    })
    public int hotFluid = 480;

    @Comment({
      "How cold a fluid should be to start adding effects the player (in Celsius).",
      "Default = 0"
    })
    public int coldFluid = 0;

    @Comment({
      "How hot an item should be to start burning the player (in Celsius).",
      "Default = 480"
    })
    public int hotItem = 480;

    @Comment({
      "How often will the durability will be decreased? In Ticks (20 ticks = 1 sec).",
      "Default = 20"
    })
    public int durabilityDecreasing = 20;

    @Comment({
      "Hot items that are included manually."
    })
    public String[] hotItemAdditions = new String[]{
      "minecraft:blaze_rod"
    };

    @Comment({
      "Cold items that are included manually."
    })
    public String[] coldItemAdditions = new String[]{
      "minecraft:ice",
      "minecraft:packed_ice"
    };

    @Comment({
      "Gaseous items that are included manually."
    })
    public String[] gaseousItemAdditions = new String[]{
      "mod_id:item"
    };

    @Comment({
      "Items that are excluded."
    })
    public String[] itemRemovals = new String[]{
      "immersiveengineering:drill",
      "immersiveengineering:chemthrower",
      "immersivepetroleum:fluid_diesel",
      "immersivepetroleum:fluid_gasoline"
    };


  }

  public static final class ConfigFeatureSizeAndWeight {

    @Comment({
      "If true, size and weight enable ",
      "Default = true"
    })
    public boolean enable = true;

    @RequiresMcRestart
    @Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
    public int veryHeavy = 1;

    @RequiresMcRestart
    @Comment("Stack size of Heavy items. Default = 4")
    public int heavy = 4;

    @RequiresMcRestart
    @Comment("Stack size of Medium items. Default = 16")
    public int medium = 16;

    @RequiresMcRestart
    @Comment("Stack size of Light items. Default = 32")
    public int light = 32;

    @RequiresMcRestart
    @Comment("Stack size of Very Light items. Default = 64")
    public int veryLight = 64;

  }
}
