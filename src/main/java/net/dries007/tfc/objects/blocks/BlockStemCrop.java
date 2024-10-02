package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.eerussianguy.firmalife.init.StemCrop;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropSimple;
import net.dries007.tfc.objects.te.TEStemCrop;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.BoolProp.WILD;
import static su.terrafirmagreg.data.Properties.EnumProp.FACING;

@MethodsReturnNonnullByDefault
public abstract class BlockStemCrop extends BlockCropSimple {


  public BlockStemCrop(StemCrop crop) {
    super(crop, false);
    setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.UP));
  }

  //static factory method is required because
  //superconstructor requires getStageProperty to work
  //but we can't set the property until after the superconstructor
  //this is the workaround.
  public static BlockStemCrop create(StemCrop crop) {
    PropertyInteger property = STAGE_MAP.get(crop.getMaxStage() + 1);
    return new BlockStemCrop(crop) {

      @Override
      public PropertyInteger getStageProperty() {
        return property;
      }
    };
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    //even though the existing stemcrops only show direction when fully grown, this is made available
    //so that subclasses can have directionality while growing if they want
    return TileUtils.getTile(worldIn, pos, TEStemCrop.class)
                    .map(tile -> state.withProperty(FACING, tile.getFruitDirection()))
                    .orElse(state);
  }

  @Override
  @SuppressWarnings("deprecation")
  public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    Vec3d offset = super.getOffset(state, worldIn, pos);
    double x = offset.x, z = offset.z;
    switch (state.getValue(FACING)) {
      case EAST:
        x = Math.abs(x);
        break;
      case WEST:
        x = -Math.abs(x);
        break;
      case NORTH:
        z = -Math.abs(z);
        break;
      case SOUTH:
        z = Math.abs(z);
        break;
      default:
        break;
    }
    return new Vec3d(x, 0.0D, z);
  }

  @Override
  public void grow(World world, BlockPos cropPos, IBlockState cropState, Random random) {
    if (world.isRemote) {return;}
    //if penultimate stage
    PropertyInteger stageProperty = getStageProperty();
    if (cropState.getProperties().containsKey(stageProperty) && cropState.getValue(stageProperty) == getCrop().getMaxStage() - 1) {
      TileUtils.getTile(world, cropPos, TEStemCrop.class).ifPresent(tile -> {
        EnumFacing fruitDirection = tile.getFruitDirection();
        BlockPos fruitPos = cropPos.offset(fruitDirection);
        StemCrop crop = (StemCrop) getCrop();
        if (crop.getCropBlock().canPlaceBlockAt(world, fruitPos)) {
          IBlockState fruitState = crop.getCropBlock()
                                       .getDefaultState()
                                       .withProperty(FACING, fruitDirection.getOpposite());
          world.setBlockState(fruitPos, fruitState);
        }
      });

    }
    super.grow(world, cropPos, cropState, random);

  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    super.onBlockAdded(worldIn, pos, state);
    //if adding a fully grown and wild crop
    if (state.getValue(getStageProperty()) == getCrop().getMaxStage() && state.getValue(WILD)) {
      TileUtils.getTile(worldIn, pos, TEStemCrop.class).ifPresent(tile -> {
        EnumFacing fruitDirection = tile.getFruitDirection();
        BlockPos targetPos = pos.offset(fruitDirection);
        StemCrop crop = (StemCrop) getCrop();

        //spawn fruit
        if (crop.getCropBlock().canPlaceBlockAt(worldIn, targetPos)) {
          worldIn.setBlockState(targetPos, crop.getCropBlock().getDefaultState().withProperty(FACING, fruitDirection.getOpposite()));

          //revert back a stage
        } else {
          worldIn.setBlockState(pos, state.withProperty(getStageProperty(), getCrop().getMaxStage() - 1));
        }
      });
    }
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, getStageProperty(), WILD, FACING);
  }

  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEStemCrop();
  }

  @Override
  public abstract PropertyInteger getStageProperty();

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block block, BlockPos npos) {
    super.neighborChanged(state, worldIn, pos, block, npos);

  }

}
