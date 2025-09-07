package su.terrafirmagreg.framework.manager.content.base.biome.spi;

import su.terrafirmagreg.framework.manager.content.base.biome.api.IBiomeEntry;

import net.minecraft.world.biome.Biome;

import lombok.Getter;

import java.util.Random;


@Getter
public abstract class BaseBiome extends Biome implements IBiomeEntry {

  protected final BiomeSettings settings;

  public BaseBiome(BiomeSettings settings) {
    super(settings.build());

    this.settings = settings;
  }

  @Override
  public boolean ignorePlayerSpawnSuitability() {

    return settings.isSpawnBiome();
  }

  public BaseBiome mutate(Random rand) {

    return this;
  }
}
