package su.terrafirmagreg.modules.core.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockThatch extends BaseBlock {

  public BlockThatch() {
    super(BlockSettings.of()
      .material(Material.PLANTS)
      .registryKey("thatch")
      .sound(SoundType.PLANT)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .nonOpaque()
      .nonFullCube()
      .hardness(0.6F)
      .fireInfo(60, 20)
      .addOreDict("block", "straw")
    );
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return true;
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    // Player will take damage when hitting thatch if fall is over 13 blocks, fall damage is then set to 0.
    entityIn.fall((entityIn.fallDistance - 10), 1.0F); // TODO: 17/4/18 balance fall damage reduction.
    entityIn.fallDistance = 0;

    entityIn.motionX *= 0.1;
    entityIn.motionZ *= 0.1;

    // This makes the player way too slow
    //entityIn.setInWeb();
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }
}
