package net.dries007.horsepower.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockDirectional;
import su.terrafirmagreg.api.registry.provider.IProviderTile;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.ProbeHitData;
import net.dries007.horsepower.tileentity.TileFiller;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.DIRECTIONAL;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockFiller extends BaseBlockDirectional implements IProbeInfoAccessor, IProviderTile {


  public BlockFiller(Settings settings, String name) {
    super(settings);

    getSettings()
      .nonCube()
      .isSuffocating()
      .resistance(5.0F)
      .hardness(5.0F);

    setRegistryName(name + "filler");
  }


  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.INVISIBLE;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos0) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (world instanceof World && validateFilled((World) world, state1, pos0)) {
      return state1.getBlock().getBoundingBox(state1, world, pos);
    } else {
      return super.getBoundingBox(state, world, pos0);
    }
  }

  @Override
  public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos0, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                    @Nullable Entity entityIn, boolean isActualState) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (validateFilled(world, state1, pos0)) {
      state1.getBlock().addCollisionBoxToList(state1, world, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public final AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
    BlockPos offset = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState iBlockState = world.getBlockState(offset);
    if (validateFilled(world, iBlockState, pos)) {
      return iBlockState.getBlock().getSelectedBoundingBox(iBlockState, world, offset);
    } else {
      return super.getSelectedBoundingBox(state, world, pos);
    }
  }

  @Override
  public void onPlayerDestroy(World world, BlockPos pos0, IBlockState state) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (validateFilled(world, state1, pos0)) {
      state1.getBlock().onPlayerDestroy(world, pos, world.getBlockState(pos));
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return null;
  }

  @Override
  public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
  }

  @Override
  public final RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end) {
    BlockPos p = pos.offset(blockState.getValue(DIRECTIONAL));
    IBlockState state = world.getBlockState(p);
    if (validateFilled(world, state, pos)) {
      RayTraceResult trace = state.getBlock().collisionRayTrace(state, world, p, start, end);
      return trace != null ? new RayTraceResult(trace.typeOfHit, trace.hitVec, trace.sideHit, pos) : trace;
    } else {
      return super.collisionRayTrace(blockState, world, pos, start, end);
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float x, float y, float z) {
    BlockPos p = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(p);
    if (validateFilled(world, state1, pos)) {
      return state1.getBlock().onBlockActivated(world, p, state1, playerIn, hand, facing, x, y, z);
    } else {
      return super.onBlockActivated(world, p, state, playerIn, hand, facing, x, y, z);
    }
  }

  @Override
  public void onEntityWalk(World world, BlockPos pos0, Entity entityIn) {
    BlockPos pos = pos0.offset(world.getBlockState(pos0).getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (validateFilled(world, state1, pos0)) {
      state1.getBlock().onEntityWalk(world, pos, entityIn);
    }
  }

  @Override
  public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos0, EnumFacing side) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (world instanceof World && validateFilled((World) world, state1, pos0)) {
      return state1.getBlock().getWeakPower(state1, world, pos, side);
    } else {
      return super.getWeakPower(state, world, pos0, side);
    }
  }

  @Override
  public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos0, EnumFacing side) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (world instanceof World && validateFilled((World) world, state1, pos0)) {
      return state1.getBlock().getStrongPower(state1, world, pos, side);
    } else {
      return super.getStrongPower(state, world, pos0, side);
    }
  }

  @Override
  public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
    BlockPos offset = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(offset);
    if (validateFilled(world, state1, pos)) {
      return state1.getBlock().getItem(world, offset, state1);
    } else {
      return super.getItem(world, pos, state);
    }
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    BlockPos offset = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(offset);
    if (validateFilled(world, state1, pos)) {
      state1.getBlock().removedByPlayer(world.getBlockState(offset), world, offset, player, willHarvest);
    }
    return super.removedByPlayer(state, world, pos, player, willHarvest);
  }

  @Override
  public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
    BlockPos offset = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(offset);
    if (world instanceof World && validateFilled((World) world, state1, pos)) {
      return state1.getBlock().canConnectRedstone(state1, world, offset, side);
    } else {
      return super.canConnectRedstone(state, world, pos, side);
    }
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    BlockPos offset = pos.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(offset);
    if (validateFilled(world, state1, pos)) {
      return state1.getBlock().getPickBlock(state1, target, world, offset, player);
    } else {
      return super.getPickBlock(state, target, world, pos, player);
    }
  }

  @Override
  public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos0, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState iBlockState = world.getBlockState(pos);
    if (!validateFilled(world, iBlockState, pos0)) {
      return true;
    }
    boolean flag = iBlockState.getBlock().addLandingEffects(iBlockState, world, pos, iblockstate, entity, numberOfParticles);
    if (!flag) {
      world.spawnParticle(EnumParticleTypes.BLOCK_DUST, pos0.getX() + 0.5, pos0.getY() + 1, pos0.getZ() + 0.5, numberOfParticles,
                          0.0D, 0.0D, 0.0D, 0.15000000596046448D, Block.getStateId(iBlockState));
    }
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
    BlockPos pos = target.getBlockPos().offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (!validateFilled(world, state1, target.getBlockPos())) {
      return true;
    }
    RayTraceResult target1 = new RayTraceResult(target.typeOfHit, target.hitVec.subtract(0, 1, 0), target.sideHit, pos);
    boolean flag = state1.getBlock().addHitEffects(state1, world, target1, manager);
    if (!flag) {
      Minecraft.getMinecraft().effectRenderer.addBlockHitEffects(pos, target.sideHit);
    }
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean addDestroyEffects(World world, BlockPos pos0, ParticleManager manager) {
    BlockPos pos = pos0.offset(world.getBlockState(pos0).getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (!validateFilled(world, state1, pos0)) {
      return true;
    }
    boolean flag = state1.getBlock().addDestroyEffects(world, pos, manager);
    if (!flag) {
      Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(pos, state1);
    }
    return true;
  }

  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
    IBlockState state = world.getBlockState(pos);
    if (world instanceof World && !((World) world).isRemote && pos.offset(state.getValue(DIRECTIONAL)).equals(neighbor)) {
      validateFilled((World) world, world.getBlockState(neighbor), pos);
    }
  }

  @Override
  public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos0, EnumFacing side) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (world instanceof World && validateFilled((World) world, state1, pos0)) {
      return state1.getBlock().shouldCheckWeakPower(state1, world, pos, side);
    } else {
      return super.shouldCheckWeakPower(state, world, pos0, side);
    }
  }

  @Override
  public SoundType getSoundType(IBlockState state, World world, BlockPos pos0, @Nullable Entity entity) {
    BlockPos pos = pos0.offset(state.getValue(DIRECTIONAL));
    IBlockState state1 = world.getBlockState(pos);
    if (validateFilled(world, state1, pos0)) {
      return state1.getBlock().getSoundType(state1, world, pos, entity);
    } else {
      return super.getSoundType(state, world, pos, entity);
    }
  }

  private boolean validateFilled(World world, IBlockState state, BlockPos pos) {
    if (state.getBlock() instanceof BlockChopperHorse || state.getBlock() instanceof BlockPress) {
      return true;
    } else {
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      return false;
    }
  }


  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileFiller();
  }

  public Class<TileFiller> getTileClass() {
    return TileFiller.class;
  }

  // The One Probe Integration
  @Optional.Method(modid = "theoneprobe")
  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
    BlockPos pos = data.getPos().offset(blockState.getValue(DIRECTIONAL));
    IBlockState state = world.getBlockState(pos);
    if (validateFilled(world, state, data.getPos()) && state.getBlock() instanceof IProbeInfoAccessor) {
      ((IProbeInfoAccessor) state.getBlock()).addProbeInfo(mode, probeInfo, player, world, state,
                                                           new ProbeHitData(pos, data.getHitVec(), data.getSideHit(), data.getPickBlock()));
    }
  }
}
