package su.terrafirmagreg.framework.manager.content.base.item.spi;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public abstract class BaseItemEntity extends BaseItem {

  public BaseItemEntity(ItemSettings settings) {
    super(settings);

  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    Vec3d vec3d = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
    Vec3d lookVec = playerIn.getLookVec();
    Vec3d vec3d1 = new Vec3d(lookVec.x * 5.0 + vec3d.x, lookVec.y * 5.0 + vec3d.y, lookVec.z * 5.0 + vec3d.z);

    RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d1, false);
    if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
      if (!worldIn.isRemote) {
        Entity entity = this.createEntity(worldIn, playerIn, itemstack);
        if (entity != null) {
          entity.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
          entity.rotationYaw = (playerIn.rotationYaw + 180) % 360;
          worldIn.spawnEntity(entity);

          if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
          }
        }
      }
      return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }
    return new ActionResult<>(EnumActionResult.FAIL, itemstack);
  }

  @Override
  @Nullable
  public abstract Entity createEntity(World worldIn, Entity location, ItemStack itemstack);
}
