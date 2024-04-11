package tfctech;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.util.LoggingUtils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


import tfctech.client.TechGuiHandler;
import tfctech.network.PacketFridgeUpdate;
import tfctech.network.PacketLatexUpdate;
import tfctech.network.PacketTileEntityUpdate;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCTECH;

@SuppressWarnings("WeakerAccess")
@Mod(modid = MODID_TFCTECH, name = TFCTech.NAME, version = Tags.VERSION, dependencies = TFCTech.DEPENDENCIES)
public class TFCTech {

    public static final String NAME = "TFCTech Unofficial";
    public static final String DEPENDENCIES = "after:tfc;after:ic2;after:gregtech";
    private static final boolean signedBuild = true;
    @SuppressWarnings("FieldMayBeFinal")
    @Mod.Instance
    private static TFCTech instance = null;
    private static final LoggingUtils LOGGER = new LoggingUtils(MODID_TFCTECH);
    private SimpleNetworkWrapper network;

    public static SimpleNetworkWrapper getNetwork() {
        return instance.network;
    }

    public static LoggingUtils getLog() {
        return LOGGER;
    }

    public static TFCTech getInstance() {
        return instance;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "tfctech.compat.waila.TOPPlugin");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TechGuiHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_TFCTECH);
        int id = 0;
        network.registerMessage(new PacketLatexUpdate.Handler(), PacketLatexUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketTileEntityUpdate.Handler(), PacketTileEntityUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketFridgeUpdate.Handler(), PacketFridgeUpdate.class, ++id, Side.CLIENT);
        if (!signedBuild) {
            LOGGER.error("INVALID FINGERPRINT DETECTED! This means this jar file has been compromised and are not supported.");
        }
    }
}
