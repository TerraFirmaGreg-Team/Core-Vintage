package su.terrafirmagreg.modules.core.feature.damageresistance;

import net.minecraftforge.common.config.Config;

public class FeatureDamageResistanceConfig {


  @Config.Comment("Damage Source Types that will default to Slashing damage.")
  public String[] slashingSources = new String[]{

  };

  @Config.Comment("Damage Source Types that will default to Piercing damage.")
  public String[] piercingSources = new String[]{
    "arrow",
    "cactus",
    "thorns"
  };

  @Config.Comment("Damage Source Types that will default to Crushing damage.")
  public String[] crushingSources = new String[]{
    "anvil",
    "falling_block"
  };

  @Config.Comment("Damage Source Entities that will default to Slashing damage.")
  public String[] slashingEntities = new String[]{
    "minecraft:wither_skeleton",
    "minecraft:vex",
    "minecraft:vindication_illager",
    "minecraft:zombie_pigman",
    "minecraft:wolf",
    "minecraft:polar_bear"
  };

  @Config.Comment("Damage Source Entities that will default to Piercing damage.")
  public String[] piercingEntities = new String[]{
    "minecraft:stray",
    "minecraft:skeleton"
  };

  @Config.Comment("Damage Source Entities that will default to Crushing damage.")
  public String[] crushingEntities = new String[]{
    "minecraft:husk",
    "minecraft:skeleton_horse",
    "minecraft:zombie_horse",
    "minecraft:spider",
    "minecraft:giant",
    "minecraft:zombie",
    "minecraft:slime",
    "minecraft:cave_spider",
    "minecraft:silverfish",
    "minecraft:villager_golem",
    "minecraft:zombie_villager"
  };
}
