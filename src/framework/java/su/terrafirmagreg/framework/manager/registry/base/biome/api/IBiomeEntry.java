package su.terrafirmagreg.framework.manager.registry.base.biome.api;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.biome.api.IBiomeEntry.BiomeSettings;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import com.google.common.collect.Lists;

import lombok.Getter;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public interface IBiomeEntry extends IRegistryEntry<BiomeSettings, Biome> {

  @Override
  default void postRegister() {
    var settings = getSettings();

    BiomeUtils.addTypes(asEntry(), settings.getTypes());
    BiomeUtils.addSpawn(asEntry(), settings.isSpawnBiome());
    BiomeUtils.addWorldGen(asEntry(), settings.isWorldGen());
  }

  @Getter
  class BiomeSettings extends RegistrySettings<BiomeSettings> {

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

    protected BiomeSettings(String name) {
      this.name = ModUtils.name(name);

    }

    public static BiomeSettings of(String name) {
      return new BiomeSettings(name);
    }

    public BiomeSettings baseBiome(Biome baseBiome) {
      this.baseBiome = baseBiome.getRegistryName().toString();
      return this.self();
    }

    public BiomeSettings baseBiome(String baseBiome) {
      this.baseBiome = baseBiome;
      return this.self();
    }

    public BiomeSettings biomeWeight(int biomeWeight) {
      this.biomeWeight = biomeWeight;
      return this.self();
    }

    public BiomeSettings addType(BiomeDictionary.Type... types) {
      this.types.addAll(Arrays.asList(types));
      return this.self();
    }

    public BiomeSettings guiColour(int guiColour) {
      this.guiColour = guiColour;
      return this.self();
    }

    public BiomeSettings waterColor(int waterColor) {
      this.waterColor = waterColor;
      return this.self();
    }

    public BiomeSettings baseHeight(float baseHeight) {
      this.baseHeight = baseHeight;
      return this.self();
    }

    public BiomeSettings heightVariation(float heightVariation) {
      this.heightVariation = heightVariation;
      return this.self();
    }

    public BiomeSettings temperature(float temperature) {
      this.temperature = temperature;
      return this.self();
    }

    public BiomeSettings rainfall(float rainfall) {
      this.rainfall = rainfall;
      return this.self();
    }

    public BiomeSettings spawnBiome() {
      this.spawnBiome = true;
      return this.self();
    }

    public BiomeSettings enableWorldGen() {
      this.worldGen = true;
      return this.self();
    }

    public BiomeSettings enableSnow() {
      this.enableSnow = true;
      return this.self();
    }

    public BiomeSettings disabledRain() {
      this.enableRain = false;
      return this.self();
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
