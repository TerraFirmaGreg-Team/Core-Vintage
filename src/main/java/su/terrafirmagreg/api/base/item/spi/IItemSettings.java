package su.terrafirmagreg.api.base.item.spi;

import su.terrafirmagreg.api.registry.provider.IProviderAutoReg;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface IItemSettings extends IProviderAutoReg, ICapabilitySize {

  Settings getSettings();

  // Override Item methods

  default int getItemStackLimit(@NotNull ItemStack stack) {
    return getStackSize(stack);
  }

  default IRarity getForgeRarity(ItemStack stack) {
    return getSettings().getRarity();
  }

  default String getTranslationKey() {
    return "item." + getSettings().getTranslationKey();
  }

  default int getMaxDamage() {
    return getSettings().getMaxDamage();
  }

  default int getItemStackLimit() {
    return getSettings().getMaxCount();
  }

  // Override IOreDictProvider methods

  default void onRegisterOreDict() {
    if (!getSettings().getOreDict().isEmpty()) {
      getSettings().getOreDict().forEach(ore -> OreDictUtils.register(getItem(), ore));
      getSettings().getOreDict().clear();
    }
  }

  default Item getItem() {
    return (Item) this;
  }

  // Override IAutoRegProvider methods

  default String getRegistryKey() {
    return getSettings().getRegistryKey();
  }

  // Override IItemSize methods

  default Size getSize(ItemStack stack) {
    return getSettings().getSize();
  }

  default Weight getWeight(ItemStack stack) {
    return getSettings().getWeight();
  }

  default boolean canStack(ItemStack stack) {
    return getSettings().isCanStack();
  }

  @Getter
  class Settings {

    final List<Object[]> oreDict = new ArrayList<>();
    final Set<CreativeTabs> tabs = new HashSet<>();

    String registryKey;
    String translationKey;

    Size size = Size.SMALL;
    Weight weight = Weight.LIGHT;
    boolean canStack = true;
    int maxCount = 64;
    int maxDamage;
    IRarity rarity = ItemRarity.COMMON.getRarity();

    private Settings() {

    }

    public static Settings of() {
      return new Settings();
    }

    public Settings registryKey(String registryKey) {
      this.registryKey = registryKey;
      return this;
    }

    public Settings maxCount(int maxCount) {
      this.maxCount = maxCount;
      return this;
    }

    public Settings maxDamage(int damage) {
      this.maxDamage = damage;
      return this;
    }

    public Settings tab(CreativeTabs tab) {
      this.tabs.add(tab);
      return this;
    }

    public Settings rarity(IRarity rarity) {
      this.rarity = rarity;
      return this;
    }

    public Settings rarity(ItemRarity rarity) {
      this.rarity = rarity.getRarity();
      return this;
    }

    public Settings translationKey(String translationKey) {
      this.translationKey = translationKey;
      return this;
    }

    public Settings addOreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this;
    }

    public Settings weight(Weight weight) {
      this.weight = weight;
      return this;
    }

    public Settings size(Size size) {
      this.size = size;
      return this;
    }

    public Settings notCanStack() {
      this.canStack = false;
      return this;
    }
  }
}
