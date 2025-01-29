package net.dries007.tfc.objects.items.ceramics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.ItemTFC;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemPottery extends ItemTFC {

  private final Size size;
  private final Weight weight;

  public ItemPottery() {
    this(Size.NORMAL, Weight.LIGHT);
  }

  public ItemPottery(Size size, Weight weight) {
    this.size = size;
    this.weight = weight;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return size;
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return weight;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
