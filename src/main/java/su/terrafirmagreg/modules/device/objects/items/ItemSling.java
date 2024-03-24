package su.terrafirmagreg.modules.device.objects.items;

import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.entity.projectile.EntitySlingStoneMetal;
import lyeoj.tfcthings.entity.projectile.EntitySlingStoneMetalLight;
import lyeoj.tfcthings.entity.projectile.EntityUnknownProjectile;
import lyeoj.tfcthings.items.ItemMetalSlingAmmo;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSling extends ItemBase {

	public ItemSling() {
		this.setMaxDamage(64);
		this.setNoRepair();
		this.setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(@NotNull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && entityIn.getItemInUseMaxCount() > 0) {
					int maxPower = ConfigTFCThings.Items.SLING.maxPower;
					int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;
					float powerRatio = Math.min((float) entityIn.getItemInUseMaxCount() / (float) chargeSpeed, maxPower) / (float) maxPower;
					return (float) MathHelper.floor(((entityIn.getItemInUseMaxCount() * powerRatio) % 8) + 1);
				}
				return 0.0F;
			}
		});
		OreDictUtils.register(this, "tool");
	}

	@Override
	public @NotNull String getName() {
		return "device/sling/normal";
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}


	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack itemStack) {
		return Size.NORMAL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack itemStack) {
		return Weight.MEDIUM;
	}

	public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {

		ItemStack itemstack = playerIn.getHeldItem(handIn);
		boolean flag = !this.findAmmo(playerIn).isEmpty();

		if (!playerIn.isCreative() && !flag) {
			return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
		}
	}

	public void onPlayerStoppedUsing(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer entityplayer) {

			boolean flag = entityplayer.isCreative();
			ItemStack itemStack = this.findAmmo(entityplayer);

			int maxPower = ConfigTFCThings.Items.SLING.maxPower;
			int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;

			int power = Math.min((this.getMaxItemUseDuration(stack) - timeLeft) / chargeSpeed, maxPower);
			float velocity = 1.6F * (power / (float) maxPower);
			float inaccuracy = 0.5F * (8.0F - power);

			if (!itemStack.isEmpty() && !flag) {

				if (!worldIn.isRemote) {
					shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
				}
				worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

				itemStack.shrink(1);
				if (itemStack.isEmpty()) {
					entityplayer.inventory.deleteStack(itemStack);
				}
				stack.damageItem(1, entityplayer);

			} else if (flag) {
				if (!worldIn.isRemote) {
					shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
				}
				worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			}
		}
	}

	private void shoot(World worldIn, EntityLivingBase entityLiving, int power, float velocity, float inaccuracy, ItemStack itemStack) {

		EntitySlingStone entitySlingStone;
		float adjustedVelocity = velocity;

		if (itemStack.getItem() instanceof ItemIngot) {
			entitySlingStone = new EntityUnknownProjectile(worldIn, entityLiving, power);
		} else if (itemStack.getItem() instanceof ItemMetalSlingAmmo ammo) {
			switch (ammo.getType()) {
				case 0:
					entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 5);
					break;
				case 1:
					entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
					for (int i = 0; i < 4; i++) {
						EntitySlingStoneMetal bonusStone = new EntitySlingStoneMetal(worldIn, entityLiving, power);
						bonusStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, velocity * 0.75f, inaccuracy + 2.5f);
						worldIn.spawnEntity(bonusStone);
					}
					break;
				case 2:
					entitySlingStone = new EntitySlingStoneMetalLight(worldIn, entityLiving, power + 3);
					adjustedVelocity *= 1.2F;
					break;
				default:
					entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
					entitySlingStone.setFire(10);
			}
		} else {
			entitySlingStone = new EntitySlingStone(worldIn, entityLiving, power);
		}
		entitySlingStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, adjustedVelocity, inaccuracy);
		worldIn.spawnEntity(entitySlingStone);
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (this.isStone(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isStone(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isStone(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isStone(ItemStack stack) {
		if (stack.getItem() instanceof ItemRock) {
			return true;
		} else if (stack.getItem() instanceof ItemIngot ingot) {
			return ingot.getMetal(stack) == Metal.UNKNOWN;
		}
		return false;
	}

	public int getMaxItemUseDuration(@NotNull ItemStack stack) {
		return 72000;
	}

	public @NotNull EnumAction getItemUseAction(@NotNull ItemStack stack) {
		return EnumAction.BOW;
	}

}
