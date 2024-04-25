package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TECrate;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.util.PropertyUtils.SEALED;

/**
 * Crate is an inventory that preserves the contents when sealed It can be picked up and keeps it's inventory Sealed state is stored in a block state
 * property, and cached in the TE (for gui purposes)
 */
public class BlockCrate extends BlockBase implements ITEBlock {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.05D, 0.0D, 0.05D, 0.95D, 0.875D, 0.95D);
    private static final AxisAlignedBB BOUNDING_BOX_SEALED = new AxisAlignedBB(0.05D, 0.0D, 0.05D, 0.95D, 0.875D, 0.95D);

    @SuppressWarnings("WeakerAccess")
    public BlockCrate() {
        super(Material.CIRCUITS);
        setSoundType(SoundType.WOOD);
        setHardness(2F);
        setDefaultState(blockState.getBaseState()
                .withProperty(SEALED, false));
    }

    /**
     * Used to update the vessel seal state and the TE, in the correct order
     */
    public static void toggleCrateSeal(World world, BlockPos pos) {
        TECrate tile = TileUtils.getTile(world, pos, TECrate.class);
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
    public @NotNull String getName() {
        return "device/crate";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TECrate.class;
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE; // Causes overburden if sealed
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_HEAVY; // Stacksize = 1
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
        return this.getDefaultState().withProperty(SEALED, meta == 1);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return state.getValue(SEALED) ? BOUNDING_BOX_SEALED : BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos,
                                            @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Block blockIn,
                                @NotNull BlockPos fromPos) {
        if (!canStay(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TECrate tile = TileUtils.getTile(worldIn, pos, TECrate.class);
        if (tile != null) {
            tile.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(@NotNull World world, @NotNull BlockPos pos) {
        return canStay(world, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            TECrate te = TileUtils.getTile(worldIn, pos, TECrate.class);
            if (te != null) {
                if (heldItem.isEmpty() && playerIn.isSneaking()) {
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
                    toggleCrateSeal(worldIn, pos);
                } else {
                    GuiHandler.openGui(worldIn, pos, playerIn, GuiHandler.Type.CRATE);
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer,
                                @NotNull ItemStack stack) {
        // If the barrel was sealed, then copy the contents from the item
        if (!worldIn.isRemote) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                TECrate te = TileUtils.getTile(worldIn, pos, TECrate.class);
                if (te != null) {
                    worldIn.setBlockState(pos, state.withProperty(SEALED, true));
                    te.readFromItemTag(nbt);
                }
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
    public boolean isSideSolid(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return new TECrate();
    }

    @Override
    public void getDrops(@NotNull NonNullList<ItemStack> drops, @NotNull IBlockAccess world, @NotNull BlockPos pos, IBlockState state, int fortune) {
        // Only drop the barrel if it's not sealed, since the barrel with contents will be already dropped by the TE
        if (!state.getValue(SEALED)) {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public void onBlockExploded(World world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        // Unseal the barrel if an explosion destroys it, so it drops it's contents
        world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
        super.onBlockExploded(world, pos, explosion);
    }

    private boolean canStay(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos.down())
                .getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }
}
