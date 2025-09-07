package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.helper.GrassColorHelper;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IGrassBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;
import su.terrafirmagreg.modules.soil.content.block.spi.BlockSoil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;
import static su.terrafirmagreg.api.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.api.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.SNOWY;
import static su.terrafirmagreg.api.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.WEST;
import static su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilGrass extends BlockSoil implements IProviderBlockColor, IGrassBlock {


  public BlockSoilGrass(SoilType type) {
    super(type, BlockSettings.of()
      .material(Material.GRASS)
      .registryKey(type.getRegistryKey("grass"))
      .tag(Tags.GRASS)
      .sound(SoundType.PLANT)
      .hardness(2.1F)
      .randomTicks()
      .renderLayer(BlockRenderLayer.CUTOUT)
    );

    setDefaultState(getBlockState().getBaseState()
      .withProperty(NORTH, Boolean.FALSE)
      .withProperty(EAST, Boolean.FALSE)
      .withProperty(SOUTH, Boolean.FALSE)
      .withProperty(WEST, Boolean.FALSE)
      .withProperty(SNOWY, Boolean.FALSE)
      .withProperty(CLAY, Boolean.FALSE));

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL);
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
    pos = pos.add(0, -1, 0);
    Block blockUp = world.getBlockState(pos.up()).getBlock();
    return state
      .withProperty(NORTH, BlockHelper.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
      .withProperty(EAST, BlockHelper.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
      .withProperty(SOUTH, BlockHelper.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
      .withProperty(WEST, BlockHelper.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))))
      .withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
  }

  @Override
  public boolean isTopSolid(IBlockState state) {
    return super.isTopSolid(state);
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
    if (world.isRemote) {
      return;
    }
    spreadGrass(world, pos, state, rand);
    super.randomTick(world, pos, state, rand);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (worldIn.isRemote) {
      return;
    }

    if (!worldIn.isAreaLoaded(pos, 3)) {
      return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
    }

    Block block = worldIn.getBlockState(pos).getBlock();
    if (block instanceof IType<?> type && type.getType() instanceof SoilType soilType) {

      if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
        worldIn.setBlockState(pos, BlocksSoil.DIRT.get(soilType).getDefaultState());

      } else {
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
          for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

            if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
              return;
            }

            IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

            if (iblockstate1.getBlock() == BlocksSoil.DIRT.get(soilType) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
              worldIn.setBlockState(blockpos, BlocksSoil.GRASS.get(soilType).getDefaultState());
            }
          }
        }
      }

    }
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(CLAY) ? Items.CLAY_BALL : ItemsSoil.PILE.get(type);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, SNOWY, CLAY);
  }


  @Override
  public IBlockColor getBlockColor() {
    return GrassColorHelper::computeGrassColor;
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getBlockColor().colorMultiplier(this.getDefaultState(), null, null, i);
  }

  @Override
  public IBlockState getDirt() {
    return BlocksSoil.DIRT.get(type).getDefaultState();
  }
}
