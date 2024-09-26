package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;

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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.client.model.modelvariants.ChopperModels;
import se.gory_moon.horsepower.client.renderer.TESRChopperHorse;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileHPBase;
import se.gory_moon.horsepower.util.Localization;
import se.gory_moon.horsepower.util.color.Colors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockChopperHorse extends BaseBlockHorse implements IProbeInfoAccessor {

  public static final PropertyDirection FACING = PropertyDirection.create("facing", Arrays.asList(EnumFacing.HORIZONTALS));
  public static final PropertyEnum<ChopperModels> PART = PropertyEnum.create("part", ChopperModels.class);
  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);

  public BlockChopperHorse() {
    super(Settings.of(Material.WOOD));
    getSettings()
      .hardness(5.0F)
      .resistance(5.0F)
      .sound(SoundType.WOOD)
      .harvestLevel(ToolClasses.AXE, 0);

    setRegistryName(Constants.CHOPPER_BLOCK);
    setTranslationKey(Constants.CHOPPER_BLOCK);
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileChopperHorse.class);
    if (tile != null) {
      tile.onBreakBlock(worldIn, pos, state);
    }
    worldIn.updateComparatorOutputLevel(pos, this);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byIndex(meta);

    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }

    return getDefaultState().withProperty(FACING, enumfacing).withProperty(PART, ChopperModels.BASE);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getIndex();
  }

  @Override
  public boolean hasCustomBreakingProgress(IBlockState state) {
    return true;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return COLLISION_AABB;
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      EnumFacing enumfacing = state.getValue(FACING);
      worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing)
                                      .withProperty(PART, ChopperModels.BASE), 2);
    }
  }

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
    return new BlockStateContainer(this, PART, FACING);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
    tooltip.add(Localization.ITEM.HORSE_CHOPPING.SIZE.translate(Colors.WHITE.toString(), Colors.LIGHTGRAY.toString()));
    tooltip.add(Localization.ITEM.HORSE_CHOPPING.LOCATION.translate());
    tooltip.add(Localization.ITEM.HORSE_CHOPPING.USE.translate());
  }

  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
    if (!((World) world).isRemote && pos.up().equals(neighbor) && !(world.getBlockState(neighbor).getBlock() instanceof BlockFiller)) {
      ((World) world).setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
                                          EntityLivingBase placer, EnumHand hand) {
    return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite())
                            .withProperty(PART, ChopperModels.BASE);
  }

  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return COLLISION_AABB;
  }

  @NotNull
  @Override
  public Class<TileChopperHorse> getTileClass() {
    return TileChopperHorse.class;
  }

  // The One Probe Integration
  @Optional.Method(modid = "theoneprobe")
  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
    var tile = TileUtils.getTile(world, data.getPos(), TileChopperHorse.class);

    if (tile != null) {
      double totalWindup = Configs.general.pointsForWindup > 0 ? Configs.general.pointsForWindup : 1;
      probeInfo.progress((long) ((((double) tile.getField(2)) / totalWindup) * 100L), 100L,
                         new ProgressStyle().prefix(Localization.TOP.WINDUP_PROGRESS.translate() + " ")
                                            .suffix("%"));
      if (tile.getField(0) > 1) {
        probeInfo.progress((long) ((((double) tile.getField(1)) / ((double) tile.getField(0))) * 100L), 100L,
                           new ProgressStyle().prefix(Localization.TOP.CHOPPING_PROGRESS.translate() + " ")
                                              .suffix("%"));
      }
    }
  }

  @Override
  public @Nullable TileChopperHorse createNewTileEntity(World worldIn, int meta) {
    return new TileChopperHorse();
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRChopperHorse();
  }
}
