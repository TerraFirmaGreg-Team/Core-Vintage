package su.terrafirmagreg.framework.manager.registry.base.item.api;


import su.terrafirmagreg.api.capability.spi.CombinedCapabilityProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry.ItemSettings;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemCapability;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface IItemEntry extends IRegistryEntry<ItemSettings, Item> {

  default ICapabilityProvider settings$initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    ArrayList<ICapabilityProvider> providers = new ArrayList<>();
    for (IProviderItemCapability itemCapability : getSettings().getCapability()) {
      providers.add(itemCapability.createProvider(stack));
    }
    addCapabilities(providers, stack, nbt);
    return new CombinedCapabilityProvider(providers);
  }

  default ArrayList<ICapabilityProvider> addCapabilities(ArrayList<ICapabilityProvider> providers, @NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {

    return providers;
  }

  @Override
  default void preRegister() {
    var settings = getSettings();
    settings.addOreDict(settings.getRegistryKey());
    asEntry()
      .setHasSubtypes(settings.isHasSubtypes())
      .setMaxDamage(settings.getMaxDamage())
      .setMaxStackSize(settings.getMaxStackSize());
  }

  @Override
  default void postRegister() {
    var settings = getSettings();

    OreDictUtils.addOreDict(asEntry(), settings.getOreDict());
    ModelUtils.addModel(asEntry());
  }


  @Getter
  class ItemSettings extends RegistrySettings<ItemSettings> {

    final List<Object[]> oreDict;
    final List<IProviderItemCapability> capability;


    ResourceLocation resource = null;
    CreativeTabs group;
    IRarity rarity;

    boolean isFireResistant;
    boolean hasSubtypes;
    int maxDamage;
    int maxStackSize;


    protected ItemSettings() {

      this.oreDict = new ObjectArrayList<>();
      this.capability = new ObjectArrayList<>();

      this.rarity = EnumRarity.COMMON;
      this.maxStackSize = 64;

    }

    public static ItemSettings of() {
      return new ItemSettings();
    }

    public static ItemSettings of(Block block) {
      ItemSettings settingsItem = ItemSettings.of();
      if (block instanceof IBlockEntry settingsBlock) {
        var settings = settingsBlock.getSettings();
        settingsItem
          .registryKey(settings.getRegistryKey())
          .customResource(settings.getResource())
          .rarity(settings.getRarity())
          .group(settings.getGroup())
          .addOreDict(settings.getOreDict())
          .maxStackSize(settings.isNonCanStack() ? 1 : 64)
          .capability(settings.getCapability());
      }

      return settingsItem;
    }

    public ItemSettings maxDamage(int durability) {
      if (this.maxStackSize != 64 && this.maxStackSize > 1) {throw new RuntimeException("An item cannot have durability and be stackable!");}
      this.maxDamage = durability;
      this.maxStackSize = 1;

      return this.self();
    }

    public ItemSettings maxStackSize(int maxStackSize) {
      if (maxStackSize < 1) {throw new IllegalArgumentException("Maximum stack size must be greater than zero!");}
      if (maxStackSize > 1 && this.maxDamage != 0) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxStackSize = maxStackSize;
      return this.self();
    }

    public ItemSettings group(CreativeTabs group) {
      this.group = group;
      return this.self();
    }

    public ItemSettings rarity(IRarity rarity) {
      this.rarity = rarity;
      return this.self();
    }

    public ItemSettings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this.self();
    }

    public ItemSettings customResource(ResourceLocation resource) {
      this.resource = resource;
      return this.self();
    }

    public ItemSettings removeOreDictAll() {
      this.oreDict.clear();
      return this.self();
    }

    public ItemSettings removeOreDict(Object... oreDict) {
      this.oreDict.remove(oreDict);
      return this.self();
    }

    public ItemSettings addOreDict(Supplier<Boolean> supplier, Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this.self();
    }

    public ItemSettings addOreDict(List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this.self();
    }

    public ItemSettings addOreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this.self();
    }

    public ItemSettings capability(List<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this.self();
    }

    public ItemSettings capability(IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
      return this.self();
    }

    public ItemSettings fireResistant() {
      this.isFireResistant = true;
      return this.self();
    }

    public ItemSettings hasSubtypes() {
      this.hasSubtypes = true;
      return this.self();
    }
  }
}
