package net.dries007.tfc.objects.items.tools;

import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemKnifeTFCF extends ItemTool implements ICapabilitySize {

  public final ToolMaterial material;

  public ItemKnifeTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(material.getAttackDamage(), AttackSpeed, material, ImmutableSet.of());
    this.material = material;
    this.attackDamage = AttackDamage;
    this.attackSpeed = AttackSpeed;
    this.setMaxDamage(Durability);
    this.setHarvestLevel(ToolClasses.KNIFE, material.getHarvestLevel());

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
  }

  @Override
  public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
    return true;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.NORMAL; // Stored in large vessels
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return false;
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
    // Knives always take damage
    if (!worldIn.isRemote) {
      stack.damageItem(1, entityLiving);
    }
    return true;
  }
}
