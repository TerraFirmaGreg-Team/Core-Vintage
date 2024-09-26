package su.terrafirmagreg.api.base.item.spi;

import su.terrafirmagreg.api.registry.provider.IProviderAutoReg;
import su.terrafirmagreg.api.registry.provider.IProviderModel;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.data.ItemRarity;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface IItemSettings extends IProviderAutoReg, IProviderModel {

  default int getItemStackLimit(@NotNull ItemStack stack) {
    return this.getStackSize(stack);
  }

  // Override Item methods

  default IRarity getForgeRarity(ItemStack stack) {
    return this.getSettings().getRarity();
  }

  Settings getSettings();

  default String getTranslationKey() {
    return "item." + this.getSettings().getTranslationKey();
  }

  default int getMaxDamage() {
    return this.getSettings().getMaxDamage();
  }

  default int getItemStackLimit() {
    return this.getSettings().getMaxCount();
  }

  // Override IOreDictProvider methods

  default void onRegisterOreDict() {
    if (!this.getSettings().getOreDict().isEmpty()) {
      this.getSettings().getOreDict().forEach(ore -> OreDictUtils.register(getItem(), ore));
      this.getSettings().getOreDict().clear();
    }
  }

  default Item getItem() {
    return (Item) this;
  }

  // Override IAutoRegProvider methods

  @Override
  default ResourceLocation getResourceLocation() {
    if (this.getSettings().getResource() != null) {
      return this.getSettings().getResource();
    }

    return null;
  }

  // Override IProviderModel methods

  default String getRegistryKey() {
    return this.getSettings().getRegistryKey();
  }

  // Override IItemSize methods

  default Weight getWeight(ItemStack stack) {
    return this.getSettings().getWeight();
  }

  default Size getSize(ItemStack stack) {
    return this.getSettings().getSize();
  }

  default boolean canStack(ItemStack stack) {
    return this.getSettings().isCanStack();
  }

  @Getter
  class Settings {

    final List<Object[]> oreDict = new ArrayList<>();
    final Set<CreativeTabs> tabs = new HashSet<>();

    String registryKey;
    String translationKey;

    ResourceLocation resource;

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

    public Settings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this;
    }

    public Settings oreDict(Object... oreDict) {
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
