package de.mennomax.astikorcarts.handler;

import de.mennomax.astikorcarts.packets.CPacketActionKey;
import de.mennomax.astikorcarts.packets.CPacketOpenCartGui;
import de.mennomax.astikorcarts.packets.CPacketToggleSlow;
import de.mennomax.astikorcarts.packets.SPacketDrawnUpdate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static su.terrafirmagreg.Constants.MODID_ASTIKORCARTS;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_ASTIKORCARTS);
	private static int id = 0;

	public static void registerPackets() {
		INSTANCE.registerMessage(CPacketActionKey.ActionKeyPacketHandler.class, CPacketActionKey.class, id++, Side.SERVER);
		INSTANCE.registerMessage(CPacketOpenCartGui.OpenCartGuiPacketHandler.class, CPacketOpenCartGui.class, id++, Side.SERVER);
		INSTANCE.registerMessage(CPacketToggleSlow.ToggleSlowHandler.class, CPacketToggleSlow.class, id++, Side.SERVER);
		INSTANCE.registerMessage(SPacketDrawnUpdate.DrawnUpdatePacketHandler.class, SPacketDrawnUpdate.class, id++, Side.CLIENT);
	}
}
