package su.terrafirmagreg.modules.animal.object.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import org.jetbrains.annotations.Nullable;

/**
 * Makes predators wanders only in it's designed hunting area Area = {@link EntityCreature#setHomePosAndDistance} A nice addition for 1.15: Make the home area
 * changes over time, as if this entity is migrating to another place
 */
public class EntityAnimalAIWanderHuntArea extends EntityAIWander {

  public EntityAnimalAIWanderHuntArea(EntityCreature creature, double speed) {
    super(creature, speed);
  }

  @Override
  public boolean shouldExecute() {
    return super.shouldExecute();
  }

  @Nullable
  @Override
  protected Vec3d getPosition() {
    if (this.entity.hasHome()) {
      BlockPos blockpos = this.entity.getHomePosition();
      return RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 16, 7,
                                                                  new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
    }
    return null;
  }
}
