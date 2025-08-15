package su.terrafirmagreg.modules.wood.object.item.spi;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemColor;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;

import net.minecraft.client.renderer.color.IItemColor;
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
public abstract class ItemWoodCart extends BaseItem implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodCart(WoodType type, String variant) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey(variant))
      .customResource(type.getResource(variant))
      .addOreDict(variant)
      .capability(CapabilityProviderSize.of(Size.HUGE, Weight.VERY_HEAVY))
      .maxStackSize(1)
    );

    this.type = type;
  }


  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    Vec3d vec3d = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
    Vec3d lookVec = playerIn.getLookVec();
    Vec3d vec3d1 = new Vec3d(lookVec.x * 5.0 + vec3d.x, lookVec.y * 5.0 + vec3d.y, lookVec.z * 5.0 + vec3d.z);

    RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d1, false);
    if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
      if (!worldIn.isRemote) {
        EntityWoodCart cart = this.createEntity(worldIn, playerIn, itemstack);
        if (cart != null) {
          cart.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
          cart.setWood(type);
          cart.rotationYaw = (playerIn.rotationYaw + 180) % 360;
          worldIn.spawnEntity(cart);

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
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }

  @Override
  @Nullable
  public abstract EntityWoodCart createEntity(World worldIn, Entity location, ItemStack itemstack);
}
