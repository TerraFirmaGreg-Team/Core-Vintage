package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Optional;


import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.client.model.modelvariants.HandGrindstoneModels;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileHandGrindstone;
import se.gory_moon.horsepower.util.Localization;
import se.gory_moon.horsepower.util.color.Colors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockHandGrindstone extends BlockHPBase implements IProbeInfoAccessor {

  public static final PropertyEnum<HandGrindstoneModels> PART = PropertyEnum.create("part", HandGrindstoneModels.class);

  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 10D / 16D, 15D / 16D);
  private static final AxisAlignedBB BOUNDING_AABB = new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 14D / 16D, 15D / 16D);

  public BlockHandGrindstone() {
    super(Settings.of(Material.ROCK));

    getSettings()
            .hardness(1.5F)
            .resistance(10F)
            .sound(SoundType.STONE)
            .harvestLevel(ToolClasses.PICKAXE, 1);

    setRegistryName(Constants.HAND_GRINDSTONE_BLOCK);
    setTranslationKey(Constants.HAND_GRINDSTONE_BLOCK);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
          float hitY, float hitZ) {

    if (player instanceof FakePlayer || player == null) {
      return true;
    }

    var tile = TileUtils.getTile(world, pos, TileHandGrindstone.class);
    if (tile != null && tile.canWork() && !player.isSneaking()) {
      if (!world.isRemote) {
        if (tile.turn()) {
          player.addExhaustion((float) Configs.general.grindstoneExhaustion);
        }
        return true;
      } else {
        return true;
      }
    }

    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }

  @Override
  public int getSlot(IBlockState state, float hitX, float hitY, float hitZ) {
    return -2;
  }

  @Override
  public void emptiedOutput(World world, BlockPos pos) {

  }

  @NotNull
  @Override
  public Class<TileHandGrindstone> getTileClass() {
    return TileHandGrindstone.class;
  }

  @Override
  public @Nullable TileHandGrindstone createNewTileEntity(World worldIn, int meta) {
    return new TileHandGrindstone();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public boolean hasCustomBreakingProgress(IBlockState state) {
    return true;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return COLLISION_AABB;
  }

  @Override
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return EMPTY_AABB;
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      worldIn.setBlockState(pos, state.withProperty(PART, HandGrindstoneModels.BASE), 2);
    }
  }

  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    world.setBlockState(pos, state.withProperty(PART, HandGrindstoneModels.BASE), 2);

    var tile = TileUtils.getTile(world, pos, TileHandGrindstone.class);
    if (tile == null) {
      return;
    }
    tile.setForward(placer.getAdjustedHorizontalFacing().getOpposite());
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, PART);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
    tooltip.add(Localization.ITEM.HAND_GRINDSTONE.INFO.translate("\n" + Colors.LIGHTGRAY));
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
    TileHandGrindstone tileEntity = (TileHandGrindstone) world.getTileEntity(data.getPos());
    if (tileEntity != null) {
      probeInfo.progress((long) ((((double) tileEntity.getField(1)) / ((double) tileEntity.getField(0))) * 100L), 100L, new ProgressStyle()
              .prefix(Localization.TOP.GRINDSTONE_PROGRESS.translate() + " ")
              .suffix("%"));
    }
  }
}
