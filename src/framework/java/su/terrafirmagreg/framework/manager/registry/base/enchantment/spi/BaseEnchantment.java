package su.terrafirmagreg.framework.manager.registry.base.enchantment.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.enchantment.api.IEnchantmentEntry;

import net.minecraft.enchantment.Enchantment;

import lombok.Getter;

@Getter
public abstract class BaseEnchantment extends Enchantment implements IEnchantmentEntry {

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
