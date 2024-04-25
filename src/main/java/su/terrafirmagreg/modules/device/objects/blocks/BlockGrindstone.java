package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRGrindstone;
import su.terrafirmagreg.modules.device.objects.tiles.TEGrindstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockGrindstone extends BlockBase implements ITEBlock {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockGrindstone() {
        super(Material.WOOD);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.5f);
        this.setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH));
    }

    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return new TEGrindstone();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @NotNull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4));
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != Blocks.BARRIER) {
            BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
            return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock()
                    .isLeaves(iblockstate, worldIn, pos.down());
        } else {
            return false;
        }
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX,
                                    float hitY, float hitZ) {
        if (hand.equals(EnumHand.MAIN_HAND)) {
            var te = TileUtils.getTile(world, pos, TEGrindstone.class);
            if (te != null) {
                ItemStack heldStack = playerIn.getHeldItem(hand);
                IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing) null);
                if (inventory != null) {

                    int slot = inventory.getStackInSlot(TEGrindstone.SLOT_GRINDSTONE)
                            .isEmpty() && inventory.getStackInSlot(TEGrindstone.SLOT_INPUT)
                            .isEmpty() ? TEGrindstone.SLOT_GRINDSTONE : TEGrindstone.SLOT_INPUT;

                    if (slot == TEGrindstone.SLOT_INPUT) {
                        if (inventory.isItemValid(slot, heldStack)) {
                            playerIn.setHeldItem(EnumHand.MAIN_HAND, te.insertOrSwapItem(slot, heldStack));
                            te.setAndUpdateSlots(slot);
                            return true;
                        } else {
                            if (!inventory.getStackInSlot(slot).isEmpty()) {
                                ItemHandlerHelper.giveItemToPlayer(playerIn, inventory.extractItem(slot, inventory.getStackInSlot(slot)
                                        .getCount(), false));
                                return true;
                            }
                        }
                    }

                    if (slot == TEGrindstone.SLOT_GRINDSTONE && inventory.getStackInSlot(slot)
                            .isEmpty() && inventory.isItemValid(slot, heldStack)) {
                        playerIn.setHeldItem(EnumHand.MAIN_HAND, te.insertOrSwapItem(slot, heldStack));
                        te.setAndUpdateSlots(slot);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void breakBlock(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state) {
        var te = TileUtils.getTile(world, pos, TEGrindstone.class);
        if (te != null) {
            te.onBreakBlock(world, pos, state);
        }
        super.breakBlock(world, pos, state);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    public @NotNull BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.HEAVY;
    }

    @Override
    public @NotNull String getName() {
        return "device/grindstone_base";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEGrindstone.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRGrindstone();
    }
}
