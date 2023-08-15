package net.dries007.tfc;


import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.TerraFirmaCraft.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@Mod.EventBusSubscriber
@Mod(
        modid = MOD_ID,
        name = MOD_NAME,
        useMetadata = true,
        guiFactory = GUI_FACTORY,
        dependencies = DEPENDENCIES)
public final class TerraFirmaCraft {
    public static final String MOD_ID = "tfc";
    public static final String MOD_NAME = "TerraFirmaCraft";
    public static final String GUI_FACTORY = "net.dries007.tfc.client.util.TFCModGuiFactory";
    public static final String DEPENDENCIES = "required:forge@[14.23.5.2847,);after:jei@[4.14.2,);after:gregtech;after:top@(1.8.25,)";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final WorldTypeTFC WORLD_TYPE_TFC = new WorldTypeTFC();
    public static SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MOD_ID);

    @Mod.Instance
    private static TerraFirmaCraft INSTANCE = null;
    @SidedProxy(
            modId = MOD_ID,
            clientSide = "net.dries007.tfc.client.ClientProxy",
            serverSide = "net.dries007.tfc.common.CommonProxy")
    private static CommonProxy PROXY;
    private static int networkIdCounter = 0;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static CommonProxy getProxy() {
        return PROXY;
    }

    public static TerraFirmaCraft getInstance() {
        return INSTANCE;
    }

    /**
     * Используй это только на preInit фазе.
     */
    public static <T extends IMessage> void registerNetwork(IMessageHandler<T, IMessage> handler, Class<T> packetLatexUpdateClass, Side side) {
        NETWORK.registerMessage(handler, packetLatexUpdateClass, networkIdCounter++, side);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Started PreInitialization Phase!");
        PROXY.onPreInit(event);
        LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Started Initialization Phase!");
        PROXY.onInit(event);
        LOGGER.info("Finished Initialization Phase!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Started PostInitialization Phase!");
        PROXY.onPostInit(event);
        LOGGER.info("Finished PostInitialization Phase!");
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("Started LoadComplete Phase!");
        PROXY.onLoadComplete(event);
        LOGGER.info("Finished LoadComplete Phase!");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        LOGGER.info("Started ServerStarting Phase!");
        PROXY.onServerStarting(event);
        LOGGER.info("Finished ServerStarting Phase!");
    }
}
