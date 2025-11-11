package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.framework.manager.content.provider.IProviderTile;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;
import su.terrafirmagreg.modules.wood.content.render.TESRWoodBarrel;
import su.terrafirmagreg.modules.wood.content.tile.TileWoodBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.BoolProp.SEALED;

/**
 * Barrel block. Can be filled with fluids (10 B), and one item stack. Performs barrel recipes. Sealed state is stored in block state and cached in TE, synced when updated via custom packet
 *
 * @see TileWoodBarrel
 * @see BarrelRecipe
 */
@SuppressWarnings("deprecation")
@Getter
public class BlockWoodBarrel extends BaseBlock implements IProviderTile, IWoodEntry {

  private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

  protected final WoodType type;

  public BlockWoodBarrel(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .customResource(type.getResource("barrel"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .addOreDict("barrel")
      .hardness(2F)
      .fireInfo(5, 20)
      .tile(TileWoodBarrel.class, new TESRWoodBarrel())
      .capability(stack ->
        CapabilityProviderSize.of(
          stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE,
          Weight.VERY_HEAVY,
          stack.getTagCompound() == null
        ))
      .nonCube()
    );

    this.type = type;
    setDefaultState(getBlockState().getBaseState()
      .withProperty(SEALED, false));
  }

  /**
   * Used to toggle the barrel seal state and update the tile entity, in the correct order
   */
  public static void toggleBarrelSeal(World world, BlockPos pos) {
    TileUtils.getTile(world, pos, TileWoodBarrel.class).ifPresent(tile -> {
      IBlockState state = world.getBlockState(pos);
      boolean previousSealed = state.getValue(SEALED);
      world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
      if (previousSealed) {
        tile.onUnseal();
      } else {
        tile.onSealed();
      }
    });
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(SEALED, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SEALED) ? 1 : 0;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUNDING_BOX;
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    if (!world.isRemote) {
      if (world.getBlockState(fromPos).getBlock() instanceof BlockRedstoneComparator) {
        return;
      }
      boolean powered = world.isBlockPowered(pos);
      if (powered || block.getDefaultState().canProvidePower()) {
        if (powered != state.getValue(SEALED)) {
          toggleBarrelSeal(world, pos);
        }
      }
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileWoodBarrel.class).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    worldIn.updateComparatorOutputLevel(pos, this);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack heldItem = playerIn.getHeldItem(hand);
    var tile = TileUtils.getTile(worldIn, pos, TileWoodBarrel.class);
    return tile.map(tileWoodBarrel -> {
      if (heldItem.isEmpty() && playerIn.isSneaking()) {
        worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
        toggleBarrelSeal(worldIn, pos);
        return true;
      } else if (heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !state.getValue(SEALED)) {
        IFluidHandler fluidHandler = tile.get().getCapability(
          CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (fluidHandler != null) {
          if (!worldIn.isRemote) {
            FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler);
            tile.get().markDirty();
          }
          return true;
        }
      }
      if (!worldIn.isRemote) {
        GuiHandler.openGui(worldIn, pos, playerIn);
      }
      return true;
    }).orElse(false);

  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (!worldIn.isRemote && stack.getTagCompound() != null) {
      var tile = TileUtils.getTile(worldIn, pos, TileWoodBarrel.class);
      tile.ifPresent(tileWoodBarrel -> {
        worldIn.setBlockState(pos, state.withProperty(SEALED, true));
        tileWoodBarrel.loadFromItemStack(stack);
      });
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean hasComparatorInputOverride(IBlockState state) {
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
  public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
    return state.getValue(SEALED) ? 15 : 0;
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SEALED);
  }

  /**
   * Handle drops via {@link this#breakBlock(World, BlockPos, IBlockState)}
   */
  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
  }

  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
    // Unseal the vessel if an explosion destroys it, so it drops it's contents
    world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
    super.onBlockExploded(world, pos, explosion);
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    ItemStack stack = new ItemStack(state.getBlock());
    var tile = TileUtils.getTile(world, pos, TileWoodBarrel.class);
    return tile.map(tileWoodBarrel -> {
      if (tileWoodBarrel.isSealed()) {
        tileWoodBarrel.saveToItemStack(stack);
      }
      return stack;
    }).orElse(stack);
  }


  @Nullable
  @Override
  public TileWoodBarrel createNewTileEntity(World worldIn, int meta) {
    return new TileWoodBarrel();
  }
}
