package net.dries007.tfc;


import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.module.ModuleManager;
import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.module.wood.ModuleWood;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.minecraft.util.ResourceLocation;
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

import java.util.HashSet;
import java.util.Set;

import static net.dries007.tfc.Tags.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@Mod.EventBusSubscriber
@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, useMetadata = true, guiFactory = GUI_FACTORY, dependencies = DEPENDENCIES)
public final class TerraFirmaCraft {
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final WorldTypeTFC WORLD_TYPE_TFC = new WorldTypeTFC();
    public static SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MOD_ID);

    @SuppressWarnings("unused")
    @Mod.Instance
    private static TerraFirmaCraft INSTANCE = null;
    @SidedProxy(modId = MOD_ID,
                clientSide = "net.dries007.tfc.client.ClientProxy",
                serverSide = "net.dries007.tfc.common.CommonProxy")
    private static CommonProxy PROXY;
    private final ModuleManager moduleManager;
    private Set<Class<? extends ModuleBase>> registeredModules = new HashSet<>();
    private static int networkIdCounter = 0;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public TerraFirmaCraft() {
        this.moduleManager = new ModuleManager(MOD_ID);

        LOGGER.info("oleg ayyyyy0" + this);
    }

    public static CommonProxy getProxy() {
        return PROXY;
    }

    public static TerraFirmaCraft getInstance() {
        return INSTANCE;
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Используй это только на preInit фазе.
     */
    public static <T extends IMessage> void registerNetwork(IMessageHandler<T, IMessage> handler, Class<T> packetLatexUpdateClass, Side side) {
        NETWORK.registerMessage(handler, packetLatexUpdateClass, networkIdCounter++, side);
    }


    @Mod.EventHandler
    public void onConstructionEvent(FMLConstructionEvent event) {

        this.moduleManager.registerModules(ModuleWood.class);

        this.moduleManager.onConstructionEvent();
        this.moduleManager.routeFMLStateEvent(event);
    }

    private void registerModule(Class<? extends ModuleBase> moduleClass) {

        this.moduleManager.registerModules(moduleClass);
        this.registeredModules.add(moduleClass);
    }

    public boolean isModuleEnabled(Class<? extends ModuleBase> moduleClass) {

        return this.registeredModules.contains(moduleClass);
    }

    @Mod.EventHandler
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

        LOGGER.info("Started PreInitialization Phase!");
        PROXY.onPreInit(event);
        LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void onInitializationEvent(FMLInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

        LOGGER.info("Started Initialization Phase!");
        PROXY.onInit(event);
        LOGGER.info("Finished Initialization Phase!");
    }

    @Mod.EventHandler
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

        LOGGER.info("Started PostInitialization Phase!");
        PROXY.onPostInit(event);
        LOGGER.info("Finished PostInitialization Phase!");
    }

    @Mod.EventHandler
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

        LOGGER.info("Started LoadComplete Phase!");
        PROXY.onLoadComplete(event);
        LOGGER.info("Finished LoadComplete Phase!");
    }

    @Mod.EventHandler
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

        LOGGER.info("Started ServerStarting Phase!");
        PROXY.onServerStarting(event);
        LOGGER.info("Finished ServerStarting Phase!");
    }

    @Mod.EventHandler
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

    }

    @Mod.EventHandler
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);

    }

    @Mod.EventHandler
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {

        this.moduleManager.routeFMLStateEvent(event);
    }
}
