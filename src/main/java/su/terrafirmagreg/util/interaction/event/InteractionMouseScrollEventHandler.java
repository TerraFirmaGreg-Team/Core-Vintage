package su.terrafirmagreg.util.interaction.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.interaction.network.CSPacketInteractionMouseWheel;
import su.terrafirmagreg.util.interaction.util.InteractionRayTraceData;
import su.terrafirmagreg.util.network.IPacketService;

/**
 * Responsible for sending interaction scroll wheel data to the server.
 * <p>
 * Must be registered with the event bus.
 */
public class InteractionMouseScrollEventHandler {

    private final IPacketService packetService;

    public InteractionMouseScrollEventHandler(IPacketService packetService) {

        this.packetService = packetService;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void on(MouseEvent event) {

        int wheelDelta = event.getDwheel();

        if (wheelDelta == 0) {
            return;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.player;

        if (!player.isSneaking()) {
            return;
        }

        RayTraceResult rayTraceResult = minecraft.objectMouseOver;

        if (rayTraceResult == null) {
            return;
        }

        if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }

        if (rayTraceResult.hitInfo instanceof InteractionRayTraceData.List) {

            CSPacketInteractionMouseWheel packet = new CSPacketInteractionMouseWheel(
                    rayTraceResult.getBlockPos(),
                    wheelDelta,
                    rayTraceResult.sideHit,
                    rayTraceResult.hitVec
            );
            this.packetService.sendToServer(packet);
            event.setCanceled(true);
        }
    }
}
