package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.client.render.TESRRockGemDisplay;
import su.terrafirmagreg.modules.rock.object.tile.TileRockGemDisplay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockRockStandGem extends BlockRock implements IProviderTile {

  public BlockRockStandGem(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
      .hardness(1.0f)
      .size(Size.LARGE)
      .weight(Weight.HEAVY)
      .nonFullCube()
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .nonOpaque();

    setDefaultState(blockState.getBaseState()
                              .withProperty(HORIZONTAL, EnumFacing.EAST)
                              .withProperty(UP, Boolean.TRUE));
  }

  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
               .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta))
               .withProperty(UP, meta / 4 % 2 != 0);
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    if (fromPos.equals(pos.up())) {
      if (worldIn.getBlockState(fromPos).getBlock() instanceof BlockAir) {
        state = state.withProperty(UP, Boolean.TRUE);
      } else {
        state = state.withProperty(UP, Boolean.FALSE);
      }
      worldIn.setBlockState(pos, state, 2);
    }
  }

  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileRockGemDisplay.class)
             .ifPresent(TileRockGemDisplay::onBreakBlock);
    super.breakBlock(worldIn, pos, state);
  }

  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      return TileUtils.getTile(worldIn, pos, TileRockGemDisplay.class)
                      .map(tileRockGemDisplay -> tileRockGemDisplay.onRightClick(playerIn, hand))
                      .orElse(true);
    }
    return true;
  }

  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
  }

  @Override
  public boolean hasComparatorInputOverride(IBlockState state) {
    return true;
  }

  @Override
  public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
    return TileUtils.getTile(world, pos, TileRockGemDisplay.class)
                    .map(tile -> (int) Math.floor(15 * ((double) tile.getSize() / (double) tile.getMaxStackSize())))
                    .orElse(0);
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, UP);
  }

  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(UP) ? 4 : 0);
  }

  @Override
  public Class<TileRockGemDisplay> getTileClass() {
    return TileRockGemDisplay.class;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRRockGemDisplay();
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileRockGemDisplay();
  }
}
