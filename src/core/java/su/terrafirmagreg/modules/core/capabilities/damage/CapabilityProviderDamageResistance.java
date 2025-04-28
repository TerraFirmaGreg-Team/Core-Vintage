package su.terrafirmagreg.modules.core.capabilities.damage;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class CapabilityProviderDamageResistance implements ICapabilityDamageResistance, ICapabilityProvider {

  private final float crushingModifier;
  private final float piercingModifier;
  private final float slashingModifier;

  public CapabilityProviderDamageResistance() {
    this(0, 0, 0);
  }

  public CapabilityProviderDamageResistance(float crushingModifier, float piercingModifier, float slashingModifier) {
    this.crushingModifier = crushingModifier;
    this.piercingModifier = piercingModifier;
    this.slashingModifier = slashingModifier;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityDamageResistance.CAPABILITY;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }
}
