package su.terrafirmagreg.framework.manager.content.base.enchantment.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.enchantment.api.IEnchantmentEntry;

import net.minecraft.enchantment.Enchantment;

import lombok.Getter;

@Getter
public abstract class BaseEnchantment extends Enchantment implements IEnchantmentEntry {

  protected final EnchantmentSettings settings;

  public BaseEnchantment(EnchantmentSettings settings) {
    super(settings.getRarity(), settings.getEnchantmentType(), settings.getSlots());

    this.settings = settings;
  }

  @Override
  public String getName() {

    return ModUtils.localize("enchantment", this.getRegistryName());
  }
}
