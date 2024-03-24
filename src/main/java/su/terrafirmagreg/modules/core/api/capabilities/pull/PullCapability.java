package su.terrafirmagreg.modules.core.api.capabilities.pull;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import su.terrafirmagreg.api.util.ModUtils;

@Setter
@Getter
public class PullCapability {

	public static final ResourceLocation KEY = ModUtils.getID("pull_capability");

	@CapabilityInject(IPullCapability.class)
	public static Capability<IPullCapability> PULL_CAPABILITY;


	public static void preInit() {
		CapabilityManager.INSTANCE.register(IPullCapability.class, new PullStorage(), PullProvider::new);

	}
}
