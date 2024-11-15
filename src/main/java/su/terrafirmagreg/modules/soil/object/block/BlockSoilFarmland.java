package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.spi.IDirt;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilFarmland extends BlockFarmland implements ISoilBlock, IProviderBlockColor {

  public static final int[] TINT = new int[]{
    0xffffffff,
    0xffe7e7e7,
    0xffd7d7d7,
    0xffc7c7c7,
    0xffb7b7b7,
    0xffa7a7a7,
    0xff979797,
    0xff878787,
    };
  public static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

  protected final Settings settings;
  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoilFarmland(SoilBlockVariant variant, SoilType type) {

    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.GROUND);

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .ignoresProperties(MOISTURE)
      .sound(SoundType.GROUND)
      .useNeighborBrightness()
      .hardness(2.0F)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .fallable(this, variant.getSpecification())
      .oreDict(variant)
      .oreDict(variant, type);

    setDefaultState(blockState.getBaseState()
                              .withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt
  }

  @Override
  public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return (side != EnumFacing.DOWN && side != EnumFacing.UP);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(ItemsSoil.PILE.get(type));
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    int current = state.getValue(MOISTURE);
    int target = world.isRainingAt(pos.up()) ? 7 : getWaterScore(world, pos);

    if (current < target) {
      if (current < 7) {
        world.setBlockState(pos, state.withProperty(MOISTURE, current + 1), 2);
      }
    } else if (current > target || target == 0) {
      if (current > 0) {
        world.setBlockState(pos, state.withProperty(MOISTURE, current - 1), 2);
      } else if (!hasCrops(world, pos)) {
        IDirt.turnToDirt(world, pos);
      }
    }
  }

  public int getWaterScore(IBlockAccess world, BlockPos pos) {
    final int hRange = 7;
    float score = 0;
    for (BlockPos.MutableBlockPos i : BlockPos.getAllInBoxMutable(pos.add(-hRange, -1, -hRange), pos.add(hRange, 2, hRange))) {
      BlockPos diff = i.subtract(pos);
      float hDist = MathHelper.sqrt(diff.getX() * diff.getX() + diff.getZ() * diff.getZ());
      if (hDist > hRange) {
        continue;
      }
      if (world.getBlockState(i).getMaterial() != Material.WATER) {
        continue;
      }
      score += ((hRange - hDist) / (float) hRange);
    }
    return score > 1 ? 7 : Math.round(score * 7);
  }

  private boolean hasCrops(World worldIn, BlockPos pos) {
    Block block = worldIn.getBlockState(pos.up()).getBlock();
    return block instanceof IPlantable plantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, plantable);
  }

  public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
    if (ForgeHooks.onFarmlandTrample(worldIn, pos, BlocksSoil.DIRT.get(type).getDefaultState(), fallDistance, entityIn)) {
      IDirt.turnToDirt(worldIn, pos);
    }

    entityIn.fall(fallDistance, 1.0F);
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    if (fromPos.getY() == pos.getY() + 1) {
      IBlockState up = world.getBlockState(fromPos);
      if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
        IDirt.turnToDirt(world, pos);
      }
    }
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
      IDirt.turnToDirt(worldIn, pos);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    switch (side) {
      case UP:
        return true;
      case NORTH:
      case SOUTH:
      case WEST:
      case EAST:
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (iblockstate.isOpaqueCube()) {
          return false;
        }
        if (block instanceof BlockFarmland || block instanceof BlockGrassPath) {
          return false;
        }
      default:
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.PILE.get(type);
  }

  @Override
  public IBlockColor getBlockColor() {
    return (s, w, p, i) -> BlockSoilFarmland.TINT[s.getValue(MOISTURE)];
  }

}
