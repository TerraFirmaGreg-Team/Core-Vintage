package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderTile;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;
import su.terrafirmagreg.modules.rock.object.render.TESRRockGemDisplay;
import su.terrafirmagreg.modules.rock.object.tile.TileRockGemDisplay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
@Getter
public class BlockRockStandGem extends BaseBlock implements IRockEntry, IProviderTile {

  public static final String NAME = "stand_gem";
  protected final RockType type;

  public BlockRockStandGem(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .registryKey(type.getRegistryKey(NAME))
      .hardness(type.getHardness(1f))
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .tile(TileRockGemDisplay.class, new TESRRockGemDisplay())
      .hardness(1.0f)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.HEAVY))
      .nonFullCube()
      .nonOpaque()
      .addOreDict(NAME)
      .addOreDict(NAME, type)
    );

    this.type = type;
    setDefaultState(getBlockState().getBaseState()
      .withProperty(HORIZONTAL, EnumFacing.EAST)
      .withProperty(UP, Boolean.TRUE));
  }

  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
      .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta))
      .withProperty(UP, meta / 4 % 2 != 0);
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
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


  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileRockGemDisplay();
  }
}
