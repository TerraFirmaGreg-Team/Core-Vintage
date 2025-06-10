package su.terrafirmagreg.modules.core.feature.hotornot;

import net.minecraftforge.common.config.Config.Comment;

public class FeatureHotOrNotConfig {

  @Comment({
    "If true, hot or not enable ",
    "Default = true"
  })
  public boolean enabled = true;

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
