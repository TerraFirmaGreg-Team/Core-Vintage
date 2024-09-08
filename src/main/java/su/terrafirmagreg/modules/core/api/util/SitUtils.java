package su.terrafirmagreg.modules.core.api.util;

import java.util.List;

import lombok.experimental.UtilityClass;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import su.terrafirmagreg.modules.core.object.entity.EntitySeatOn;

@UtilityClass
public final class SitUtils {

  /**
   * Makes an entity sit on a block
   *
   * @param world    the worldObj
   * @param pos      the BlockPos of the block to sit on
   * @param creature the entityLiving that will sit on this block
   * @param yOffset  the y offset of the top facing
   */
  public static void sitOnBlock(@NotNull World world, @NotNull BlockPos pos,
          @NotNull EntityLiving creature, double yOffset) {
    if (!world.isRemote && !world.getBlockState(pos).getMaterial().isReplaceable()) {
      EntitySeatOn seat = new EntitySeatOn(world, pos, yOffset);
      world.spawnEntity(seat);
      creature.startRiding(seat);
    }
  }

  /**
   * Returns the entity which is sitting on this BlockPos.
   *
   * @param world the WorldObj
   * @param pos   the BlockPos of this block
   * @return the entity which is sitting on this block, or null if none
   */
  @Nullable
  public static Entity getSittingEntity(@NotNull World world, @NotNull BlockPos pos) {
    if (!world.isRemote) {
      List<EntitySeatOn> seats = world.getEntitiesWithinAABB(EntitySeatOn.class,
              new AxisAlignedBB(pos).grow(1D));
      for (EntitySeatOn seat : seats) {
        if (pos.equals(seat.getPos())) {
          return seat.getSittingEntity();
        }
      }
    }
    return null;
  }
}
