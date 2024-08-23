package su.terrafirmagreg.modules.core.objects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

/**
 * Generic entity used for sitting on top of blocks
 */
@Getter
public class EntitySeatOn extends Entity {

    private BlockPos pos;

    public EntitySeatOn(World world) {
        super(world);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }

    public EntitySeatOn(World world, BlockPos pos, double y0ffset) {
        this(world);
        this.pos = pos;
        setPosition(pos.getX() + 0.5D, pos.getY() + y0ffset, pos.getZ() + 0.5D);
    }

    @Nullable
    public Entity getSittingEntity() {
        for (Entity ent : this.getPassengers()) {
            if (ent instanceof EntityLiving) return ent;
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
    protected void readEntityFromNBT(NBTTagCompound nbt) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {}

    @Override
    public double getMountedYOffset() {
        return this.height * 0.0D;
    }

}
