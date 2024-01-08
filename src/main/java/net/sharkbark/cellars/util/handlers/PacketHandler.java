package net.sharkbark.cellars.util.handlers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static su.terrafirmagreg.Constants.MODID_CELLARS;

public class PacketHandler {
	public static final SimpleNetworkWrapper packetReq = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_CELLARS);

	public static void init() {
		packetReq.registerMessage(FDPacket.Handler.class, FDPacket.class, 0, Side.SERVER);
	}

	;
}
