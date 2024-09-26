package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodBarrel;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.SEALED;

/**
 * Barrel block. Can be filled with fluids (10 B), and one item stack. Performs barrel recipes. Sealed state is stored in block state and cached in TE, synced
 * when updated via custom packet
 *
 * @see TileWoodBarrel
 * @see BarrelRecipe
 */
@SuppressWarnings("deprecation")
public class BlockWoodBarrel extends BlockWood implements IProviderTile {

  private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D,
                                                                      1.0D, 0.875D);

  public BlockWoodBarrel(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

    getSettings()
      .hardness(2F)
      .weight(Weight.VERY_HEAVY)
      .nonCube();

    setDefaultState(blockState.getBaseState()
                              .withProperty(SEALED, false));
  }

  /**
   * Used to toggle the barrel seal state and update the tile entity, in the correct order
   */
  public static void toggleBarrelSeal(World world, BlockPos pos) {
    var tile = TileUtils.getTile(world, pos, TileWoodBarrel.class);
    if (tile != null) {
      IBlockState state = world.getBlockState(pos);
      boolean previousSealed = state.getValue(SEALED);
      world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
      if (previousSealed) {
        tile.onUnseal();
      } else {
        tile.onSealed();
      }
    }
  }

  @Override
  public Size getSize(ItemStack stack) {
    return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE;
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return stack.getTagCompound() == null;
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
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block,
                              BlockPos fromPos) {
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
    var tile = TileUtils.getTile(worldIn, pos, TileWoodBarrel.class);
    if (tile != null) {
      tile.onBreakBlock(worldIn, pos, state);
    }
    worldIn.updateComparatorOutputLevel(pos, this);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                  EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack heldItem = playerIn.getHeldItem(hand);
    var tile = TileUtils.getTile(worldIn, pos, TileWoodBarrel.class);
    if (tile != null) {
      if (heldItem.isEmpty() && playerIn.isSneaking()) {
        worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F,
                          0.85F);
        toggleBarrelSeal(worldIn, pos);
        return true;
      } else if (heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                 && !state.getValue(SEALED)) {
        IFluidHandler fluidHandler = tile.getCapability(
          CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (fluidHandler != null) {
          if (!worldIn.isRemote) {
            FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler);
            tile.markDirty();
          }
          return true;
        }
      } else {
        if (!worldIn.isRemote) {
          GuiHandler.openGui(worldIn, pos, playerIn);
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                              EntityLivingBase placer, ItemStack stack) {
    if (!worldIn.isRemote && stack.getTagCompound() != null) {
      var tile = TileUtils.getTile(worldIn, pos, TileWoodBarrel.class);
      if (tile != null) {
        worldIn.setBlockState(pos, state.withProperty(SEALED, true));
        tile.loadFromItemStack(stack);
      }
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
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos,
                       IBlockState state, int fortune) {
  }

  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
    // Unseal the vessel if an explosion destroys it, so it drops it's contents
    world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
    super.onBlockExploded(world, pos, explosion);
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
                                EntityPlayer player) {
    ItemStack stack = new ItemStack(state.getBlock());
    var tile = TileUtils.getTile(world, pos, TileWoodBarrel.class);
    if (tile != null && tile.isSealed()) {
      tile.saveToItemStack(stack);
    }
    return stack;
  }

  @Override
  public Class<TileWoodBarrel> getTileClass() {
    return TileWoodBarrel.class;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRWoodBarrel();
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodBarrel();
  }
}
