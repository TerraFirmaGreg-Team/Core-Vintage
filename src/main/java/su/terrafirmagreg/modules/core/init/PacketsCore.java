package su.terrafirmagreg.modules.core.init;


import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.modules.core.network.SCPacketAmbiental;
import su.terrafirmagreg.modules.core.network.SCPacketPlayerDataUpdate;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsCore {

  public static void onRegister(INetworkManager registry) {

    registry.register(Side.CLIENT, SCPacketAmbiental.class);
    registry.register(Side.CLIENT, SCPacketPlayerDataUpdate.class);
  }
}
