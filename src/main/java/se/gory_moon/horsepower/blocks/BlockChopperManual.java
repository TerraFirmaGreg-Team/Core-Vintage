package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
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
import se.gory_moon.horsepower.tileentity.TileHPBase;
import se.gory_moon.horsepower.util.Localization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockChopperManual extends BaseBlock implements IProbeInfoAccessor, IProviderTile {

  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 6D / 16D, 1.0D);

  public BlockChopperManual() {
    super(Settings.of(Material.WOOD));
    getSettings()
      .nonFullCube()
      .nonOpaque()
      .size(Size.LARGE)
      .weight(Weight.HEAVY)
      .hardness(2.0F)
      .resistance(5.0F)
      .sound(SoundType.WOOD)
      .harvestLevel(ToolClasses.AXE, 0);

    setRegistryName(Constants.HAND_CHOPPING_BLOCK);
    setTranslationKey(Constants.HAND_CHOPPING_BLOCK);
  }

  public static boolean isValidChoppingTool(ItemStack stack, EntityPlayer player) {
    return !stack.isEmpty() && ((stack.getItem().getHarvestLevel(stack, "axe", player, null) > -1) || isChoppingToolWhitelisted(stack));
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
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileChopperManual.class);
    tile.ifPresent(tileChopperManual -> tileChopperManual.onBreakBlock(worldIn, pos, state));
    worldIn.updateComparatorOutputLevel(pos, this);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (playerIn instanceof FakePlayer) {return true;}

    ItemStack stack = playerIn.getHeldItem(hand);
    return TileUtils.getTile(worldIn, pos, TileChopperManual.class).map(tile -> {
      if (!stack.isEmpty() && tile.isItemValidForSlot(0, stack)) {
        ItemStack itemStack = tile.getStackInSlot(0);
        boolean flag = false;

        if (itemStack.isEmpty()) {
          tile.setInventorySlotContents(0, stack.copy());
          stack.setCount(stack.getCount() - tile.getInventoryStackLimit(stack));
          flag = true;
        } else if (TileHPBase.canCombine(itemStack, stack)) {
          int i = Math.min(tile.getInventoryStackLimit(stack), stack.getMaxStackSize()) - itemStack.getCount();
          int j = Math.min(stack.getCount(), i);
          stack.shrink(j);
          itemStack.grow(j);
          flag = j > 0;
        }

        if (flag) {
          return true;
        }
      }

      tile.markDirty();
      return true;
    }).orElse(false);


  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    super.onBlockHarvested(worldIn, pos, state, player);

    if (!player.isCreative() && !worldIn.isRemote) {
      TileUtils.getTile(worldIn, pos, TileChopperManual.class).ifPresent(tile -> InventoryHelper.dropInventoryItems(worldIn, pos, tile.getInventory()));
    }
  }

  @Override
  public boolean removedByPlayer(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player, boolean willHarvest) {
    // we pull up a few calls to this point in time because we still have the TE here
    // the execution otherwise is equivalent to vanilla order
    this.onPlayerDestroy(world, pos, state);
    onBlockHarvested(world, pos, state, player);
    if (willHarvest) {
      TileUtils.getTile(world, pos).ifPresent(tile -> this.harvestBlock(world, player, pos, state, tile, player.getHeldItemMainhand()));
    }
    world.setBlockToAir(pos);
    // return false to prevent the above called functions to be called again
    return false;
  }

  @Override
  @NotNull
  public Class<TileChopperManual> getTileClass() {
    return TileChopperManual.class;
  }

  @Override
  public @Nullable TileChopperManual createNewTileEntity(World worldIn, int meta) {
    return new TileChopperManual();
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
    return TileUtils.getTile(world, pos, TileChopperManual.class).map(tile -> {
      ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
      if (isValidChoppingTool(heldItem, player)) {
        if (tile.canWork()) {
          return -1f;
        }
      }
      return null;
    }).orElseGet(() -> super.getPlayerRelativeBlockHardness(state, player, world, pos));
  }

  @Override
  public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
    if (player instanceof FakePlayer || player == null) {
      return;
    }
    TileUtils.getTile(world, pos, TileChopperManual.class).ifPresent(tile -> {
      ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
      if (isValidChoppingTool(held, player)) {
        if (tile.chop(player, held)) {
          player.addExhaustion((float) Configs.general.choppingblockExhaustion);
          if (Configs.general.shouldDamageAxe) {
            held.damageItem(1, player);
          }
        }
      }
    });
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

    TileUtils.getTile(world, data.getPos(), TileChopperManual.class).ifPresent(tile -> {
      probeInfo.progress((long) ((((double) tile.getField(1)) / ((double) tile.getField(0))) * 100L), 100L,
                         new ProgressStyle().prefix(Localization.TOP.CHOPPING_PROGRESS.translate() + " ").suffix("%"));
    });
  }
}
