package net.anodecathode.time4tfc.network;

import net.anodecathode.time4tfc.network.packet.PacketServerSettings;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("time4tfc");

    public static void initialize() {
        INSTANCE.registerMessage(PacketServerSettings.class, PacketServerSettings.class, 0, Side.CLIENT);
    }
}
