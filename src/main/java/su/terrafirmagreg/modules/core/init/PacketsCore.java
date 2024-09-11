package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.core.network.CSPacketGuiButton;
import su.terrafirmagreg.modules.core.network.SCPacketChunkData;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;
import su.terrafirmagreg.modules.core.network.SCPacketTemperature;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsCore {

  public static void onRegister(IPacketRegistry registry) {

    registry.register(
            CSPacketGuiButton.class,
            CSPacketGuiButton.class,
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

    registry.register(
            SCPacketChunkData.class,
            SCPacketChunkData.class,
            Side.CLIENT
    );
  }
}
