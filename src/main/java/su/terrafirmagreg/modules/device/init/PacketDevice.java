package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.network.SCPacketFridge;
import su.terrafirmagreg.modules.device.network.SCPacketLatex;
import su.terrafirmagreg.modules.device.network.SCPacketTileEntity;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketDevice {

    public static void onRegister(IPacketRegistry registry) {
        registry.register(
                CSPacketFreezeDryer.class,
                CSPacketFreezeDryer.class,
                Side.SERVER
        );
        registry.register(
                SCPacketFridge.class,
                SCPacketFridge.class,
                Side.CLIENT
        );
        registry.register(
                SCPacketLatex.class,
                SCPacketLatex.class,
                Side.CLIENT
        );
        registry.register(
                SCPacketTileEntity.class,
                SCPacketTileEntity.class,
                Side.CLIENT
        );
    }
}
