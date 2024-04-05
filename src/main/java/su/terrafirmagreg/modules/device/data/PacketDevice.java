package su.terrafirmagreg.modules.device.data;

import net.minecraftforge.fml.relauncher.Side;
import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;

public final class PacketDevice {

	public static void onRegister(IPacketRegistry registry) {
		registry.register(
				CSPacketFreezeDryer.class,
				CSPacketFreezeDryer.class,
				Side.SERVER
		);
	}
}
