package net.dries007.astikorcarts.handler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.packets.CPacketActionKey;
import net.dries007.astikorcarts.packets.CPacketOpenCartGui;
import net.dries007.astikorcarts.packets.CPacketToggleSlow;
import net.dries007.astikorcarts.packets.SPacketDrawnUpdate;

public class PacketHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(AstikorCarts.MODID);
  private static int id = 0;

  public static void registerPackets() {
    INSTANCE.registerMessage(CPacketActionKey.ActionKeyPacketHandler.class, CPacketActionKey.class, id++, Side.SERVER);
    INSTANCE.registerMessage(CPacketOpenCartGui.OpenCartGuiPacketHandler.class, CPacketOpenCartGui.class, id++, Side.SERVER);
    INSTANCE.registerMessage(CPacketToggleSlow.ToggleSlowHandler.class, CPacketToggleSlow.class, id++, Side.SERVER);
    INSTANCE.registerMessage(SPacketDrawnUpdate.DrawnUpdatePacketHandler.class, SPacketDrawnUpdate.class, id++, Side.CLIENT);
  }
}
