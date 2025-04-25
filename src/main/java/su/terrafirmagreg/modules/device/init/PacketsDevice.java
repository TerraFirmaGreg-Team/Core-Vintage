package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.network.SCPacketLatexExtractor;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsDevice {

  public static void onRegister(INetworkRegistrar registrar) {
    registrar.addPacket(Side.SERVER, CSPacketFreezeDryer.class);
//    registrar.addPacket(Side.CLIENT, SCPacketFridge.class);
    registrar.addPacket(Side.CLIENT, SCPacketLatexExtractor.class);
//    registrar.addPacket(Side.CLIENT, SCPacketTileEntity.class);
  }
}
