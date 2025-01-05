package su.terrafirmagreg.api.base.object.biome.api;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import com.google.common.collect.Lists;

import lombok.Getter;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public interface IBiomeSettings extends IBaseSettings<Settings> {

  @Getter
  class Settings extends BaseSettings<Settings> {

    final List<BiomeDictionary.Type> types = Lists.newArrayList();

    final String name;

    String baseBiome;

    int guiColour = 0xffffff;
    int waterColor = 16777215;
    int biomeWeight = 0;

    float baseHeight = 0.1F;
    float heightVariation = 0.2F;
    float temperature = 0.5F;
    float rainfall = 0.5F;

    boolean spawnBiome = false;
    boolean worldGen = false;
    boolean enableSnow = false;
    boolean enableRain = true;
    boolean generateVillages = true;

    Color debugColour = new Color(guiColour);

    protected Settings(String name) {
      this.name = ModUtils.name(name);
    }

    public static Settings of(String name) {
      return new Settings(name);
    }

    public Settings baseBiome(Biome baseBiome) {
      this.baseBiome = baseBiome.getRegistryName().toString();
      return this;
    }

    public Settings baseBiome(String baseBiome) {
      this.baseBiome = baseBiome;
      return this;
    }

    public Settings biomeWeight(int biomeWeight) {
      this.biomeWeight = biomeWeight;
      return this;
    }

    public Settings addType(BiomeDictionary.Type... types) {
      this.types.addAll(Arrays.asList(types));
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
