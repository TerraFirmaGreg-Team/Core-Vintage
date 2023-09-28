package net.dries007.tfc;


import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.module.ModuleManager;
import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.module.TFCModules;
import net.dries007.tfc.module.agriculture.ModuleAgriculture;
import net.dries007.tfc.module.animal.ModuleAnimal;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.ModuleCorePost;
import net.dries007.tfc.module.devices.ModuleDevice;
import net.dries007.tfc.module.metal.ModuleMetal;
import net.dries007.tfc.module.plant.ModulePlant;
import net.dries007.tfc.module.rock.ModuleRock;
import net.dries007.tfc.module.soil.ModuleSoil;
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
    public static SimpleNetworkWrapper network;
    @SuppressWarnings("unused")
    @Mod.Instance
    private static TerraFirmaCraft instance;
    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_SIDE)
    private static CommonProxy proxy;
    private static int networkIdCounter = 0;
    private final ModuleManager moduleManager;
    private Set<Class<? extends ModuleBase>> registeredModules = new HashSet<>();

    public TerraFirmaCraft() {
        instance = this;
        network = new SimpleNetworkWrapper(MOD_ID);
        FluidRegistry.enableUniversalBucket();
        this.moduleManager = new ModuleManager(MOD_ID);
    }

    public static CommonProxy getProxy() {
        return proxy;
    }

    public static TerraFirmaCraft getInstance() {
        return instance;
    }

    public static ResourceLocation getID(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Используй это только на preInit фазе.
     */
    public static <T extends IMessage> void registerNetwork(IMessageHandler<T, IMessage> handler, Class<T> packetLatexUpdateClass, Side side) {
        network.registerMessage(handler, packetLatexUpdateClass, networkIdCounter++, side);
    }


    @Mod.EventHandler
    public void onConstructionEvent(FMLConstructionEvent event) {

        this.moduleManager.registerModules(
                ModuleCore.class
        );

        // --- MODULES ---

        if (TFCModules.MODULES.get(ModuleRock.MODULE_ID)) {
            this.registerModule(ModuleRock.class);
        }

        if (TFCModules.MODULES.get(ModuleSoil.MODULE_ID)) {
            this.registerModule(ModuleSoil.class);
        }

        if (TFCModules.MODULES.get(ModuleWood.MODULE_ID)) {
            this.registerModule(ModuleWood.class);
        }

        if (TFCModules.MODULES.get(ModuleMetal.MODULE_ID)) {
            this.registerModule(ModuleMetal.class);
        }

//        if (TFCModules.MODULES.get(ModuleFood.MODULE_ID)) {
//            this.registerModule(ModuleFood.class);
//        }

        if (TFCModules.MODULES.get(ModulePlant.MODULE_ID)) {
            this.registerModule(ModulePlant.class);
        }

        if (TFCModules.MODULES.get(ModuleDevice.MODULE_ID)) {
            this.registerModule(ModuleDevice.class);
        }

        if (TFCModules.MODULES.get(ModuleAnimal.MODULE_ID)) {
            this.registerModule(ModuleAnimal.class);
        }

        if (TFCModules.MODULES.get(ModuleAgriculture.MODULE_ID)) {
            this.registerModule(ModuleAgriculture.class);
        }

        this.registerModule(ModuleCorePost.class);

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
        LOGGER.info("Started PreInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        proxy.onPreInit(event);
        LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void onInitializationEvent(FMLInitializationEvent event) {
        LOGGER.info("Started Initialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        proxy.onInit(event);
        LOGGER.info("Finished Initialization Phase!");
    }

    @Mod.EventHandler
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        LOGGER.info("Started PostInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        proxy.onPostInit(event);
        LOGGER.info("Finished PostInitialization Phase!");
    }

    @Mod.EventHandler
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        LOGGER.info("Started LoadComplete Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        proxy.onLoadComplete(event);
        LOGGER.info("Finished LoadComplete Phase!");
    }

    @Mod.EventHandler
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        LOGGER.info("Started ServerAboutToStart Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        LOGGER.info("Finished ServerAboutToStart Phase!");
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        LOGGER.info("Started ServerStarting Phase!");
        proxy.onServerStarting(event);
        this.moduleManager.routeFMLStateEvent(event);
        LOGGER.info("Finished ServerStarting Phase!");
    }

    @Mod.EventHandler
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        LOGGER.info("Started ServerStarted Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        LOGGER.info("Finished ServerStarted Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        LOGGER.info("Started ServerStopping Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        LOGGER.info("Finished ServerStopping Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {
        LOGGER.info("Started ServerStopped Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        LOGGER.info("Finished ServerStopped Phase!");
    }
}
