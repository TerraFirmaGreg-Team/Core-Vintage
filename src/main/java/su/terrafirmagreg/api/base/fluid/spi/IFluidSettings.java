package su.terrafirmagreg.api.base.fluid.spi;

import su.terrafirmagreg.api.registry.provider.IProviderAutoReg;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import net.dries007.tfc.objects.fluids.properties.FluidProperty;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface IFluidSettings extends IProviderAutoReg {

  Settings getSettings();

  @Override
  default String getRegistryKey() {

    return this.getSettings().getRegistryKey();
  }

  default Fluid getFluid() {

    return (Fluid) this;
  }

  @Getter
  class Settings {

    final Map<FluidProperty<?>, Object> properties = new HashMap<>();

    final String registryKey;

    String translationKey;

    int color = 0xffffff;

    ResourceLocation still = ModUtils.resource("blocks/core/fluid/still");
    ResourceLocation flowing = ModUtils.resource("blocks/core/fluid/flow");
    ResourceLocation overlay = null;

    Function<Fluid, Block> fluidBlock;

    protected Settings(String registryKey) {
      this.registryKey = registryKey;
    }

    public static Settings of(String registryKey) {
      return new Settings(registryKey);
    }

    public Settings color(int color) {
      this.color = color;
      return this;
    }

    public Settings translationKey(String translationKey) {
      this.translationKey = translationKey;
      return this;
    }

    public Settings still(ResourceLocation still) {
      this.still = still;
      return this;
    }

    public Settings flowing(ResourceLocation flowing) {
      this.flowing = flowing;
      return this;
    }

    public Settings overlay(ResourceLocation overlay) {
      this.overlay = overlay;
      return this;
    }

    public <T> Settings property(FluidProperty<T> propertyType, T propertyValue) {
      properties.put(propertyType, propertyValue);
      return this;
    }

    public Settings block(Function<Fluid, Block> blockFactory) {
      this.fluidBlock = blockFactory;
      return this;
    }

  }
}
