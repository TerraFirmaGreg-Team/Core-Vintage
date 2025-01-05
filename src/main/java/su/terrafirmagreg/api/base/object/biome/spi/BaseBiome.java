package su.terrafirmagreg.api.base.object.biome.spi;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings;

import net.minecraft.world.biome.Biome;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BaseBiome extends Biome implements IBiomeSettings {

  protected final Settings settings;

  public BaseBiome(Settings settings) {
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
