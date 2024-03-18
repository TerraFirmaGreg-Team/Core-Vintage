package su.terrafirmagreg.modules.core.api.capabilities.pull;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class PullProvider implements ICapabilityProvider {
	@CapabilityInject(IPullCapability.class)
	public static final Capability<IPullCapability> PULL = null;

	private final IPullCapability instance = PULL.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == PULL;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == PULL) {
			return PULL.cast(this.instance);
		}
		return null;
	}
}
