package su.terrafirmagreg.modules.agriculture.object.block;

import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushType;
import su.terrafirmagreg.modules.agriculture.init.BlocksAgriculture;
import su.terrafirmagreg.modules.agriculture.object.tile.TileBushTrellis;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.FRUITING;

@Getter
@SuppressWarnings("deprecation")
public class BlockBushTrellis extends BlockTrellis implements IProviderTile {

  protected final BerryBushType type;


  public BlockBushTrellis(BerryBushType type) {

    this.type = type;

    getSettings()
      .registryKey("agriculture/bush_trellis/" + type)
      .randomTicks();

    setDefaultState(blockState.getBaseState().withProperty(FRUITING, false));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(FRUITING, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FRUITING) ? 1 : 0;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (world.isRemote) {return;}

    TileUtils.getTile(world, pos, TileBushTrellis.class).ifPresent(tile -> {
      boolean grown = state.getValue(FRUITING);
      boolean climateValid = tile.isClimateValid();
      if (!climateValid) {
        tile.resetCounter();
      } else if (tile.getTicksSinceUpdate() >= (ICalendar.TICKS_IN_DAY * 30)) {
        if (!grown) {
          world.setBlockState(pos, state.withProperty(FRUITING, true));
        } else {
          BlockPos upPos = pos.up();
          Block upBlock = world.getBlockState(upPos).getBlock();
          if (tile.getTicksSinceUpdate() >= (ICalendar.TICKS_IN_DAY * 50) && upBlock instanceof BlockBushTrellis && !(upBlock instanceof BlockBushTrellis)) {
            world.setBlockState(upPos, getDefaultState());
            TileUtils.getTile(world, upPos, TileBushTrellis.class).ifPresent(TileBushTrellis::resetCounter);
            tile.resetCounter();
          }
        }
      }
    });
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
      Item held = player.getHeldItem(hand).getItem();
      if (held instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockBushTrellis) {
        return false;
      }
      if (state.getValue(FRUITING)) {
        ItemHandlerHelper.giveItemToPlayer(player, type.getFoodDrop());
        world.setBlockState(pos, state.withProperty(FRUITING, false));
        TileUtils.getTile(world, pos, TileBushTrellis.class).ifPresent(TileBushTrellis::resetCounter);
        return true;
      } else if (player.isSneaking()) {
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(BlocksAgriculture.BERRY_BUSH.get(type)));
        world.setBlockState(pos, BlocksAgriculture.TRELLIS.getDefaultState());
        TileUtils.getTile(world, pos, TileBushTrellis.class).ifPresent(TileBushTrellis::resetCounter);
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TileUtils.getTile(worldIn, pos, TileBushTrellis.class).ifPresent(TileBushTrellis::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FRUITING);
  }

  @Override
  public @Nullable TileBushTrellis createNewTileEntity(World worldIn, int meta) {
    return new TileBushTrellis();
  }

  @Override
  public Class<TileBushTrellis> getTileClass() {
    return TileBushTrellis.class;
  }
}
