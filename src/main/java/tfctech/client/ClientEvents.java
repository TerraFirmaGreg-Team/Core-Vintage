package tfctech.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
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

import tfctech.objects.blocks.devices.BlockFridge;

import static su.terrafirmagreg.api.data.Reference.TFCTECH;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFCTECH)
public final class ClientEvents
{
    @SubscribeEvent
    public static void drawBlockHighlight(DrawBlockHighlightEvent event)
    {
        if (event.getTarget() == null || event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK) return;
        BlockPos pos = event.getTarget().getBlockPos();
        World world = event.getPlayer().world;
        EntityPlayer player = event.getPlayer();
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof BlockFridge)
        {
            BlockFridge fridge = (BlockFridge) state.getBlock();
            if (state.getValue(BlockFridge.UPPER))
            {
                pos = pos.down();
            }
            int slot = BlockFridge.getPlayerLookingItem(pos, player, state.getValue(BlockFridge.FACING));
            if (slot > -1)
            {
                Vec3d itemPos = BlockFridge.getItems(state.getValue(BlockFridge.FACING))[slot];
                double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
                double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
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
