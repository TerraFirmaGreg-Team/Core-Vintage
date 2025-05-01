package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.network.SCPacketLatexExtractor;

public final class PacketsDevice {

  public static void onRegister(INetworkRegistrar registrar) {
    registrar.addPacket(CSPacketFreezeDryer.class);
//    registrar.addPacket(Side.CLIENT, SCPacketFridge.class);
    registrar.addPacket(SCPacketLatexExtractor.class);
//    registrar.addPacket(Side.CLIENT, SCPacketTileEntity.class);
  }
}
