package su.terrafirmagreg.modules.core.init;


import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.modules.core.packet.CSPacketGuiButton;
import su.terrafirmagreg.modules.core.packet.SCPacketAmbiental;
import su.terrafirmagreg.modules.core.packet.SCPacketPlayerDataUpdate;
import su.terrafirmagreg.modules.core.packet.SCPacketSimple;

public final class PacketsCore {

  public static void onRegister(IPacketRegistrar registry) {
    registry.addPacket(new CSPacketGuiButton());

    registry.addPacket(new SCPacketSimple());
    registry.addPacket(new SCPacketAmbiental());
    registry.addPacket(new SCPacketPlayerDataUpdate());
  }
}
