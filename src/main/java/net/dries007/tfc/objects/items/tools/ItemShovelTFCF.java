package net.dries007.tfc.objects.items.tools;

import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.ConfigTFC;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemShovelTFCF extends ItemSpade implements ICapabilitySize {

  public final ToolMaterial material;

  public ItemShovelTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(material);
    this.material = material;
    this.attackDamage = AttackDamage * material.getAttackDamage();
    this.attackSpeed = AttackSpeed;
    this.setMaxDamage(Durability);
    this.setHarvestLevel("shovel", material.getHarvestLevel());

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
  }

  @Override
  @Nonnull
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack itemstack = player.getHeldItem(hand);

    if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
      return EnumActionResult.FAIL;
    } else {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (!(block instanceof BlockRockVariant rockVariant)) {
        return EnumActionResult.PASS;
      }
      if (ConfigTFC.General.OVERRIDES.enableGrassPath && facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR
          && rockVariant.getType() == Rock.Type.GRASS || rockVariant.getType() == Rock.Type.DRY_GRASS || rockVariant.getType() == Rock.Type.DIRT) {
        IBlockState iblockstate1 = BlockRockVariant.get(rockVariant.getRock(), Rock.Type.PATH).getDefaultState();
        worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!worldIn.isRemote) {
          worldIn.setBlockState(pos, iblockstate1, 11);
          itemstack.damageItem(1, player);
        }

        return EnumActionResult.SUCCESS;
      }
    }
    return EnumActionResult.PASS;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.LARGE; // Stored only in chests
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
}
