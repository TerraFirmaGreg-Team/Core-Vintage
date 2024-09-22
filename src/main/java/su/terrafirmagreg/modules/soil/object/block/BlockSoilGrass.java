package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.spi.IGrass;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.CLAY;
import static su.terrafirmagreg.data.Properties.EAST;
import static su.terrafirmagreg.data.Properties.NORTH;
import static su.terrafirmagreg.data.Properties.SNOWY;
import static su.terrafirmagreg.data.Properties.SOUTH;
import static su.terrafirmagreg.data.Properties.WEST;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilGrass extends BlockSoil implements IProviderBlockColor, IGrass {


  public BlockSoilGrass(SoilBlockVariant variant, SoilType type) {
    super(Settings.of(Material.GRASS), variant, type);

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .sound(SoundType.PLANT)
            .hardness(2.1F)
            .randomTicks()
            .fallable(this, VERTICAL_AND_HORIZONTAL)
            .renderLayer(BlockRenderLayer.CUTOUT);

    setDefaultState(blockState.getBaseState()
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
            .withProperty(SNOWY, Boolean.FALSE)
            .withProperty(CLAY, Boolean.FALSE));

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
    pos = pos.add(0, -1, 0);
    Block blockUp = world.getBlockState(pos.up()).getBlock();
    return state
            .withProperty(NORTH, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
            .withProperty(EAST, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
            .withProperty(SOUTH, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
            .withProperty(WEST, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))))
            .withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
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
    if (block instanceof ISoilBlock soilBlock) {
      var soil = soilBlock.getType();

      if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
        worldIn.setBlockState(pos, BlocksSoil.DIRT.get(soil).getDefaultState());

      } else {
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
          for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

            if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
              return;
            }

            IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

            if (iblockstate1.getBlock() == BlocksSoil.DIRT.get(soil) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4
                    && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
              worldIn.setBlockState(blockpos, BlocksSoil.GRASS.get(soil).getDefaultState());
            }
          }
        }
      }
    }
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, SNOWY, CLAY);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    if (this.variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
      double d0 = (float) pos.getX() + rand.nextFloat();
      double d1 = (double) pos.getY() - 0.05D;
      double d2 = (float) pos.getZ() + rand.nextFloat();
      world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
    }
  }


  @Override
  public IBlockColor getBlockColor() {
    return GrassColorHandler::computeGrassColor;
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getBlockColor().colorMultiplier(this.getDefaultState(), null, null, i);
  }
}
