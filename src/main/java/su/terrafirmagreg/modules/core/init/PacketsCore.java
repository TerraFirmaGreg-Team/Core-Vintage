package su.terrafirmagreg.modules.core.init;


import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.modules.core.network.CSPacketGuiButton;
import su.terrafirmagreg.modules.core.network.SCPacketAmbiental;
import su.terrafirmagreg.modules.core.network.SCPacketPlayerDataUpdate;
import su.terrafirmagreg.modules.core.network.SCPacketSimple;

public final class PacketsCore {

  public static void onRegister(INetworkRegistrar registry) {
    registry.addPacket(CSPacketGuiButton.class);

    registry.addPacket(SCPacketSimple.class);
    registry.addPacket(SCPacketAmbiental.class);
    registry.addPacket(SCPacketPlayerDataUpdate.class);
  }
}
