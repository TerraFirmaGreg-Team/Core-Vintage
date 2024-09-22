package su.terrafirmagreg.api.base.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseItem extends Item implements IItemSettings {

  protected final Settings settings;

  public BaseItem() {
    this.settings = Settings.of();
  }

  public BaseItem(Settings settings) {
    this.settings = settings;
  }

  @Override
  public int getItemStackLimit() {
    return this.settings.getMaxCount();
  }

  @Override
  public int getMaxDamage() {
    return this.settings.getMaxDamage();
  }

  @Override
  public String getTranslationKey() {
    return this.settings.getTranslationKey() == null ? super.getTranslationKey() : "item." + this.settings.getTranslationKey();
  }

  /**
   * This should NOT be overridden except for VERY SPECIAL cases If an item needs to not stack, i.e. small vessels, override
   * {@link ICapabilitySize#canStack(ItemStack)} If an item needs a variable stack size, override {@link ICapabilitySize#getWeight(ItemStack)} /
   * {@link ICapabilitySize#getSize(ItemStack)} and return a different value to get a different stack size
   */
  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }

  public IRarity getForgeRarity(ItemStack stack) {
    return this.settings.getRarity();
  }

  @Override
  public Weight getWeight(ItemStack stack) {
    return this.settings.getWeight();
  }

  @Override
  public Size getSize(ItemStack stack) {
    return this.settings.getSize();
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return this.settings.isCanStack();
  }

}
