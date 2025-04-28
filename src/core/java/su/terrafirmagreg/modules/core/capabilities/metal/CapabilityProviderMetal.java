package su.terrafirmagreg.modules.core.capabilities.metal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CapabilityProviderMetal implements ICapabilityMetal, ICapabilityProvider {

  private final Metal metal;
  private final int amount;
  private final boolean canMelt;

  public CapabilityProviderMetal() {
    this(Metal.UNKNOWN, 0, false);
  }

  public CapabilityProviderMetal(Metal metal, int amount, boolean canMelt) {
    this.metal = metal;
    this.amount = amount;
    this.canMelt = canMelt;
  }

  public static CapabilityProviderMetal of(Metal metal, int amount, boolean canMelt) {
    return new CapabilityProviderMetal(metal, amount, canMelt);
  }

  public static CapabilityProviderMetal of(Metal metal, int amount) {
    return new CapabilityProviderMetal(metal, amount, true);
  }

  public static CapabilityProviderMetal of(Metal metal) {
    return new CapabilityProviderMetal(metal, 0, true);
  }

  public static CapabilityProviderMetal of() {
    return new CapabilityProviderMetal();
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
