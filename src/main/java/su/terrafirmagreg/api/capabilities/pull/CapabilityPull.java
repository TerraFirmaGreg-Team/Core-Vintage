package su.terrafirmagreg.api.capabilities.pull;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityPull {

    public static final ResourceLocation KEY = ModUtils.resource("pull_capability");

    @CapabilityInject(ICapabilityPull.class)
    public static Capability<ICapabilityPull> CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ICapabilityPull.class, new StoragePull(), ProviderPull::new);

    }

    public static ICapabilityPull get(Entity entity) {
        return entity.getCapability(CAPABILITY, null);
    }

    public static boolean has(Entity entity) {
        return entity.hasCapability(CAPABILITY, null);
    }
}
