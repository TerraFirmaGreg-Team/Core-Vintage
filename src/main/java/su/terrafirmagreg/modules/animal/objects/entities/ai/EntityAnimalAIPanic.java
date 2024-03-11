package su.terrafirmagreg.modules.animal.objects.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;

/**
 * Improves Panic AI by making this entity runs whenever it receives damage
 */
public class EntityAnimalAIPanic extends EntityAIPanic {
	protected int timer;

	public EntityAnimalAIPanic(EntityCreature creature, double speedIn) {
		super(creature, speedIn);
	}

	@Override
	public boolean shouldExecute() {
		if (this.creature.hurtTime > 0) {
			timer = 80;
			return this.findRandomPosition();
		}
		return false;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && timer > 0;
	}
}
