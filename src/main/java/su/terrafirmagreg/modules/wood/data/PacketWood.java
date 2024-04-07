package su.terrafirmagreg.modules.wood.data;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.wood.network.CSPacketActionKey;
import su.terrafirmagreg.modules.wood.network.CSPacketOpenCartGui;
import su.terrafirmagreg.modules.wood.network.CSPacketToggleSlow;
import su.terrafirmagreg.modules.wood.network.SCPacketDrawnUpdate;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketWood {

    public static void onRegister(IPacketRegistry registry) {

        registry.register(
                CSPacketActionKey.class,
                CSPacketActionKey.class,
                Side.SERVER
        );

        registry.register(
                CSPacketOpenCartGui.class,
                CSPacketOpenCartGui.class,
                Side.SERVER
        );

        registry.register(
                CSPacketToggleSlow.class,
                CSPacketToggleSlow.class,
                Side.SERVER
        );

        registry.register(
                SCPacketDrawnUpdate.class,
                SCPacketDrawnUpdate.class,
                Side.CLIENT
        );
    }
}
