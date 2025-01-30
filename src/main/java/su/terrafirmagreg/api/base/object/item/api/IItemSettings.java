package su.terrafirmagreg.api.base.object.item.api;


import su.terrafirmagreg.api.base.capability.spi.CombinedCapabilityProvider;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemCapability;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface IItemSettings extends IBaseSettings<Settings> {

  default ICapabilityProvider definition$initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    ArrayList<ICapabilityProvider> providers = new ArrayList<>();
    for (IProviderItemCapability itemCapability : getSettings().getCapability()) {
      providers.add(itemCapability.createProvider(stack));
    }
    return new CombinedCapabilityProvider(providers);
  }

  @Getter
  class Settings extends BaseSettings<Settings> {

    final ArrayList<Object[]> oreDict = new ArrayList<>();
    final ArrayList<IProviderItemCapability> capability = new ArrayList<>();
    final Set<CreativeTabs> groups = new ObjectOpenHashSet<>();

    ResourceLocation resource;

    boolean isFireResistant;
    int maxDamage = 0;
    int maxCount = 64;

    IRarity rarity = EnumRarity.COMMON;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings maxDamage(int maxDamage) {
      if (maxDamage < 1) {throw new IllegalArgumentException("Maximum stack size must be greater than zero!");}
      if (maxDamage > 1 && this.maxCount != 0) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxDamage = maxDamage;
      return this;
    }

    public Settings maxCount(int maxCount) {
      if (this.maxDamage != 64 && this.maxDamage > 1) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxCount = maxCount;
      this.maxDamage = 1;
      return this;
    }

    public Settings group(List<CreativeTabs> groupList) {
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

    public Settings capability(List<IProviderItemCapability> providers) {
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
  }
}
