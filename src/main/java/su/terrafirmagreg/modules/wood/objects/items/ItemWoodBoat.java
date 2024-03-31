package su.terrafirmagreg.modules.wood.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodBoat;

import java.util.List;
import java.util.Objects;

@Getter
public class ItemWoodBoat extends ItemBase implements IWoodItem {

	private final WoodItemVariant itemVariant;
	private final WoodType type;


	public ItemWoodBoat(WoodItemVariant itemVariant, WoodType type) {
		this.type = type;
		this.itemVariant = itemVariant;

//        OreDictUtils.register(this, variant.toString());
//        OreDictUtils.register(this, variant.toString(), type.toString());
	}


	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.LARGE;
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.MEDIUM;
	}

	@Override
	public boolean canStack(@NotNull ItemStack stack) {
		return false;
	}

	/**
	 * Copy from vanilla ItemBoat, but setting EntityBoatTFC's wood type
	 */
	@NotNull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		float f = 1.0F;
		float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch);
		float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw);
		double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX);
		double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) + (double) playerIn.getEyeHeight();
		double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ);
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		Vec3d vec3d1 = vec3d.add((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
		RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);

		if (raytraceresult == null) {
			return new ActionResult<>(EnumActionResult.PASS, itemstack);
		} else {
			Vec3d vec3d2 = playerIn.getLook(1.0F);
			boolean flag = false;
			List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox()
					.expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D)
					.grow(1.0D));

			for (Entity entity : list) {
				if (entity.canBeCollidedWith()) {
					AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());

					if (axisalignedbb.contains(vec3d)) {
						flag = true;
					}
				}
			}

			if (flag) {
				return new ActionResult<>(EnumActionResult.PASS, itemstack);
			} else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
				return new ActionResult<>(EnumActionResult.PASS, itemstack);
			} else {
				Block block = worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
				boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
				EntityWoodBoat entityboat = new EntityWoodBoat(worldIn, raytraceresult.hitVec.x, flag1
						? raytraceresult.hitVec.y - 0.12D
						: raytraceresult.hitVec.y, raytraceresult.hitVec.z);
				entityboat.setBoatType(EntityBoat.Type.OAK); // not sure if required
				entityboat.setWood(type);
				entityboat.rotationYaw = playerIn.rotationYaw;

				if (!worldIn.getCollisionBoxes(entityboat, entityboat.getEntityBoundingBox().grow(-0.1D)).isEmpty()) {
					return new ActionResult<>(EnumActionResult.FAIL, itemstack);
				} else {
					if (!worldIn.isRemote) {
						worldIn.spawnEntity(entityboat);
					}

					if (!playerIn.capabilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
					return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
				}
			}
		}
	}

	@Override
	public void onModelRegister() {
		ModelUtils.registerInventoryModel(this, getResourceLocation());

	}


	@Override
	public IItemColor getColorHandler() {
		return (s, i) -> this.getType().getColor();
	}
}
