package su.terrafirmagreg.api.base.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;

import net.minecraft.item.ItemFood;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import lombok.Getter;

@Getter
public class BaseItemFood extends ItemFood implements IItemSettings {

  protected final Settings settings;

  public BaseItemFood(int amount, float saturation, boolean isWolfFood) {
    super(amount, saturation, isWolfFood);

    this.settings = Settings.of();

    getSettings()
      .oreDict();
  }

  @Getter
  protected static class Settings extends IItemSettings.Settings {


    protected ICapabilityProvider capability;

    public static Settings of() {
      return new Settings();
    }

    public Settings capability(ICapabilityProvider capability) {
      this.capability = capability;
      return this;
    }
  }
}
