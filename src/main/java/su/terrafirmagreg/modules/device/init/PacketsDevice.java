package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.network.SCPacketFridge;
import su.terrafirmagreg.modules.device.network.SCPacketLatex;
import su.terrafirmagreg.modules.device.network.SCPacketTileEntity;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsDevice {

  public static void onRegister(IPacketRegistry registry) {
    registry.register(Side.SERVER, CSPacketFreezeDryer.class);
    registry.register(Side.CLIENT, SCPacketFridge.class);
    registry.register(Side.CLIENT, SCPacketLatex.class);
    registry.register(Side.CLIENT, SCPacketTileEntity.class);
  }
}
