package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.core.network.SCPacketGuiButton;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;
import su.terrafirmagreg.modules.core.network.SCPacketTemperature;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketCore {

    public static void onRegister(IPacketRegistry registry) {

        registry.register(
                SCPacketGuiButton.class,
                SCPacketGuiButton.class,
                Side.SERVER
        );

        registry.register(
                SCPacketSimpleMessage.class,
                SCPacketSimpleMessage.class,
                Side.CLIENT
        );

        registry.register(
                SCPacketTemperature.class,
                SCPacketTemperature.class,
                Side.CLIENT
        );
    }
}
