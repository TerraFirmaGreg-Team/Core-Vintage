package su.terrafirmagreg.modules.wood.objects.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import su.terrafirmagreg.modules.core.api.capabilities.pull.PullCapability;

public class EntityWoodAIPullCart extends EntityAIBase {

	private final EntityLiving living;

	public EntityWoodAIPullCart(EntityLiving livingIn) {
		this.living = livingIn;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (this.living.hasCapability(PullCapability.PULL_CAPABILITY, null)) {
			return this.living.getCapability(PullCapability.PULL_CAPABILITY, null).getDrawn() != null;
		}
		return false;
	}

}
