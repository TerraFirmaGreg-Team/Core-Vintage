package su.terrafirmagreg.modules.core.capabilities.ambiental;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public final class CapabilityAmbiental {

  public static final ResourceLocation KEY = ModUtils.resource("ambiental_capability");

  @CapabilityInject(ICapabilityAmbiental.class)
  public static final Capability<ICapabilityAmbiental> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityAmbiental.class, new CapabilityStorageAmbiental(), CapabilityProviderAmbiental::new);
  }

  @Nullable
  public static ICapabilityAmbiental get(Entity entity) {
    return entity.getCapability(CAPABILITY, null);
  }

  public static boolean has(Entity entity) {
    return entity.hasCapability(CAPABILITY, null);
  }
}
