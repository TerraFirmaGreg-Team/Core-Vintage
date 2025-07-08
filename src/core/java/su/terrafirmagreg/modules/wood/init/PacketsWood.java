package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.modules.wood.packet.CSPacketActionKey;
import su.terrafirmagreg.modules.wood.packet.CSPacketOpenCartGui;
import su.terrafirmagreg.modules.wood.packet.CSPacketToggleSlow;
import su.terrafirmagreg.modules.wood.packet.SCPacketDrawnUpdate;

public class PacketsWood {

  public static void onRegister(IPacketRegistrar registrar) {

    registrar.addPacket(new CSPacketActionKey());
    registrar.addPacket(new CSPacketOpenCartGui());
    registrar.addPacket(new CSPacketToggleSlow());

    registrar.addPacket(new SCPacketDrawnUpdate());
  }
}
