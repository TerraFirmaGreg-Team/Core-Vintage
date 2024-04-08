package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketDevice {

    public static void onRegister(IPacketRegistry registry) {
        registry.register(
                CSPacketFreezeDryer.class,
                CSPacketFreezeDryer.class,
                Side.SERVER
        );
    }
}
