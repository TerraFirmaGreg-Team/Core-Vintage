package net.dries007.tfc.module.core.init;

import net.dries007.tfc.module.core.network.*;
import net.minecraftforge.fml.relauncher.Side;
import su.terrafirmagreg.util.network.IPacketRegistry;

public class PacketCore {

    public static void register(IPacketRegistry registry) {

        // Received on server
        registry.register(new SCPacketGuiButton(), SCPacketGuiButton.class, Side.SERVER);
        registry.register(new SCPacketPlaceBlockSpecial(), SCPacketPlaceBlockSpecial.class, Side.SERVER);
        registry.register(new SCPacketSwitchPlayerInventoryTab(), SCPacketSwitchPlayerInventoryTab.class, Side.SERVER);
        registry.register(new SCPacketOpenCraftingGui(), SCPacketOpenCraftingGui.class, Side.SERVER);
        registry.register(new SCPacketCycleItemMode(), SCPacketCycleItemMode.class, Side.SERVER);
        registry.register(new SCPacketStackFood(), SCPacketStackFood.class, Side.SERVER);

        // Received on client
        registry.register(new SCPacketChunkData(), SCPacketChunkData.class, Side.CLIENT);
        registry.register(new SCPacketCapabilityContainerUpdate(), SCPacketCapabilityContainerUpdate.class, Side.CLIENT);
        registry.register(new SCPacketCalendarUpdate(), SCPacketCalendarUpdate.class, Side.CLIENT);
        registry.register(new SCPacketFoodStatsUpdate(), SCPacketFoodStatsUpdate.class, Side.CLIENT);
        registry.register(new SCPacketFoodStatsReplace(), SCPacketFoodStatsReplace.class, Side.CLIENT);
        registry.register(new SCPacketPlayerDataUpdate(), SCPacketPlayerDataUpdate.class, Side.CLIENT);
        registry.register(new SCPacketSpawnTFCParticle(), SCPacketSpawnTFCParticle.class, Side.CLIENT);
        registry.register(new SCPacketSimpleMessage(), SCPacketSimpleMessage.class, Side.CLIENT);
        registry.register(new SCPacketProspectResult(), SCPacketProspectResult.class, Side.CLIENT);
    }
}
