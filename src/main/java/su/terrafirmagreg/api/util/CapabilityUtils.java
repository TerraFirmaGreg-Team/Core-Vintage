package su.terrafirmagreg.api.util;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
@SuppressWarnings("unused")
public final class CapabilityUtils {

  @Nullable
  public static <T> T get(ICapabilityProvider provider, Capability<T> capability) {
    return get(provider, capability, null);
  }

  @Nullable
  public static <T> T get(ICapabilityProvider provider, Capability<T> capability, @Nullable EnumFacing facing) {
    return provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
  }

  public static <T> Optional<T> getOptional(ICapabilityProvider provider, Capability<T> capability) {
    return Optional.ofNullable(get(provider, capability));
  }
}
