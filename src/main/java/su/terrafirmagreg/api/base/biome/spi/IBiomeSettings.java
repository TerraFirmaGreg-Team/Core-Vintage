package su.terrafirmagreg.api.base.biome.spi;

import su.terrafirmagreg.api.registry.provider.IProviderAutoReg;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.world.biome.Biome;


import lombok.Getter;

import java.awt.Color;

public interface IBiomeSettings extends IProviderAutoReg {

  default String getRegistryKey() {
    return getSettings().registryKey;
  }

  Settings getSettings();

  @Getter
  class Settings {

    final String name;
    final String registryKey;

    String baseBiome;
    int guiColour = 0xffffff;
    Color debugColour = new Color(guiColour);
    int waterColor = 16777215;
    float baseHeight = 0.1F;
    float heightVariation = 0.2F;
    float temperature = 0.5F;
    float rainfall = 0.5F;

    boolean spawnBiome = false;
    boolean worldGen = false;
    boolean enableSnow = false;
    boolean enableRain = true;

    public Settings(String name) {
      this.name = ModUtils.name(name);
      this.registryKey = name;

    }

    public Settings baseBiome(Biome baseBiome) {
      this.baseBiome = baseBiome.getRegistryName().toString();
      return this;
    }

    public Settings baseBiome(String baseBiome) {
      this.baseBiome = baseBiome;
      return this;
    }

    public Settings guiColour(int guiColour) {
      this.guiColour = guiColour;
      return this;
    }

    public Settings waterColor(int waterColor) {
      this.waterColor = waterColor;
      return this;
    }

    public Settings baseHeight(float baseHeight) {
      this.baseHeight = baseHeight;
      return this;
    }

    public Settings heightVariation(float heightVariation) {
      this.heightVariation = heightVariation;
      return this;
    }

    public Settings temperature(float temperature) {
      this.temperature = temperature;
      return this;
    }

    public Settings rainfall(float rainfall) {
      this.rainfall = rainfall;
      return this;
    }

    public Settings spawnBiome() {
      this.spawnBiome = true;
      return this;
    }

    public Settings enableWorldGen() {
      this.worldGen = true;
      return this;
    }

    public Settings enableSnow() {
      this.enableSnow = true;
      return this;
    }

    public Settings disabledRain() {
      this.enableRain = false;
      return this;
    }

    public Biome.BiomeProperties build() {
      var biome = new Biome.BiomeProperties(this.name);

      biome.setBaseBiome(this.baseBiome);
      biome.setWaterColor(this.waterColor);
      biome.setBaseHeight(this.baseHeight);
      biome.setHeightVariation(this.heightVariation);
      biome.setTemperature(this.temperature);
      biome.setRainfall(this.rainfall);
      if (!enableRain) {
        biome.setRainDisabled();
      }
      if (enableSnow) {
        biome.setSnowEnabled();
      }
      return biome;
    }
  }
}
