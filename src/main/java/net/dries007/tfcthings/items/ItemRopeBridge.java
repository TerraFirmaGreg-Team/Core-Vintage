package net.dries007.tfcthings.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import net.dries007.tfcthings.entity.projectile.EntityRopeBridgeThrown;
import net.dries007.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;

public class ItemRopeBridge extends ItemTFC implements TFCThingsConfigurableItem {

  public ItemRopeBridge() {
    this.setRegistryName("rope_bridge_bundle");
    this.setTranslationKey("rope_bridge_bundle");
    this.setCreativeTab(CreativeTabsTFC.CT_MISC);
  }

  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    playerIn.setActiveHand(handIn);
    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
  }

  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }

  public int getMaxItemUseDuration(ItemStack stack) {
    return 72000;
  }

  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    int charge = this.getMaxItemUseDuration(stack) - timeLeft;
    if (charge > 5) {
      float f = ItemBow.getArrowVelocity(charge);
      if (!worldIn.isRemote) {
        EntityRopeBridgeThrown bridge = new EntityRopeBridgeThrown(worldIn, entityLiving, stack);
        bridge.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, f * 1.5f, 0.0f);
        worldIn.spawnEntity(bridge);
      }
      worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS,
                        0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    }
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
    return Weight.LIGHT;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack itemStack) {
    return Size.SMALL;
  }

  @Override
  public int getStackSize(@NotNull ItemStack stack) {
    return ConfigTFCThings.Items.ROPE_BRIDGE.maxStackSize;
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeBridge;
  }
}
