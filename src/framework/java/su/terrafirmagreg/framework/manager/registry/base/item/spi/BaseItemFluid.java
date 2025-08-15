package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public abstract class BaseItemFluid extends ItemFluidContainer implements IItemEntry {

  protected final ItemSettings settings;
  protected final int capacity;

  public BaseItemFluid(int capacity, ItemSettings settings) {
    super(capacity);

    this.settings = settings;
    this.capacity = capacity;
  }

  public BaseItemFluid(int capacity) {
    this(capacity, ItemSettings.of());

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
  public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }
}
