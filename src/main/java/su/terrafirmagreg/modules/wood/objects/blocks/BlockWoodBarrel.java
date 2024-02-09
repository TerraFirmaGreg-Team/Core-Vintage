package su.terrafirmagreg.modules.wood.objects.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.util.Helpers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.tiles.TEWoodBarrel;

/**
 * Barrel block. Can be filled with fluids (10 B), and one item stack. Performs barrel recipes.
 * Sealed state is stored in block state and cached in TE, synced when updated via custom packet
 *
 * @see TEWoodBarrel
 * @see BarrelRecipe
 */
public class BlockWoodBarrel extends BlockWood implements IItemSize {

    public static final PropertyBool SEALED = PropertyBool.create("sealed");
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    public BlockWoodBarrel(WoodBlockVariant variant, WoodType type) {
        super(variant, type);

        setHardness(2F);
        setDefaultState(blockState.getBaseState().withProperty(SEALED, false));
    }

    /**
     * Used to toggle the barrel seal state and update the tile entity, in the correct order
     */
    public static void toggleBarrelSeal(World world, BlockPos pos) {
        var tile = Helpers.getTE(world, pos, TEWoodBarrel.class);
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


    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE;
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_HEAVY;
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return stack.getTagCompound() == null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTopSolid(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(SEALED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SEALED) ? 1 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @NotNull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull IBlockState state, World world, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos fromPos) {
        if (!world.isRemote) {
            if (world.getBlockState(fromPos).getBlock() instanceof BlockRedstoneComparator)
                return;
            boolean powered = world.isBlockPowered(pos);
            if (powered || block.getDefaultState().canProvidePower()) {
                if (powered != state.getValue(SEALED)) {
                    toggleBarrelSeal(world, pos);
                }
            }
        }
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TEWoodBarrel tile = Helpers.getTE(worldIn, pos, TEWoodBarrel.class);
        if (tile != null) {
            tile.onBreakBlock(worldIn, pos, state);
        }
        worldIn.updateComparatorOutputLevel(pos, this);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TEWoodBarrel te = Helpers.getTE(worldIn, pos, TEWoodBarrel.class);
        if (te != null) {
            if (heldItem.isEmpty() && playerIn.isSneaking()) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
                toggleBarrelSeal(worldIn, pos);
                return true;
            } else if (heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !state.getValue(SEALED)) {
                IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                if (fluidHandler != null) {
                    if (!worldIn.isRemote) {
                        FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler);
                        te.markDirty();
                    }
                    return true;
                }
            } else {
//                if (!worldIn.isRemote) {
//                    CoreGuiHandler.openGui(worldIn, pos, playerIn, CoreGuiHandler.Type.WOOD_BARREL);
//                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, @NotNull ItemStack stack) {
        if (!worldIn.isRemote && stack.getTagCompound() != null) {
            TEWoodBarrel te = Helpers.getTE(worldIn, pos, TEWoodBarrel.class);
            if (te != null) {
                worldIn.setBlockState(pos, state.withProperty(SEALED, true));
                te.loadFromItemStack(stack);
            }
        }
    }

    @Override
    @NotNull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SEALED);
    }

    @Override
    public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(@NotNull IBlockState baseState, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return new TEWoodBarrel();
    }

    /**
     * Handle drops via {@link this#breakBlock(World, BlockPos, IBlockState)}
     */
    @Override
    public void getDrops(@NotNull NonNullList<ItemStack> drops, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state, int fortune) {
    }

    @Override
    public void onBlockExploded(World world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        // Unseal the vessel if an explosion destroys it, so it drops it's contents
        world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
        super.onBlockExploded(world, pos, explosion);
    }

    @Override
    @NotNull
    public ItemStack getPickBlock(IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        ItemStack stack = new ItemStack(state.getBlock());
        TEWoodBarrel tile = Helpers.getTE(world, pos, TEWoodBarrel.class);
        if (tile != null && tile.isSealed()) {
            tile.saveToItemStack(stack);
        }
        return stack;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getComparatorInputOverride(IBlockState state, @NotNull World world, @NotNull BlockPos pos) {
        return state.getValue(SEALED) ? 15 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        final ModelResourceLocation sealed = new ModelResourceLocation(getResourceLocation(), "sealed=true");
        final ModelResourceLocation unsealed = new ModelResourceLocation(getResourceLocation(), "sealed=false");

        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .build());

        ModelLoader.setCustomMeshDefinition(
                Item.getItemFromBlock(this),
                stack -> stack.getTagCompound() != null ? sealed : unsealed);

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));

    }
}
