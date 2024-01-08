package de.mennomax.astikorcarts.entity;

import de.mennomax.astikorcarts.config.ModConfig;
import de.mennomax.astikorcarts.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMobCart extends AbstractDrawn {

	public EntityMobCart(World worldIn) {
		super(worldIn);
		this.setSize(1.375F, 1.4F);
		this.spacing = 2.4D;
	}

	@Override
	public boolean canBePulledBy(Entity pullingIn) {
		if (this.isPassenger(pullingIn)) {
			return false;
		}
		for (String entry : ModConfig.mobCart.canPull) {
			if (entry.equals(pullingIn instanceof EntityPlayer ? "minecraft:player" : EntityList.getKey(pullingIn)
			                                                                                    .toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Item getCartItem() {
		return ModItems.MOBCART;
	}

	@Override
	public void applyEntityCollision(Entity entity) {

		if (!entity.isPassenger(this)) {
			if (!this.world.isRemote && this.pulling != entity && !(this.getControllingPassenger() instanceof EntityPlayer) && this.getPassengers()
			                                                                                                                       .size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
				entity.startRiding(this);
			} else {
				super.applyEntityCollision(entity);
			}
		}
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (player.isSneaking()) {
				for (Entity entity : this.getPassengers()) {
					if (!(entity instanceof EntityPlayer)) {
						entity.dismountRidingEntity();
						return true;
					}
				}
			} else if (this.pulling != player) {
				player.startRiding(this);
			}
		}
		return true;
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger)) {
			double f = -0.1D;

			if (this.getPassengers().size() > 1) {
				f = this.getPassengers().indexOf(passenger) == 0 ? 0.2D : -0.6D;

				if (passenger instanceof EntityAnimal) {
					f += 0.2D;
				}
			}

			Vec3d vec3d = new Vec3d(f, 0.0D, 0.0D).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
			passenger.setPosition(this.posX + vec3d.x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + vec3d.z);

			if (!(passenger instanceof EntityPlayer)) {
				passenger.setRenderYawOffset(this.rotationYaw);
				float f2 = MathHelper.wrapDegrees(passenger.rotationYaw - this.rotationYaw);
				float f1 = MathHelper.clamp(f2, -105.0F, 105.0F);
				passenger.prevRotationYaw += f1 - f2;
				passenger.rotationYaw += f1 - f2;
				passenger.setRotationYawHead(passenger.rotationYaw);

				if (passenger instanceof EntityAnimal && this.getPassengers().size() > 1) {
					int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
					passenger.setRenderYawOffset(((EntityAnimal) passenger).renderYawOffset + (float) j);
					passenger.setRotationYawHead(passenger.getRotationYawHead() + (float) j);
				}
			}
		}
	}

	@Override
	public double getMountedYOffset() {
		return 0.7D;
	}

	protected boolean canFitPassenger(Entity passenger) {
		return this.getPassengers().size() < 2;
	}

}
