package su.terrafirmagreg.api.spi.biome;

import su.terrafirmagreg.api.registry.provider.IAutoRegProvider;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.world.biome.Biome;


import lombok.Getter;

import java.awt.*;

public interface IBiomeSettings extends IAutoRegProvider {

    Settings getSettings();

    default String getRegistryKey() {
        return getSettings().regKey;
    }

    @Getter
    class Settings {

        protected final String name;
        protected final String regKey;

        protected String baseBiome;
        protected int guiColour = 0xffffff;
        protected Color debugColour = new Color(guiColour);
        protected int waterColor = 16777215;
        protected float baseHeight = 0.1F;
        protected float heightVariation = 0.2F;
        protected float temperature = 0.5F;
        protected float rainfall = 0.5F;

        protected boolean spawnBiome = false;
        protected boolean worldGen = false;
        protected boolean enableSnow = false;
        protected boolean enableRain = true;

        public Settings(String name) {
            this.name = ModUtils.name(name);
            this.regKey = name;

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
            if (!enableRain) biome.setRainDisabled();
            if (enableSnow) biome.setSnowEnabled();
            return biome;
        }
    }
}
