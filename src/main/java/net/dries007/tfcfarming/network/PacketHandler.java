package net.dries007.tfcfarming.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfcfarming.TFCFarming;

public class PacketHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(TFCFarming.modId);

  public static void registerPackets() {
    INSTANCE.registerMessage(SPacketNutrientDataResponse.Handler.class, SPacketNutrientDataResponse.class, 0, Side.CLIENT);
    INSTANCE.registerMessage(CPacketRequestNutrientData.Handler.class, CPacketRequestNutrientData.class, 1, Side.SERVER);
  }

}
