package su.terrafirmagreg.modules.core.capabilities.size;

import su.terrafirmagreg.framework.registry.api.provider.IProviderItemCapability;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class CapabilityProviderSize implements ICapabilitySize, ICapabilityProvider, IProviderItemCapability {

  private static final EnumMap<Size, EnumMap<Weight, CapabilityProviderSize[]>> CACHE = new EnumMap<>(Size.class);
  private final Size size;
  private final Weight weight;
  private final boolean canStack;

  public CapabilityProviderSize() {

    this(Size.SMALL, Weight.LIGHT, true);
  }

  public CapabilityProviderSize(Size size, Weight weight, boolean canStack) {

    this.size = size;
    this.weight = weight;
    this.canStack = canStack;
  }

  public static CapabilityProviderSize of() {
    return of(Size.SMALL, Weight.LIGHT, true); // Default to fitting in small vessels and stacksize = 32
  }

  public static CapabilityProviderSize of(Size size, Weight weight) {
    return of(size, weight, true); // Default to fitting in small vessels and stacksize = 32
  }

  public static CapabilityProviderSize of(Size size, Weight weight, boolean canStack) {
    EnumMap<Weight, CapabilityProviderSize[]> nested = CACHE.computeIfAbsent(size, k -> new EnumMap<>(Weight.class));
    CapabilityProviderSize[] handlers = nested.computeIfAbsent(weight, k -> new CapabilityProviderSize[2]);
    if (handlers[canStack ? 1 : 0] == null) {
      handlers[canStack ? 1 : 0] = new CapabilityProviderSize(size, weight, canStack);
    }
    return handlers[canStack ? 1 : 0];
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return this.weight;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return this.size;
  }

  /**
   * Should be called from {@link net.minecraft.item.Item#getItemStackLimit(ItemStack)}
   */
  @Override
  public int getStackSize(@NotNull ItemStack stack) {
    return this.canStack ? this.weight.stackSize : 1;
  }

  @Override
  public boolean canStack(@NotNull ItemStack stack) {
    return canStack;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilitySize.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }

  @Override
  public ICapabilityProvider createProvider(ItemStack itemStack) {
    return this;
  }

}
