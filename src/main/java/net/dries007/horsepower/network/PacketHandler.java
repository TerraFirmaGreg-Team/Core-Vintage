package net.dries007.horsepower.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.horsepower.lib.Reference;
import net.dries007.horsepower.network.messages.SyncServerRecipesMessage;

public class PacketHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL);

  public static void init() {
    INSTANCE.registerMessage(SyncServerRecipesMessage.class, SyncServerRecipesMessage.class, 0, Side.CLIENT);
  }
}
