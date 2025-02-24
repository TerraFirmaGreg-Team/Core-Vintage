package su.terrafirmagreg.api.base.object.item.api;


import su.terrafirmagreg.api.base.capability.spi.CombinedCapabilityProvider;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemCapability;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface IItemSettings extends IBaseSettings<Settings> {

  default Item asItem() {
    return (Item) this;
  }

  default ICapabilityProvider settings$initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    ArrayList<ICapabilityProvider> providers = new ArrayList<>();
    for (IProviderItemCapability itemCapability : getSettings().getCapability()) {
      providers.add(itemCapability.createProvider(stack));
    }
    return new CombinedCapabilityProvider(providers);
  }

  @Getter
  class Settings extends BaseSettings<Settings> {

    final List<Object[]> oreDict;
    final Set<IProviderItemCapability> capability;
    final Set<CreativeTabs> groups;

    ResourceLocation resource;

    boolean isFireResistant;
    int maxDamage;
    int maxStackSize;

    IRarity rarity;

    protected Settings() {

      this.groups = new HashSet<>();
      this.capability = new HashSet<>();
      this.oreDict = new ArrayList<>();

      this.rarity = EnumRarity.COMMON;
      this.maxStackSize = 64;

    }

    public static Settings of() {
      return new Settings();
    }

    public static Settings of(Block block) {
      Settings settingsItem = Settings.of();
      if (block instanceof IBlockSettings settingsBlock) {
        var settings = settingsBlock.getSettings();
        settingsItem
          .registryKey(settings.getRegistryKey())
          .rarity(settings.getRarity())
          .group(settings.getGroups())
          .oreDict(settings.getOreDict())
          .capability(settings.getCapability());
      }

      return settingsItem;
    }

    public Settings registryKey(String registryKey) {
      super.registryKey(registryKey);
      this.oreDict.add(new Object[]{registryKey});
      return this;
    }

    public Settings maxDamage(int durability) {
      if (this.maxStackSize != 64 && this.maxStackSize > 1) {throw new RuntimeException("An item cannot have durability and be stackable!");}
      this.maxDamage = durability;
      this.maxStackSize = 1;

      return this;
    }

    public Settings maxStackSize(int maxStackSize) {
      if (maxStackSize < 1) {throw new IllegalArgumentException("Maximum stack size must be greater than zero!");}
      if (maxStackSize > 1 && this.maxDamage != 0) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxStackSize = maxStackSize;
      return this;
    }

    public Settings group(Set<CreativeTabs> groupList) {
      groupList.forEach(this::group);
      return this;
    }

    public Settings group(CreativeTabs... group) {
      this.groups.addAll(Arrays.asList(group));
      return this;
    }

    public Settings group(CreativeTabs group) {
      if (group != null) {
        this.groups.add(group);
      }
      return this;
    }

    public Settings rarity(IRarity rarity) {
      this.rarity = rarity;
      return this;
    }

    public Settings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this;
    }

    public Settings oreDict(Supplier<Boolean> supplier, Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this;
    }

    public Settings oreDict(List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this;
    }

    public Settings oreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this;
    }

    public Settings capability(Set<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this;
    }

    public Settings capability(IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
      return this;
    }

    public Settings fireResistant() {
      this.isFireResistant = true;
      return this;
    }

    public Settings weight(Weight weight) {
      return this;
    }

    public Settings size(Size size) {
      return this;
    }

    public Settings notCanStack() {
      return this;
    }
  }
}
