package net.dries007.tfc.client.gui.overlay;

import su.terrafirmagreg.modules.device.objects.blocks.BlockFridge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.objects.items.metal.ItemMetalChisel;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

/**
 * Interfacing to pass on DrawHighlightEvent's custom implementations
 */
public interface IHighlightHandler {

    /**
     * Returns an AxisAlignedBB obj containing a full box to the location where player is looking at
     *
     * @param player       the player
     * @param pos          the blockpos to offset this AxisAlignedBB to
     * @param partialTicks current frame's partial ticks (since FPS is higher than TPS)
     * @return an AxisAlignedBB containing a full block's box
     */
    static AxisAlignedBB getBox(EntityPlayer player, BlockPos pos, double partialTicks) {
        double dx = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double dy = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double dz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        return new AxisAlignedBB(pos).offset(-dx, -dy, -dz);
    }

    /**
     * Draws the outlining of an AxisAlignedBB obj
     *
     * @param box       AxisAlignedBB to draw
     * @param lineWidth where 1 = MC's default selection box
     * @param red       [0-1] red value
     * @param green     [0-1] green value
     * @param blue      [0-1] blue value
     * @param alpha     [0-1] alpha value
     */
    static void drawBox(AxisAlignedBB box, float lineWidth, float red, float green, float blue, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(lineWidth);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        RenderGlobal.drawSelectionBoundingBox(box, red, green, blue, alpha);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Handles drawing custom bounding boxes depending on where player is looking at
     *
     * @param world        the client's world obj
     * @param pos          the blockpos player is looking at
     * @param player       the player that is looking at this block
     * @param rayTrace     the RayTraceResult, got from DrawBlockHighlightEvent
     * @param partialTicks current frame's partial ticks (since FPS is higher than TPS)
     * @return true if you wish to cancel drawing the block's bounding box outline
     */
    boolean drawHighlight(World world, BlockPos pos, EntityPlayer player, RayTraceResult rayTrace, double partialTicks);

    @SideOnly(Side.CLIENT)
    @Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID_TFC)
    final class EventHandler {

        /**
         * Handles custom bounding boxes drawing eg: Chisel, Quern handle
         */
        @SuppressWarnings("ConstantValue")
        @SubscribeEvent
        public static void drawHighlightEvent(DrawBlockHighlightEvent event) {
            if (event.getTarget().getBlockPos() == null) {
                return;
            }

            final EntityPlayer player = event.getPlayer();
            final World world = player.getEntityWorld();
            final RayTraceResult traceResult = event.getTarget();
            BlockPos pos = traceResult.getBlockPos();
            final IBlockState state = world.getBlockState(pos);
            final Block block = state.getBlock();

            // Handle Chisel first
            if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemMetalChisel) {
                // Get the state that the chisel would turn the block into if it clicked
                IBlockState newState = ItemMetalChisel.getChiselResultState(player, player.world, pos, traceResult.sideHit,
                        (float) traceResult.hitVec.x - pos.getX(),
                        (float) traceResult.hitVec.y - pos.getY(),
                        (float) traceResult.hitVec.z - pos.getZ());
                if (newState != null) {
                    AxisAlignedBB box = IHighlightHandler.getBox(player, pos, event.getPartialTicks()).grow(0.001);
                    double offsetX = 0, offsetY = 0, offsetZ = 0;

                    if (newState.getBlock() instanceof BlockStairs) {
                        EnumFacing facing = newState.getValue(BlockStairs.FACING);

                        offsetY = (newState.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) ? -0.5 : 0.5;
                        offsetX = -facing.getXOffset() * 0.5;
                        offsetZ = -facing.getZOffset() * 0.5;
                    } else if (newState.getBlock() instanceof BlockSlab) {
                        offsetY = (newState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) ? -0.5 : 0.5;
                    }

                    box = box.intersect(box.offset(offsetX, offsetY, offsetZ));

                    IHighlightHandler.drawBox(box, 5f, 1, 0, 0, 0.8f);
                }
            } else if (block instanceof IHighlightHandler highlightHandler) {
                // Pass on to custom implementations
                if (highlightHandler.drawHighlight(world, pos, player, traceResult, event.getPartialTicks())) {
                    // Cancel drawing this block's bounding box
                    event.setCanceled(true);
                }
            } else if (block instanceof BlockFridge fridge) {
                if (fridge.getDefaultState().getValue(BlockFridge.UPPER)) {
                    pos = pos.down();
                }
                int slot = BlockFridge.getPlayerLookingItem(pos, player, state.getValue(BlockFridge.FACING));
                if (slot > -1) {
                    Vec3d itemPos = BlockFridge.getItems(state.getValue(BlockFridge.FACING))[slot];
                    double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
                    double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
                    double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(
                            GlStateManager.SourceFactor.SRC_ALPHA,
                            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                            GlStateManager.SourceFactor.ONE,
                            GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(2.0F);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    AxisAlignedBB offsetAABB = new AxisAlignedBB(itemPos.x, itemPos.y, itemPos.z, itemPos.x, itemPos.y, itemPos.z).grow(0.1D).offset(pos);
                    RenderGlobal.drawSelectionBoundingBox(offsetAABB.grow(0.002D).offset(-d3, -d4, -d5), 0, 0, 0, 0.4F);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                }
            }
        }
    }
}
