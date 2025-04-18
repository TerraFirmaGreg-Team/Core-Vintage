package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.network.SCPacketLatexExtractor;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsDevice {

  public static void onRegister(INetworkManager registry) {
    registry.register(Side.SERVER, CSPacketFreezeDryer.class);
//    registry.register(Side.CLIENT, SCPacketFridge.class);
    registry.register(Side.CLIENT, SCPacketLatexExtractor.class);
//    registry.register(Side.CLIENT, SCPacketTileEntity.class);
  }
}
