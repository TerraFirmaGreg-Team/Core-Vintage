package su.terrafirmagreg.modules.soil.config;

import net.minecraftforge.common.config.Config;

public final class ConfigSoilBlock {


  public final GrassPath GRASS_PATH = new GrassPath();

  public static final class GrassPath {

    @Config.Name("Destroy Vegetation")
    @Config.Comment("When enabled, vegetation like tall grass or loose rocks gets destroyed")
    public boolean DESTROY_VEGETATION = true;

    @Config.Name("All Entities")
    @Config.Comment("When enabled, all entities create paths (performance intensive)")
    public boolean ALL_ENTITIES = false;

    @Config.Name("Grass -> Dirt")
    @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
    @Config.RangeDouble(min = 0.0, max = 1000.0)
    public double PLAYER_GRASS_TO_DIRT = 30.0;

    @Config.Name("Dirt -> Path")
    @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
    @Config.RangeDouble(min = 0.0, max = 1000.0)
    public double PLAYER_DIRT_TO_PATH = 10.0;

    @Config.Name("Grass -> Dirt")
    @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
    @Config.RangeDouble(min = 0.0, max = 1000.0)
    public double MOB_GRASS_TO_DIRT = 15.0;

    @Config.Name("Dirt -> Path")
    @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
    @Config.RangeDouble(min = 0.0, max = 1000.0)
    public double MOB_DIRT_TO_PATH = 5.0;
  }

}
