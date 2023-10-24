package de.mennomax.astikorcarts.entity;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import de.mennomax.astikorcarts.capabilities.PullProvider;
import de.mennomax.astikorcarts.config.ModConfig;
import de.mennomax.astikorcarts.handler.PacketHandler;
import de.mennomax.astikorcarts.packets.SPacketDrawnUpdate;
import io.netty.buffer.ByteBuf;

public abstract class AbstractDrawn extends Entity implements IEntityAdditionalSpawnData
{
    public static final UUID PULL_SLOWLY_MODIFIER_UUID = UUID.fromString("49B0E52E-48F2-4D89-BED7-4F5DF26F1263");
    public static final AttributeModifier PULL_SLOWLY_MODIFIER = (new AttributeModifier(PULL_SLOWLY_MODIFIER_UUID, "Pull slowly modifier", ModConfig.speedModifier, 2)).setSaved(false);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(AbstractDrawn.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(AbstractDrawn.class, DataSerializers.FLOAT);
    protected Entity pulling;
    // The distance between the cart and the pulling entity that should be maintained
    protected double spacing;
    private UUID firstPullingUUID;
    @SideOnly(Side.CLIENT)
    private int firstPullingId;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    @SideOnly(Side.CLIENT)
    private double factor;
    @SideOnly(Side.CLIENT)
    private float wheelrot;
    private boolean fellLastTick;

    public AbstractDrawn(World worldIn)
    {
        super(worldIn);
        this.stepHeight = 1.2F;
        if (worldIn.isRemote)
        {
            this.firstPullingId = -1;
        }
    }

    /**
     * Handles the rotation of this cart and its components.
     *
     * @param targetVecIn
     */
    public void handleRotation(Vec3d targetVecIn)
    {
        this.rotationYaw = (float) Math.toDegrees(-Math.atan2(targetVecIn.x - this.posX, targetVecIn.z - this.posZ));
    }

    /**
     * @return The position this cart should always face and travel towards.
     */
    public Vec3d getTargetVec()
    {
        return new Vec3d(this.pulling.posX, this.pulling.posY, this.pulling.posZ);
    }

    /**
     * @param pullingIn {@link Entity} that tries to pull this cart.
     * @return {@code true}, if the entity is able pull this cart, {@code false}
     * else.
     */
    public boolean canBePulledBy(Entity pullingIn)
    {
        return false;
    }

    public Entity getPulling()
    {
        return this.pulling;
    }

    /**
     * @param entityIn {@link Entity} that should pull this cart.
     */
    public void setPulling(Entity entityIn)
    {
        if (this.pulling == null || entityIn == null)
        {
            if (!this.world.isRemote)
            {
                if (entityIn == null)
                {
                    if (this.pulling != null)
                    {
                        if (this.pulling instanceof EntityLivingBase)
                        {
                            ((EntityLivingBase) this.pulling).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(AbstractDrawn.PULL_SLOWLY_MODIFIER);
                        }
                        this.pulling.getCapability(PullProvider.PULL, null).setDrawn(null);
                        this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.5F, 0.1F);
                    }
                    ((WorldServer) this.world).getEntityTracker().sendToTracking(this, PacketHandler.INSTANCE.getPacketFrom(new SPacketDrawnUpdate(-1, this.getEntityId())));
                }
                else
                {
                    if (entityIn instanceof EntityLiving)
                    {
                        ((EntityLiving) entityIn).getNavigator().clearPath();
                    }
                    entityIn.getCapability(PullProvider.PULL, null).setDrawn(this);
                    ((WorldServer) this.world).getEntityTracker().sendToTracking(this, PacketHandler.INSTANCE.getPacketFrom(new SPacketDrawnUpdate(entityIn.getEntityId(), this.getEntityId())));
                    this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
                }
            }
            this.pulling = entityIn;
        }
    }

    /**
     * Attempts to attach this cart to a entity with the given Id and returns wether or not the entity existed in the world.
     *
     * @param entityId The Id of the entity that should start pulling this cart.
     * @return {@code true} if the entity existed in the client world, {@code false} else.
     */
    @SideOnly(Side.CLIENT)
    public boolean setPullingId(int entityId)
    {
        Entity entity = this.world.getEntityByID(entityId);
        if (entity != null)
        {
            this.setPulling(entity);
            return true;
        }
        return false;
    }

    /**
     * @return The current wheel rotation angle.
     */
    @SideOnly(Side.CLIENT)
    public float getWheelRotation()
    {
        if (this.pulling != null && !Minecraft.getMinecraft().isGamePaused())
        {
            this.wheelrot -= 0.12F * this.factor;
        }
        return this.wheelrot;
    }

    public float getDamageTaken()
    {
        return this.dataManager.get(DAMAGE_TAKEN);
    }

