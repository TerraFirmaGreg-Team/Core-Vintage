package net.dries007.tfc.objects.items.tools;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;

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
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DRY_GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS_PATH;

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
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
  }

  @Override
  @NotNull
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
          float hitZ) {
    ItemStack itemstack = player.getHeldItem(hand);

    if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
      return EnumActionResult.FAIL;
    } else {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (!(block instanceof ISoilBlock soilBlock)) {
        return EnumActionResult.PASS;
      }
      if (ConfigTFC.General.OVERRIDES.enableGrassPath && facing != EnumFacing.DOWN &&
              worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR &&
              BlockUtils.isVariant(soilBlock.getVariant(), GRASS, DRY_GRASS, DIRT)) {
        IBlockState iblockstate1 = GRASS_PATH.get(soilBlock.getType()).getDefaultState();
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
}
