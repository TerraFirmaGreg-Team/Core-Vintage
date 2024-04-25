package net.anodecathode.time4tfc.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


import net.anodecathode.time4tfc.network.packet.PacketServerSettings;

import static su.terrafirmagreg.api.lib.Constants.MODID_TIME4TFC;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_TIME4TFC);

    public static void initialize() {
        INSTANCE.registerMessage(PacketServerSettings.class, PacketServerSettings.class, 0, Side.CLIENT);
    }
}