    public void setDamageTaken(float damageTaken)
    {
        this.dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    public int getTimeSinceHit()
    {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    public void setTimeSinceHit(int timeSinceHit)
    {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    public abstract Item getCartItem();

    /**
     * Executes upon carts destruction. Currently only used to drop items on death.
     *
     * @param source           The damage source.
     * @param byCreativePlayer Whether or not this entity was destroyed by a player in creative mode.
     */
    public void onDestroyed(DamageSource source, boolean byCreativePlayer)
    {
        if (!byCreativePlayer && this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            this.dropItemWithOffset(this.getCartItem(), 1, 0.0F);
        }
    }

    public void writeSpawnData(ByteBuf buffer)
    {
        if (this.pulling != null)
        {
            buffer.writeInt(this.pulling.getEntityId());
        }
    }

    public void readSpawnData(ByteBuf additionalData)
    {
        if (additionalData.readableBytes() >= 4)
        {
            int entityId = additionalData.readInt();
            if (!this.setPullingId(entityId))
            {
                this.firstPullingId = entityId;
            }
        }
    }

    /**
     * @return Whether the currently pulling entity should stop pulling this cart.
     */
    protected boolean shouldRemovePulling()
    {
        if (this.pulling != null)
        {
            if (this.pulling.isRiding())
                return true;
            if (this.collidedHorizontally)
            {
                RayTraceResult result = this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + this.height, this.posZ), new Vec3d(this.pulling.posX, this.pulling.posY + (this.height / 2.0F), this.pulling.posZ), false, true, false);
                if (result != null)
                    if (result.typeOfHit == RayTraceResult.Type.BLOCK)
                        return true;
            }
        }
        return this.pulling.isDead;
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(DAMAGE_TAKEN, 0.0F);
    }

    @Override
    public void onUpdate()
    {
        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (!this.hasNoGravity())
        {
            this.motionY -= 0.04D;
        }
        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        super.onUpdate();
        this.tickLerp();
        if (this.pulling != null)
        {
            if (!this.world.isRemote)
            {
                if (this.shouldRemovePulling())
                {
                    this.setPulling(null);
                    return;
                }
            }
            Vec3d targetVec = this.getTargetVec();
            this.handleRotation(targetVec);
            double dRotation = this.prevRotationYaw - this.rotationYaw;
            if (dRotation < -180.0D)
            {
                this.prevRotationYaw += 360.0F;
            }
            else if (dRotation >= 180.0D)
            {
                this.prevRotationYaw -= 360.0F;
            }
            double lookX = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float) Math.PI);
            double lookZ = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float) Math.PI);
            double moveX = targetVec.x - this.posX + lookX * this.spacing;
            double moveZ = targetVec.z - this.posZ + lookZ * this.spacing;
            this.motionX = moveX;
//            if(this.pulling instanceof EntityPlayer && !this.world.isRemote)
//            {
//                System.out.println(this.pulling.fallDistance);
//            }
            if (!this.pulling.onGround && this.pulling.fallDistance == 0.0F)
            {
                this.motionY = targetVec.y - this.posY;
                this.fallDistance = 0.0F;
                this.fellLastTick = false;
            }
            else if (!fellLastTick)
            {
                this.motionY = 0.0D;
                this.fellLastTick = true;
            }
            this.motionZ = moveZ;
            if (this.world.isRemote)
            {
                this.factor = Math.sqrt((moveX + lookX) * (moveX + lookX) + (moveZ + lookZ) * (moveZ + lookZ)) > 1 ? Math.sqrt(moveX * moveX + moveZ * moveZ) : -Math.sqrt(moveX * moveX + moveZ * moveZ);
            }
        }
        else
        {
            this.motionX = 0.0D;
            this.motionZ = 0.0D;
            this.attemptReattach();
        }
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        for (Entity entity : this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this)))
        {
            this.applyEntityCollision(entity);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (!this.world.isRemote && !this.isDead)
        {
            if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource()))
            {
                return false;
            }
            else
            {
                this.setTimeSinceHit(10);
                this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;

                if (flag || this.getDamageTaken() > 40.0F)
                {
                    this.onDestroyed(source, flag);
                    this.setPulling(null);
                    this.setDead();
                }

                return true;
            }
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.firstPullingUUID = compound.getUniqueId("FirstPullingUUID");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        if (this.pulling != null)
        {
            compound.setUniqueId("FirstPullingUUID", this.pulling.getUniqueID());
        }
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0)
        {
            this.lerpSteps = 0;
            this.posX = this.lerpX;
            this.posY = this.lerpY;
            this.posZ = this.lerpZ;
            this.rotationYaw = (float) this.lerpYaw;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = yaw;
        this.lerpSteps = 10;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(this.getCartItem());
    }

    private void attemptReattach()
    {
        if (this.world.isRemote)
        {
            if (this.firstPullingId != -1)
            {
                if (this.setPullingId(this.firstPullingId))
                {
                    this.firstPullingId = -1;
                }
            }
        }
        else
        {
            if (this.firstPullingUUID != null)
            {
                Entity pulling = ((WorldServer) this.world).getEntityFromUuid(this.firstPullingUUID);
                if (pulling != null)
                {
                    this.setPulling(pulling);
                    this.firstPullingUUID = null;
                }
            }
        }

    }

    private void tickLerp()
    {
        if (this.lerpSteps > 0)
        {
            double dx = this.posX + (this.lerpX - this.posX) / this.lerpSteps;
            double dy = this.posY + (this.lerpY - this.posY) / this.lerpSteps;
            double dz = this.posZ + (this.lerpZ - this.posZ) / this.lerpSteps;
            double drot = MathHelper.wrapDegrees(this.lerpYaw - this.rotationYaw);
            this.rotationYaw = (float) (this.rotationYaw + drot / this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(dx, dy, dz);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }
}