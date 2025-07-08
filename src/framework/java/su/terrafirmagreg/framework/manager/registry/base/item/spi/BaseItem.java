package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class BaseItem extends Item implements IItemEntry {

  protected final Settings settings;

  public BaseItem() {
    this.settings = Settings.of();

  }

  @Override
  public IRarity getForgeRarity(ItemStack stack) {
    return settings.getRarity();
  }


  @Override
  public String getTranslationKey(ItemStack stack) {
    return this.getTranslationKey();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.ITEM, this.getRegistryName());
  }

  @Override
  public ArrayList<ICapabilityProvider> addCapabilities(ArrayList<ICapabilityProvider> providers, @NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {

    return providers;
  }


  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {

    if (getSettings().getCapability().isEmpty()) {
      return super.initCapabilities(stack, nbt);
    }
    return settings$initCapabilities(stack, nbt) == null ? super.initCapabilities(stack, nbt) : null;
  }
}
