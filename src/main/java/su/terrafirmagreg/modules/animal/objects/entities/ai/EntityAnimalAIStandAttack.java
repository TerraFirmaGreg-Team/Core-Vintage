package su.terrafirmagreg.modules.animal.objects.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;

/**
 * Extended Melee AI for standing attacks (bears and polar bears)
 */

public class EntityAnimalAIStandAttack<T extends EntityCreature & IAnimal & EntityAnimalAIStandAttack.IEntityStandAttack> extends EntityAnimalAIAttackMelee<T> {
	public EntityAnimalAIStandAttack(T creature, double speedIn, double attackReach) {
		super(creature, speedIn, attackReach);
	}

	public EntityAnimalAIStandAttack(T creature, double speedIn, double attackReach, AttackBehavior attackBehavior) {
		super(creature, speedIn, attackReach, attackBehavior);
	}

	@Override
	public void resetTask() {
		((IEntityStandAttack) this.attacker).setStand(false);
		super.resetTask();
	}

	@Override
	protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
		double d0 = this.getAttackReachSqr(enemy);
		if (distToEnemySqr <= d0 && this.attackTick <= 0) {
			this.attackTick = 20;
			this.attacker.attackEntityAsMob(enemy);
			((IEntityStandAttack) this.attacker).setStand(false);
		} else if (distToEnemySqr <= d0 * 2.0D) {
			if (this.attackTick <= 0) {
				((IEntityStandAttack) this.attacker).setStand(false);
				this.attackTick = 20;
			}

			if (this.attackTick <= 10) {
				((IEntityStandAttack) this.attacker).setStand(true);
				((IEntityStandAttack) this.attacker).playWarning();
			}
		} else {
			this.attackTick = 20;
			((IEntityStandAttack) this.attacker).setStand(false);
		}

	}

	public interface IEntityStandAttack {
		void setStand(boolean value);

		void playWarning();
	}
}
