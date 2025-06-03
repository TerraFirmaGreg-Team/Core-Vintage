package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.modules.device.packet.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.packet.SCPacketLatexExtractor;

public final class PacketsDevice {

  public static void onRegister(IPacketRegistrar registrar) {
    registrar.addPacket(new CSPacketFreezeDryer());
//    registrar.addPacket(Side.CLIENT, SCPacketFridge.class);
    registrar.addPacket(new SCPacketLatexExtractor());
//    registrar.addPacket(Side.CLIENT, SCPacketTileEntity.class);
  }
}
