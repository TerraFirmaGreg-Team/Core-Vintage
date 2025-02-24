package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Arrays;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockContainer extends BaseBlock implements IProviderTile {


  public BaseBlockContainer(Settings settings) {
    super(settings);

  }

  protected boolean isInvalidNeighbor(World world, BlockPos pos, EnumFacing facing) {
    return world.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
  }

  protected boolean hasInvalidNeighbor(World world, BlockPos pos) {
    return Arrays.stream(EnumFacing.HORIZONTALS).anyMatch(facing -> this.isInvalidNeighbor(world, pos, facing));
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.INVISIBLE;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, this.getTileClass()).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
    worldIn.removeTileEntity(pos);
  }

  @Override
  public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    if (te instanceof IWorldNameable nameable && nameable.hasCustomName()) {
      player.addStat(StatList.getBlockStats(this));
      player.addExhaustion(0.005F);

      if (world.isRemote) {
        return;
      }

      int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
      Item item = this.getItemDropped(state, world.rand, enchantmentLevel);

      if (item == Items.AIR) {
        return;
      }

      ItemStack itemstack = new ItemStack(item, this.quantityDropped(world.rand));
      itemstack.setStackDisplayName(nameable.getName());
      spawnAsEntity(world, pos, itemstack);
    } else {
      super.harvestBlock(world, player, pos, state, null, stack);
    }
  }

  @Override
  public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
    super.eventReceived(state, world, pos, id, param);
    return TileUtils.getTile(world, pos, getTileClass())
      .map(tile -> tile.receiveClientEvent(id, param))
      .orElse(false);
  }


}
