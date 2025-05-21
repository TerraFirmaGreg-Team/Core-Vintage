package su.terrafirmagreg.modules.core.feature.pull.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.astikorcarts.entity.AbstractDrawn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CapabilityProviderPull implements ICapabilityPull, ICapabilityProvider {

  private AbstractDrawn drawn;

  public CapabilityProviderPull() {
    this(null);
  }

  public CapabilityProviderPull(AbstractDrawn drawn) {
    this.drawn = drawn;
  }

  @Override
  public AbstractDrawn getDrawn() {
    return this.drawn;
  }

  @Override
  public void setDrawn(AbstractDrawn drawnIn) {
    this.drawn = drawnIn;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityPull.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }
}
