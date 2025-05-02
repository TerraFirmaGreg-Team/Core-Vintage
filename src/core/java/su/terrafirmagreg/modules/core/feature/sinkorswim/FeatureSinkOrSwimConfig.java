package su.terrafirmagreg.modules.core.feature.sinkorswim;

import net.minecraftforge.common.config.Config.Comment;

public class FeatureSinkOrSwimConfig {


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
