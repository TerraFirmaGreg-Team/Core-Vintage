package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SuppressWarnings("deprecation")
public abstract class BaseBlockGroundcover extends BaseBlock {

  protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.9, 0.2, 0.9);

  public BaseBlockGroundcover(Settings settings) {
    super(settings);

    getSettings()
      .size(Size.SMALL)
      .weight(Weight.LIGHT)
      .hardness(0.1F)
      .nonCube()
      .replaceable();
  }

  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
  }

  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());

    if (state.getBlock() == this) {
      return (BlockHelper.isGround(soil) || soil.isFullBlock()) && !(BlockHelper.isSaltWater(soil) || BlockHelper.isFreshWater(soil));
    }
    return this.canSustain(soil);
  }

  protected boolean canSustain(IBlockState state) {
    return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB.offset(state.getOffset(source, pos));
  }

  @Override
  public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
    return true;
  }

  @Override
  public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    return true;
  }
}
