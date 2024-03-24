package su.terrafirmagreg.modules.core.api.capabilities.pull;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

import javax.annotation.Nullable;

public class PullProvider implements ICapabilityProvider, IPullCapability {

	private EntityWoodCart drawn;

	public PullProvider() {
		this(null);
	}

	public PullProvider(EntityWoodCart drawn) {
		this.drawn = drawn;
	}

	@Override
	public EntityWoodCart getDrawn() {
		return drawn;
	}

	@Override
	public void setDrawn(EntityWoodCart drawn) {
		this.drawn = drawn;
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == PullCapability.PULL_CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == PullCapability.PULL_CAPABILITY ? (T) this : null;
	}


}
