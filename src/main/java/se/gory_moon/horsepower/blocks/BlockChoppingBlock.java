package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
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
import se.gory_moon.horsepower.HPEventHandler;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileManualChopper;
import se.gory_moon.horsepower.util.Localization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockChoppingBlock extends BlockHPChoppingBase implements IProbeInfoAccessor {

  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 6D / 16D, 1.0D);

  public BlockChoppingBlock() {

    getSettings()
            .hardness(2.0F)
            .resistance(5.0F);

    setRegistryName(Constants.HAND_CHOPPING_BLOCK);
    setTranslationKey(Constants.HAND_CHOPPING_BLOCK);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
          float hitY, float hitZ) {
    return player instanceof FakePlayer || player == null || super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }

  @Override
  public void emptiedOutput(World world, BlockPos pos) {
  }

  @Override
  @NotNull
  public Class<TileManualChopper> getTileClass() {
    return TileManualChopper.class;
  }

  @Override
  public @Nullable TileManualChopper createNewTileEntity(World worldIn, int meta) {
    return new TileManualChopper();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return COLLISION_AABB;
  }

  @Override
  public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
    var tile = TileUtils.getTile(world, pos, TileManualChopper.class);
    if (tile != null) {
      ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
      if (isValidChoppingTool(heldItem, player)) {
        if (tile.canWork()) {
          return -1;
        }
      }
    }

    return super.getPlayerRelativeBlockHardness(state, player, world, pos);
  }

  public static boolean isValidChoppingTool(ItemStack stack, EntityPlayer player) {
    return !stack.isEmpty() && ((stack.getItem()
            .getHarvestLevel(stack, "axe", player, null) > -1) || isChoppingToolWhitelisted(stack));
  }

  private static boolean isChoppingToolWhitelisted(ItemStack stack) {
    for (ItemStack itemStack : HPEventHandler.choppingAxes.keySet()) {
      if (ItemStack.areItemsEqualIgnoreDurability(itemStack, stack)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
    if (player instanceof FakePlayer || player == null) {
      return;
    }
    var tile = TileUtils.getTile(world, pos, TileManualChopper.class);
    if (tile != null) {
      ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
      if (isValidChoppingTool(held, player)) {
        if (tile.chop(player, held)) {
          player.addExhaustion((float) Configs.general.choppingblockExhaustion);
          if (Configs.general.shouldDamageAxe) {
            held.damageItem(1, player);
          }
        }
      }
    }
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

    var tile = TileUtils.getTile(world, data.getPos(), TileManualChopper.class);
    if (tile != null) {
      probeInfo.progress((long) ((((double) tile.getField(1)) / ((double) tile.getField(0))) * 100L), 100L,
              new ProgressStyle().prefix(Localization.TOP.CHOPPING_PROGRESS.translate() + " ")
                      .suffix("%"));
    }
  }
}
