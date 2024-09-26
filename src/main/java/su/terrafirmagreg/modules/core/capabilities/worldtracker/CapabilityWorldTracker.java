package su.terrafirmagreg.modules.core.capabilities.worldtracker;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityWorldTracker {

  public static final ResourceLocation KEY = ModUtils.resource("world_tracker_capability");

  @CapabilityInject(ICapabilityWorldTracker.class)
  public static final Capability<ICapabilityWorldTracker> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityWorldTracker.class, new StorageWorldTracker(),
                                        ProviderWorldTracker::new);
  }

  public static ICapabilityWorldTracker get(World world) {
    return world.getCapability(CAPABILITY, null);
  }

  public static boolean has(World world) {
    return world.hasCapability(CAPABILITY, null);
  }

}
