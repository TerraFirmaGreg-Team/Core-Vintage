package su.terrafirmagreg.modules.core.api.capabilities.pull;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class PullCapability {

    public static final ResourceLocation KEY = ModUtils.id("pull_capability");

    @CapabilityInject(IPullCapability.class)
    public static Capability<IPullCapability> PULL_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(IPullCapability.class, new PullStorage(), PullProvider::new);

    }
}
