package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import com.eerussianguy.firmalife.registry.BlocksFL;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.IBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.te.TEHangingPlanter;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.GROWN;

@MethodsReturnNonnullByDefault

public class BlockBushTrellis extends BlockTrellis {

  private static final Map<IBerryBush, BlockBushTrellis> MAP = new HashMap<>();
  private final IBerryBush bush;

  public BlockBushTrellis(IBerryBush bush) {
    super();
    this.bush = bush;
    if (MAP.put(bush, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setTickRandomly(true);
    setDefaultState(blockState.getBaseState().withProperty(GROWN, false));
  }

  public static BlockBushTrellis get(IBerryBush bush) {
    return MAP.get(bush);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(GROWN, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(GROWN) ? 1 : 0;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (world.isRemote) {return;}

    TileUtils.getTile(world, pos, TEHangingPlanter.class).ifPresent(tile -> {
      boolean grown = state.getValue(GROWN);
      boolean climateValid = tile.isClimateValid();
      if (!climateValid) {
        tile.resetCounter();
      } else if (tile.getTicksSinceUpdate() >= (ICalendar.TICKS_IN_DAY * 30)) {
        if (!grown) {
          world.setBlockState(pos, state.withProperty(GROWN, true));
        } else {
          BlockPos upPos = pos.up();
          Block upBlock = world.getBlockState(upPos).getBlock();
          if (tile.getTicksSinceUpdate() >= (ICalendar.TICKS_IN_DAY * 50) && upBlock instanceof BlockTrellis &&
              !(upBlock instanceof BlockBushTrellis)) {
            world.setBlockState(upPos, getDefaultState());
            TileUtils.getTile(world, upPos, TEHangingPlanter.class).ifPresent(TEHangingPlanter::resetCounter);
            tile.resetCounter();
          }
        }
      }
    });

  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX,
                                  float hitY, float hitZ) {
    if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
      Item held = player.getHeldItem(hand).getItem();
      if (held instanceof ItemBlock && ((ItemBlock) held).getBlock() instanceof BlockTrellis) {
        return false;
      }
      if (state.getValue(GROWN)) {
        ItemHandlerHelper.giveItemToPlayer(player, bush.getFoodDrop());
        world.setBlockState(pos, state.withProperty(GROWN, false));
        TileUtils.getTile(world, pos, TEHangingPlanter.class).ifPresent(TEHangingPlanter::resetCounter);
        return true;
      } else if (player.isSneaking()) {
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(BlockBerryBush.get(bush)));
        world.setBlockState(pos, BlocksFL.TRELLIS.getDefaultState());
        TileUtils.getTile(world, pos, TEHangingPlanter.class).ifPresent(TEHangingPlanter::resetCounter);
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TileUtils.getTile(worldIn, pos, TEHangingPlanter.class).ifPresent(TEHangingPlanter::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, GROWN);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEHangingPlanter();
  }
}
