package su.terrafirmagreg.modules.core.capabilities.metal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderMetal implements ICapabilityMetal, ICapabilityProvider {

  private final Metal metal;
  private final int amount;
  private final boolean canMelt;

  public ProviderMetal() {
    this(Metal.UNKNOWN, 0, false);
  }

  public ProviderMetal(Metal metal, int amount, boolean canMelt) {
    this.metal = metal;
    this.amount = amount;
    this.canMelt = canMelt;
  }

  @Override
  public boolean canMelt(ItemStack stack) {
    return canMelt;
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack stack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack stack) {
    return amount;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityMetal.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }
}
