package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.device.object.tile.TilePressHorse;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Optional;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.client.model.modelvariants.PressModels;
import se.gory_moon.horsepower.client.renderer.TESRPress;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileHPBase;
import se.gory_moon.horsepower.util.Localization;
import se.gory_moon.horsepower.util.color.Colors;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockPress extends BaseBlockHorse implements IProbeInfoAccessor {

  public static final PropertyDirection FACING = PropertyDirection.create("facing", Arrays.asList(EnumFacing.HORIZONTALS));
  public static final PropertyEnum<PressModels> PART = PropertyEnum.create("part", PressModels.class);

  private static final AxisAlignedBB BOUND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D + 12D / 16D, 1.0D);
  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D + 3D / 16D, 1.0D);

  public BlockPress() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .hardness(5.0F)
      .resistance(5.0F)
      .sound(SoundType.WOOD)
      .harvestLevel(ToolClasses.AXE, 1);

    setRegistryName(Constants.PRESS_BLOCK);
    setTranslationKey(Constants.PRESS_BLOCK);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta))
                            .withProperty(PART, PressModels.BASE);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getHorizontalIndex();
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUND_AABB;
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      EnumFacing filled = state.getValue(FACING);
      worldIn.setBlockState(pos, state.withProperty(FACING, filled).withProperty(PART, PressModels.BASE), 2);
    }
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

    var tile = TileUtils.getTile(world, pos, TileHPBase.class);
    if (tile == null) {
      return;
    }
    tile.setForward(placer.getHorizontalFacing().getOpposite());
    super.onBlockPlacedBy(world, pos, state, placer, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, PART);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
    tooltip.add(Localization.ITEM.HORSE_PRESS.SIZE.translate(Colors.WHITE.toString(), Colors.LIGHTGRAY.toString()));
    tooltip.add(Localization.ITEM.HORSE_PRESS.LOCATION.translate());
    tooltip.add(Localization.ITEM.HORSE_PRESS.USE.translate());
  }

  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
    if (!((World) world).isRemote && pos.up().equals(neighbor) && !(world.getBlockState(neighbor)
                                                                         .getBlock() instanceof BlockFiller)) {
      ((World) world).setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
    return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PART, PressModels.BASE);
  }

  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return COLLISION_AABB;
  }

  // The One Probe Integration
  @Optional.Method(modid = "theoneprobe")
  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

    var tile = TileUtils.getTile(world, data.getPos(), TilePressHorse.class);
    if (tile != null) {
      probeInfo.progress(
        (long) ((((double) tile.getField(0)) / (float) (Configs.general.pointsForPress > 0 ? Configs.general.pointsForPress : 1)) *
                100L), 100L, new ProgressStyle().prefix(Localization.TOP.PRESS_PROGRESS.translate() + " ")
                                                .suffix("%"));
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {
      return true;
    }

    var tile = TileUtils.getTile(worldIn, pos);
    if (tile != null) {
      final IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
      if (fluidHandler != null && FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler)) {
        tile.markDirty();
        return false;
      }
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }

  @Nullable
  @Override
  public TilePressHorse createNewTileEntity(World worldIn, int meta) {
    return new TilePressHorse();
  }


  @Override
  public Class<TilePressHorse> getTileClass() {
    return TilePressHorse.class;
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRPress();
  }
}
