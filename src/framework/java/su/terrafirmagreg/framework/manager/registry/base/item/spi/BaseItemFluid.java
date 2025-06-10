package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseItemFluid extends ItemFluidContainer implements IItemEntry {

  protected final Settings settings;
  protected final int capacity;

  public BaseItemFluid(int capacity) {
    super(capacity);

    this.settings = Settings.of();
    this.capacity = capacity;
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
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }
}
