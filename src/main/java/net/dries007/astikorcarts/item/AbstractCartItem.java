package net.dries007.astikorcarts.item;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.entity.AbstractDrawn;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import javax.annotation.Nonnull;

public abstract class AbstractCartItem extends ItemTFC {

  public AbstractCartItem(String name) {
    this.setRegistryName(AstikorCarts.MODID, name);
    this.setTranslationKey(this.getRegistryName().toString());
    this.setCreativeTab(CreativeTabsTFC.CT_WOOD);
    this.setMaxStackSize(1);
  }

  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    Vec3d vec3d = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
    Vec3d lookVec = playerIn.getLookVec();
    Vec3d vec3d1 = new Vec3d(lookVec.x * 5.0 + vec3d.x, lookVec.y * 5.0 + vec3d.y, lookVec.z * 5.0 + vec3d.z);

    RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d1, false);
    if (result != null) {
      if (result.typeOfHit == Type.BLOCK) {
        if (!worldIn.isRemote) {
          AbstractDrawn cart = this.newCart(worldIn);
          cart.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
          cart.rotationYaw = (playerIn.rotationYaw + 180) % 360;
          worldIn.spawnEntity(cart);

          if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
          }
          playerIn.addStat(StatList.getObjectUseStats(this));
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      }
    }
    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
  }

  public abstract AbstractDrawn newCart(World worldIn);

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.HUGE;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.VERY_HEAVY;
  }
}
