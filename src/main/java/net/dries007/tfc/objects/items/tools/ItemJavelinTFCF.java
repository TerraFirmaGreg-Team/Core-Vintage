package net.dries007.tfc.objects.items.tools;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.objects.items.ItemQuiver;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault

public class ItemJavelinTFCF extends ItemTool implements ICapabilitySize {

  public final ToolMaterial material;

  public ItemJavelinTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(material.getAttackDamage(), AttackSpeed, material, ImmutableSet.of());
    this.material = material;
    this.attackDamage = AttackDamage;
    this.attackSpeed = AttackSpeed;
    this.setHarvestLevel("hammer", material.getHarvestLevel());
    this.setMaxDamage((int) (material.getMaxUses() * 0.1));

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.LARGE; // Stored only in chests
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return false;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    playerIn.setActiveHand(handIn);
    return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
  }

  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {
    return 72000;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    if (entityLiving instanceof EntityPlayer player) {
      int charge = this.getMaxItemUseDuration(stack) - timeLeft;
      if (charge > 5) {
        float f = ItemBow.getArrowVelocity(charge); //Same charge time as bow

        if (!worldIn.isRemote) {
          EntityThrownJavelin javelin = new EntityThrownJavelin(worldIn, player);
          javelin.setDamage(2.5f *
                            attackDamage); // When thrown, it does approx 1.8x the tool material (attack damage is already 0.7x of the tool). This makes it slightly more damaging than axes but more difficult to use
          javelin.setWeapon(stack);
          javelin.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 1.5F, 0.5F);
          worldIn.spawnEntity(javelin);
          worldIn.playSound(null, player.posX, player.posY, player.posZ, TFCSounds.ITEM_THROW, SoundCategory.PLAYERS, 1.0F,
                            1.0F / (MathUtils.RNG.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
        }
        player.inventory.deleteStack(stack);
        player.addStat(StatList.getObjectUseStats(this));
        ItemQuiver.replenishJavelin(player.inventory); //Use a quiver if possible
      }
    }
  }

  @Override
  public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
    return false;
  }
}
