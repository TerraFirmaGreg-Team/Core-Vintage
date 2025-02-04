package su.terrafirmagreg.modules.core.object.entity;

import su.terrafirmagreg.api.base.object.entity.spi.BaseEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

/**
 * Generic entity used for sitting on top of blocks
 */
@Getter
public class EntitySeatOn extends BaseEntity {

  private BlockPos pos;

  public EntitySeatOn(World world, BlockPos pos, double y0ffset) {
    this(world);
    this.pos = pos;
    setPosition(pos.getX() + 0.5D, pos.getY() + y0ffset, pos.getZ() + 0.5D);
  }

  public EntitySeatOn(World world) {
    super(world);
    this.noClip = true;
    this.height = 0.01F;
    this.width = 0.01F;
  }

  /**
   * Makes an entity sit on a block
   *
   * @param world    the worldObj
   * @param pos      the BlockPos of the block to sit on
   * @param creature the entityLiving that will sit on this block
   * @param yOffset  the y offset of the top facing
   */
  public static void sitOnBlock(@NotNull World world, @NotNull BlockPos pos, @NotNull EntityLiving creature, double yOffset) {
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
      List<EntitySeatOn> seats = world.getEntitiesWithinAABB(EntitySeatOn.class, new AxisAlignedBB(pos).grow(1D));
      for (EntitySeatOn seat : seats) {
        if (pos.equals(seat.getPos())) {
          return seat.getSittingEntity();
        }
      }
    }
    return null;
  }

  @Nullable
  public Entity getSittingEntity() {
    for (Entity ent : this.getPassengers()) {
      if (ent instanceof EntityLiving) {
        return ent;
      }
    }
    return null;
  }

  @Override
  protected void entityInit() {
  }

  @Override
  public void onEntityUpdate() {
    if (!this.world.isRemote) {
      if (pos == null || !this.isBeingRidden() || this.world.isAirBlock(pos)) {
        this.setDead();
      }
    }
  }

  @Override
  protected boolean shouldSetPosAfterLoading() {
    return false;
  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound nbt) {
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound nbt) {
  }

  @Override
  public double getMountedYOffset() {
    return this.height * 0.0D;
  }

}
