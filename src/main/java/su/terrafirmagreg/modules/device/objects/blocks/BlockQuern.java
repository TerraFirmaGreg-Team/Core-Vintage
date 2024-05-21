package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRQuern;
import su.terrafirmagreg.modules.device.objects.tiles.TileQuern;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.client.gui.overlay.IHighlightHandler;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.modules.device.objects.tiles.TileQuern.SLOT_HANDSTONE;

@SuppressWarnings("deprecation")
public class BlockQuern extends BaseBlock implements IHighlightHandler, ITileProvider {

    private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 0.625D, 1D);
    private static final AxisAlignedBB QUERN_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 0.875D, 1D);

    private static final AxisAlignedBB HANDSTONE_AABB = new AxisAlignedBB(0.1875D, 0.625D, 0.1875D, 0.8125D, 0.86D, 0.8125D);
    private static final AxisAlignedBB HANDLE_AABB = new AxisAlignedBB(0.27125D, 0.86D, 0.27125D, 0.335D, 1.015D, 0.335D);

    private static final AxisAlignedBB INPUT_SLOT_AABB = new AxisAlignedBB(0.375D, 0.86D, 0.375D, 0.625D, 1.015D, 0.625D);

    public BlockQuern() {
        super(Settings.of(Material.ROCK));

        getSettings()
                .registryKey("device/quern")
                .soundType(SoundType.STONE)
                .hardness(3.0f)
                .nonOpaque()
                .nonFullCube()
                .size(Size.VERY_LARGE)
                .weight(Weight.VERY_HEAVY);
    }

    /**
     * Gets the selection place player is looking at Used for interaction / selection box drawing
     */
    private static SelectionPlace getPlayerSelection(World world, BlockPos pos, EntityPlayer player) {
        // This will compute a line from the camera center (crosshair) starting at the player eye pos and a little after this block
        // so we can grab the exact point regardless from which face player is looking from
        double length = Math.sqrt(pos.distanceSqToCenter(player.posX, player.posY, player.posZ)) + 1.5D;
        Vec3d eyePos = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d lookingPos = eyePos.add(new Vec3d(player.getLookVec().x * length, player.getLookVec().y * length, player.getLookVec().z * length));

        var tile = TileUtils.getTile(world, pos, TileQuern.class);

        if (tile != null) {
            IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            // Draws the correct selection box depending on where the player is looking at
            if (!tile.isGrinding() && tile.hasHandstone() && HANDLE_AABB.offset(pos)
                    .calculateIntercept(eyePos, lookingPos) != null) {
                return SelectionPlace.HANDLE;
            } else if (!tile.isGrinding() && tile.hasHandstone() && (!player.getHeldItem(EnumHand.MAIN_HAND)
                    .isEmpty() || (inventory != null && !inventory.getStackInSlot(TileQuern.SLOT_INPUT)
                    .isEmpty())) && INPUT_SLOT_AABB.offset(pos)
                    .calculateIntercept(eyePos, lookingPos) != null) {
                return SelectionPlace.INPUT_SLOT;
            } else if ((tile.hasHandstone() || tile.isItemValid(TileQuern.SLOT_HANDSTONE, player.getHeldItem(EnumHand.MAIN_HAND))) &&
                    HANDSTONE_AABB.offset(pos)
                            .calculateIntercept(eyePos, lookingPos) != null) {
                return SelectionPlace.HANDSTONE;
            }
        }
        return SelectionPlace.BASE;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        var tile = TileUtils.getTile(source, pos, TileQuern.class);
        if (tile != null && tile.hasHandstone()) {
            return QUERN_AABB;
        } else {
            return BASE_AABB;
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.DOWN) {
            return BlockFaceShape.SOLID;
        }
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn,
                                      boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);
        var tile = TileUtils.getTile(world, pos, TileQuern.class);
        if (tile != null && tile.hasHandstone()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, HANDSTONE_AABB);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(world, pos, TileQuern.class);
        if (tile != null) {
            tile.onBreakBlock(world, pos, state);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand.equals(EnumHand.MAIN_HAND)) {
            var tile = TileUtils.getTile(world, pos, TileQuern.class);
            if (tile != null && !tile.isGrinding()) {
                ItemStack heldStack = playerIn.getHeldItem(hand);
                SelectionPlace selection = getPlayerSelection(world, pos, playerIn);
                IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null) {
                    if (selection == SelectionPlace.HANDLE) {
                        tile.grind();
                        world.playSound(null, pos, TFCSounds.QUERN_USE, SoundCategory.BLOCKS, 1,
                                1 + ((world.rand.nextFloat() - world.rand.nextFloat()) / 16));
                        return true;
                    } else if (selection == SelectionPlace.INPUT_SLOT) {
                        playerIn.setHeldItem(EnumHand.MAIN_HAND, tile.insertOrSwapItem(TileQuern.SLOT_INPUT, heldStack));
                        tile.setAndUpdateSlots(TileQuern.SLOT_INPUT);
                        return true;
                    } else if (selection == SelectionPlace.HANDSTONE && inventory.getStackInSlot(SLOT_HANDSTONE)
                            .isEmpty() && inventory.isItemValid(SLOT_HANDSTONE, heldStack)) {
                        playerIn.setHeldItem(EnumHand.MAIN_HAND, tile.insertOrSwapItem(SLOT_HANDSTONE, heldStack));
                        tile.setAndUpdateSlots(SLOT_HANDSTONE);
                        return true;
                    } else if (selection == SelectionPlace.BASE && !inventory.getStackInSlot(TileQuern.SLOT_OUTPUT)
                            .isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(playerIn,
                                inventory.extractItem(TileQuern.SLOT_OUTPUT, inventory.getStackInSlot(TileQuern.SLOT_OUTPUT)
                                        .getCount(), false));
                        tile.setAndUpdateSlots(TileQuern.SLOT_OUTPUT);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    public boolean drawHighlight(World world, BlockPos pos, EntityPlayer player, RayTraceResult rayTrace, double partialTicks) {
        double dx = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double dy = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double dz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        SelectionPlace selection = getPlayerSelection(world, pos, player);

        // Draws the correct selection box depending on where the player is looking at
        if (selection == SelectionPlace.HANDLE) {
            // Draws handle AABB if player is looking at it
            IHighlightHandler.drawBox(HANDLE_AABB.offset(pos).offset(-dx, -dy, -dz), 1f, 0, 0, 0, 0.4f);
        } else if (selection == SelectionPlace.INPUT_SLOT) {
            // Draws item input AABB if user has item in main hand or there is an item in slot
            IHighlightHandler.drawBox(INPUT_SLOT_AABB.offset(pos).offset(-dx, -dy, -dz), 1f, 0, 0, 0, 0.4f);
        } else if (selection == SelectionPlace.HANDSTONE) {
            // Draws handstone AABB if player is looking at it
            IHighlightHandler.drawBox(HANDSTONE_AABB.offset(pos).offset(-dx, -dy, -dz).grow(0.002D), 1f, 0, 0, 0, 0.4f);
        } else {
            // Just draw the base outline (last grow is just what MC does to actually make the outline visible
            IHighlightHandler.drawBox(BASE_AABB.offset(pos).offset(-dx, -dy, -dz).grow(0.002D), 1f, 0, 0, 0, 0.4f);
        }
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileQuern();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileQuern.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRQuern();
    }

    /**
     * Just a helper enum to figure out where player is looking at Used to draw selection boxes + handle interaction
     */
    private enum SelectionPlace {
        HANDLE,
        HANDSTONE,
        INPUT_SLOT,
        BASE
    }
}
