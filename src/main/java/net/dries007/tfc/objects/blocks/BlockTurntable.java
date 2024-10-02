package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.te.TETurntable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.IntProp.CLAY_LEVEL;

@MethodsReturnNonnullByDefault
public class BlockTurntable extends BlockNonCube {

  private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4.0D / 16, 0.0D, 4.0D / 16, 12.0D / 16, 5.0D / 16, 12.0D / 16);

  public BlockTurntable() {
    super(Material.IRON);
    setHardness(1.0F);
    setResistance(1.0F);
    setDefaultState(blockState.getBaseState().withProperty(CLAY_LEVEL, 0));
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(CLAY_LEVEL, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(CLAY_LEVEL);
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return SHAPE;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileUtils.getTile(world, pos, TETurntable.class).ifPresent(tile -> tile.onBreakBlock(world, pos, state));
    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (world.isRemote && hand != EnumHand.MAIN_HAND) {return false;}
    ItemStack held = player.getHeldItem(hand);
    if (player.isSneaking()) {
      TileUtils.getTile(world, pos, TETurntable.class)
               .filter(TETurntable::hasPottery)
               .ifPresent(TETurntable::rotate);
      return true;
    }
    if (held.getItem() == Items.CLAY_BALL) {
      int clay = state.getValue(CLAY_LEVEL);
      if (clay < 4 && held.getCount() > 5) {
        held.shrink(5);
        world.setBlockState(pos, state.withProperty(CLAY_LEVEL, clay + 1));
        return true;
      }
    } else {
      return TileUtils.getTile(world, pos, TETurntable.class).map(tile -> {
        IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (cap != null) {
          ItemStack invStack = cap.getStackInSlot(0);
          if (invStack.isEmpty() && TETurntable.isPottery(held)) {
            ItemStack leftover = cap.insertItem(0, held.splitStack(1), false);
            ItemHandlerHelper.giveItemToPlayer(player, leftover);
            return true;
          } else if (!invStack.isEmpty() && held.isEmpty()) {
            ItemStack leftover = cap.extractItem(0, 1, false);
            ItemHandlerHelper.giveItemToPlayer(player, leftover);
            return true;
          }
        }
        return false;
      }).orElse(false);
    }
    return false;
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CLAY_LEVEL);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TETurntable();
  }
}
