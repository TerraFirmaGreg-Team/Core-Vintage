package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;
import su.terrafirmagreg.modules.device.client.render.TESRLatexExtractor;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static net.minecraft.util.EnumFacing.NORTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.BASE;
import static su.terrafirmagreg.api.data.Properties.BoolProp.POT;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;
import static su.terrafirmagreg.api.data.Properties.IntProp.CUT;

@SuppressWarnings("deprecation")
public class BlockLatexExtractor extends BaseBlock implements IProviderTile {


  private static final AxisAlignedBB AABB_N = new AxisAlignedBB(0.1875D, 0.125D, 0.3125D, 0.8125D, 0.875D, 1.0D);
  private static final AxisAlignedBB AABB_S = new AxisAlignedBB(0.1875D, 0.125D, 0.0D, 0.8125D, 0.875D, 0.6875D);
  private static final AxisAlignedBB AABB_E = new AxisAlignedBB(0.0D, 0.125D, 0.1875D, 0.6875D, 0.875D, 0.8125D);
  private static final AxisAlignedBB AABB_W = new AxisAlignedBB(0.3125D, 0.125D, 0.1875D, 1.0D, 0.875D, 0.8125D);

  public BlockLatexExtractor() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("latex_extractor")
      .nonCube()
      .noItemBlock()
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .hardness(2.0F);

    setDefaultState(getBlockState().getBaseState()
      .withProperty(HORIZONTAL, NORTH)
      .withProperty(BASE, false)
      .withProperty(POT, false)
      .withProperty(CUT, 0));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return TileUtils.getTile(worldIn, pos, getTileClass())
      .map(tile -> state.withProperty(BASE, tile.hasBase())
        .withProperty(POT, tile.hasPot())
        .withProperty(CUT, tile.cutState())
      )
      .orElse(super.getActualState(state, worldIn, pos));
  }

  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (state.getValue(HORIZONTAL)) {
      case NORTH -> AABB_N;
      case SOUTH -> AABB_S;
      case EAST -> AABB_E;
      case WEST -> AABB_W;
      default -> FULL_BLOCK_AABB;
    };
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!(worldIn.getBlockState(pos.offset(state.getValue(HORIZONTAL).getOpposite())).getBlock() instanceof BlockLog)) {
      worldIn.destroyBlock(pos, false);
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, getTileClass()).ifPresent(tile -> {
      if (tile.cutState() > 0 && worldIn.getBlockState(pos.offset(state.getValue(HORIZONTAL).getOpposite())).getBlock() instanceof BlockLog) {
        worldIn.destroyBlock(pos.offset(state.getValue(HORIZONTAL).getOpposite()), true);
      }
      tile.onBreakBlock(worldIn, pos, state);
    });
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return TileUtils.getTile(world, pos, getTileClass()).map(tile -> {
      if (hand == EnumHand.MAIN_HAND) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1) {
          if (tile.makeCut()) {
            world.playSound(null, pos, SoundsDevice.LATEX_EXTRACTOR_TRUNK_SCRATH.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
          }
        } else if (!tile.hasPot() && tile.isValidPot(stack) && tile.addPot(stack)) {
          stack.shrink(1);
          world.playSound(null, pos, SoundsDevice.LATEX_EXTRACTOR_BOWL_FIT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          return true;
        } else if (!tile.hasBase() && tile.isValidBase(stack) && tile.addBase(stack)) {
          stack.shrink(1);
          world.playSound(null, pos, SoundsDevice.LATEX_EXTRACTOR_MOUNT_FIT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          return true;
        } else if (stack.isEmpty() && tile.hasPot()) {
          player.setHeldItem(hand, tile.removePot());
          world.playSound(null, pos, SoundsDevice.LATEX_EXTRACTOR_BOWL_GRAB.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          return true;
        } else if (stack.isEmpty() && tile.hasBase()) {
          player.setHeldItem(hand, tile.removeBase());
          world.playSound(null, pos, SoundsDevice.LATEX_EXTRACTOR_GROOVE_FIT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          return true;
        }
      }
      return false;
    }).orElse(super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, BASE, POT, CUT);
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileLatexExtractor();
  }

  @Override
  public Class<TileLatexExtractor> getTileClass() {
    return TileLatexExtractor.class;
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRLatexExtractor();
  }
}
