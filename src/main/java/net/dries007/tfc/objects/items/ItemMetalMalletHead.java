package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemMetalMalletHead extends ItemTFC implements ICapabilityMetal {

  private final Metal metal;
  private final int smeltAmount;

  public ItemMetalMalletHead(Metal metal) {
    this.metal = metal;
    this.smeltAmount = 100;
  }

  public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
    return Weight.HEAVY;
  }

  public @NotNull Size getSize(@NotNull ItemStack itemStack) {
    return Size.LARGE;
  }

  @Override
  public boolean canMelt(ItemStack itemStack) {
    return true;
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack itemStack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack itemStack) {
    return smeltAmount;
  }

  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }
}
