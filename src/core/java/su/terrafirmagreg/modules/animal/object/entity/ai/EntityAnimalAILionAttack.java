package su.terrafirmagreg.modules.animal.object.entity.ai;

import su.terrafirmagreg.modules.animal.object.entity.predator.EntityAnimalLion;

/**
 * Adds a bit of animation to the attack
 */
public class EntityAnimalAILionAttack extends EntityAnimalAIAttackMelee<EntityAnimalLion> {

  private final EntityAnimalLion entity;
  protected int attackTicks;

  public EntityAnimalAILionAttack(EntityAnimalLion entity) {
    super(entity, 1.3D, 1.5D, AttackBehavior.NIGHTTIME_ONLY);
    this.entity = entity;
    this.attackTicks = 0;
  }

  @Override
  public void resetTask() {
    super.resetTask();
    this.attackTicks = 0;
    entity.setMouthTicks(0);
  }

  @Override
  public void updateTask() {
    super.updateTask();
    ++this.attackTicks;
    entity.setMouthTicks(attackTicks);
  }
}
