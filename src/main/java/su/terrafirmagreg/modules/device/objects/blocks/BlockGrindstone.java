package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.tile.ITileBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRGrindstone;
import su.terrafirmagreg.modules.device.objects.tiles.TileGrindstone;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Blockstates.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockGrindstone extends BaseBlock implements ITileBlock {

    public BlockGrindstone() {
        super(Settings.of(Material.WOOD));

        getSettings()
                .registryKey("device/grindstone_base")
                .soundType(SoundType.WOOD)
                .hardness(1.5f)
                .nonOpaque()
                .nonFullCube()
                .size(Size.LARGE)
                .weight(Weight.HEAVY);
        setHarvestLevel("pickaxe", 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(HORIZONTAL, EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HORIZONTAL);
    }

    @NotNull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getHorizontalIndex();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
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

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand.equals(EnumHand.MAIN_HAND)) {
            var te = TileUtils.getTile(world, pos, TileGrindstone.class);
            if (te != null) {
                ItemStack heldStack = playerIn.getHeldItem(hand);
                IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null) {

                    int slot = inventory.getStackInSlot(TileGrindstone.SLOT_GRINDSTONE)
                            .isEmpty() && inventory.getStackInSlot(TileGrindstone.SLOT_INPUT)
                            .isEmpty() ? TileGrindstone.SLOT_GRINDSTONE : TileGrindstone.SLOT_INPUT;

                    if (slot == TileGrindstone.SLOT_INPUT) {
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

                    if (slot == TileGrindstone.SLOT_GRINDSTONE && inventory.getStackInSlot(slot)
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

    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(world, pos, TileGrindstone.class);
        if (tile != null) {
            tile.onBreakBlock(world, pos, state);
        }
        super.breakBlock(world, pos, state);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileGrindstone();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileGrindstone.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRGrindstone();
    }
}
