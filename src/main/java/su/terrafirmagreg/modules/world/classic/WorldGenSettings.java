package su.terrafirmagreg.modules.world.classic;

import su.terrafirmagreg.api.data.Reference;

import com.google.common.base.Strings;

@SuppressWarnings("WeakerAccess")
public class WorldGenSettings {

  public final int spawnFuzz; //todo: remove, vanilla does it with a gamerule
  public final boolean flatBedrock;
  public final int rockLayerSize;
  public final int ravineRarity;
  public final int ravineHeight;
  public final int ravineVariability;
  public final int surfaceRavineRarity;
  public final int surfaceRavineHeight;
  public final int surfaceRavineVariability;
  public final int riverRavineRarity;
  public final int lavaFissureRarity = 25; //todo
  public final int waterFissureRarity = 90; //todo
  public final int lavaFissureClusterRarity = 400; //todo
  public final int waterFissureClusterRarity = 225; //todo
  public final int largeRockRarity = 20; //todo

  public WorldGenSettings(WorldGenSettingsBuilder builder) {
    spawnFuzz = builder.spawnFuzz;
    flatBedrock = builder.flatBedrock;
    rockLayerSize = builder.rockLayerSize;
    ravineRarity = builder.ravineRarity;
    ravineHeight = builder.ravineHeight;
    ravineVariability = builder.ravineVariability;
    surfaceRavineRarity = builder.surfaceRavineRarity;
    surfaceRavineHeight = builder.surfaceRavineHeight;
    surfaceRavineVariability = builder.surfaceRavineVariability;
    riverRavineRarity = builder.riverRavineRarity;
  }

  public static WorldGenSettingsBuilder fromString(String options) {
    if (Strings.isNullOrEmpty(options)) {
      return new WorldGenSettingsBuilder();
    }
    return Reference.GSON.fromJson(options, WorldGenSettingsBuilder.class);
  }

  @Override
  public String toString() {
    return Reference.GSON.toJson(this);
  }

  public static class WorldGenSettingsBuilder {

    public int spawnFuzz = 250;
    public boolean flatBedrock = false;

    public int rockLayerSize = 5;

    public int ravineRarity = 100;
    public int ravineHeight = 20;
    public int ravineVariability = 50;

    public int surfaceRavineRarity = 100;
    public int surfaceRavineHeight = 125;
    public int surfaceRavineVariability = 30;

    public int riverRavineRarity = 400;

    public boolean isDefault() {
      return spawnFuzz == 250 &&
             !flatBedrock &&
             rockLayerSize == 5 &&
             ravineRarity == 100 &&
             ravineHeight == 20 &&
             ravineVariability == 50 &&
             surfaceRavineRarity == 100 &&
             surfaceRavineHeight == 125 &&
             surfaceRavineVariability == 30 &&
             riverRavineRarity == 400;
    }

    public WorldGenSettings build() {
      return new WorldGenSettings(this);
    }

    @Override
    public String toString() {
      return Reference.GSON.toJson(this);
    }
  }
}
