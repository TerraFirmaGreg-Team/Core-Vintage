package su.terrafirmagreg.framework.manager.content.base.item.spi;


import su.terrafirmagreg.framework.manager.content.base.item.api.IItemEntry;

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
  public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }
}
