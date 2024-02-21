package su.terrafirmagreg.modules.wood.objects.entities.ai;

import de.mennomax.astikorcarts.capabilities.PullProvider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityWoodAIPullCart extends EntityAIBase {

	private final EntityLiving living;

	public EntityWoodAIPullCart(EntityLiving livingIn) {
		this.living = livingIn;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (this.living.hasCapability(PullProvider.PULL, null)) {
			return this.living.getCapability(PullProvider.PULL, null).getDrawn() != null;
		}
		return false;
	}

}
