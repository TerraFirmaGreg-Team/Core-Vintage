package su.terrafirmagreg.modules.device.objects.entity;

import su.terrafirmagreg.modules.animal.api.type.IPredator;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


import lyeoj.tfcthings.main.ConfigTFCThings;

import org.jetbrains.annotations.NotNull;

public class EntitySlingStone extends EntityThrowable {

    private int power;

    public EntitySlingStone(World worldIn) {
        super(worldIn);
    }

    public EntitySlingStone(World worldIn, EntityLivingBase throwerIn) {
        this(worldIn, throwerIn, 1);
    }

    public EntitySlingStone(World worldIn, EntityLivingBase throwerIn, int power) {
        super(worldIn, throwerIn);
        this.power = power;
    }

    private boolean shouldHit(RayTraceResult result) {
        if (result.entityHit == null) {
            return false;
        } else {
            if (getThrower() != null && getThrower().isRiding()) {
                return (result.entityHit != getThrower() && result.entityHit != getThrower().getRidingEntity()) || this.ticksExisted > 20;
            }
            return result.entityHit != getThrower() || this.ticksExisted > 10;
        }
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        if (shouldHit(result)) {
            float i = power;

            if (result.entityHit instanceof IPredator || result.entityHit instanceof AbstractSkeleton) {
                double predatorMultiplier = ConfigTFCThings.Items.SLING.predatorMultiplier;
                i *= (float) predatorMultiplier;
            }
            if (this.isBurning()) {
                result.entityHit.setFire(5);
            }
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
        }

        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                if (shouldHit(result)) {
                    this.world.setEntityState(this, (byte) 3);
                    this.setDead();
                }
            } else if (world.getBlockState(result.getBlockPos())
                    .getCollisionBoundingBox(world, result.getBlockPos()) != Block.NULL_AABB) {
                this.world.setEntityState(this, (byte) 3);
                this.setDead();
            }
        }
    }

}
