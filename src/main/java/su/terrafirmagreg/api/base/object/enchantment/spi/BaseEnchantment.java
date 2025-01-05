package su.terrafirmagreg.api.base.object.enchantment.spi;

import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.enchantment.Enchantment;

import lombok.Getter;

@Getter
public abstract class BaseEnchantment extends Enchantment implements IEnchantmentSettings {

  protected final Settings settings;

  public BaseEnchantment(Settings settings) {
    super(settings.getRarity(), settings.getType(), settings.getSlots());

    this.settings = settings;
  }

  @Override
  public String getName() {

    return ModUtils.localize("enchantment", this.getRegistryName());
  }
}
