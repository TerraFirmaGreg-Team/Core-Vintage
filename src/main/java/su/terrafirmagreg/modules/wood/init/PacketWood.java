package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.wood.network.CSPacketActionKey;
import su.terrafirmagreg.modules.wood.network.CSPacketOpenCartGui;
import su.terrafirmagreg.modules.wood.network.CSPacketToggleSlow;
import su.terrafirmagreg.modules.wood.network.SCPacketDrawnUpdate;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketWood {

  public static void onRegister(IPacketRegistry registry) {

    registry.register(Side.SERVER, CSPacketActionKey.class);

    registry.register(Side.SERVER, CSPacketOpenCartGui.class);

    registry.register(Side.SERVER, CSPacketToggleSlow.class);

    registry.register(Side.CLIENT, SCPacketDrawnUpdate.class);
  }
}
