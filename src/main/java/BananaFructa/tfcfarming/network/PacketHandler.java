package BananaFructa.tfcfarming.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static su.terrafirmagreg.data.Constants.MODID_TFCFARMING;

public class PacketHandler {

  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_TFCFARMING);

  public static void registerPackets() {
    INSTANCE.registerMessage(SPacketNutrientDataResponseHandler.class, SPacketNutrientDataResponse.class, 0, Side.CLIENT);
    INSTANCE.registerMessage(CPacketRequestNutrientDataHandler.class, CPacketRequestNutrientData.class, 1, Side.SERVER);
  }

}
