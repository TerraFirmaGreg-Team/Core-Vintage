package net.dries007.tfc;


import net.dries007.tfc.module.core.ModuleCoreOld;
import net.dries007.tfc.proxy.IProxy;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.module.ModuleManager;

import java.util.HashSet;
import java.util.Set;

import static net.dries007.tfc.Tags.MOD_ID;

//@SuppressWarnings({"unused", "FieldMayBeFinal"})
//@Mod.EventBusSubscriber
//@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, useMetadata = true, guiFactory = GUI_FACTORY, dependencies = DEPENDENCIES)
public final class TerraFirmaCraft {
    public static WorldTypeTFC WORLD_TYPE_TFC;
    //    @SuppressWarnings("unused")
//    @Mod.Instance
    private static TerraFirmaCraft INSTANCE;

    //    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    private static IProxy PROXY;
    private final ModuleManager moduleManager;
    private Set<Class<? extends ModuleBase>> registeredModules = new HashSet<>();

    public TerraFirmaCraft() {
        WORLD_TYPE_TFC = new WorldTypeTFC();
        FluidRegistry.enableUniversalBucket();
        this.moduleManager = new ModuleManager(Tags.MOD_ID);
    }

    public static IProxy getProxy() {
        return PROXY;
    }

    public static TerraFirmaCraft getInstance() {
        return INSTANCE;
    }


    @Mod.EventHandler
    public void onConstructionEvent(FMLConstructionEvent event) {

//        this.moduleManager.registerModules(
//                ModuleCoreOld.class
//        );
//
//        // --- MODULES ---
//
//        if (MODULES.get(ModuleRock.MODULE_ID)) this.registerModule(ModuleRock.class);
//
//        if (MODULES.get(ModuleSoil.MODULE_ID)) this.registerModule(ModuleSoil.class);
//
//        if (MODULES.get(ModuleWood.MODULE_ID)) this.registerModule(ModuleWood.class);
//
//        if (MODULES.get(ModuleMetal.MODULE_ID)) this.registerModule(ModuleMetal.class);
//
//        if (MODULES.get(ModuleFood.MODULE_ID)) this.registerModule(ModuleFood.class);
//
//        if (MODULES.get(ModulePlant.MODULE_ID)) this.registerModule(ModulePlant.class);
//
//        if (MODULES.get(ModuleDevice.MODULE_ID)) this.registerModule(ModuleDevice.class);
//
//        if (MODULES.get(ModuleAnimal.MODULE_ID)) this.registerModule(ModuleAnimal.class);
//
//        if (MODULES.get(ModuleAgriculture.MODULE_ID)) this.registerModule(ModuleAgriculture.class);

        //this.registerModule(ModuleCorePost.class);

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
        ModuleCoreOld.LOGGER.info("Started PreInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void onInitializationEvent(FMLInitializationEvent event) {
        ModuleCoreOld.LOGGER.info("Started Initialization Phase!");

        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished Initialization Phase!");
    }

    @Mod.EventHandler
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        ModuleCoreOld.LOGGER.info("Started PostInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished PostInitialization Phase!");
    }

    @Mod.EventHandler
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        ModuleCoreOld.LOGGER.info("Started LoadComplete Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished LoadComplete Phase!");
    }

    @Mod.EventHandler
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        ModuleCoreOld.LOGGER.info("Started ServerAboutToStart Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished ServerAboutToStart Phase!");
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        ModuleCoreOld.LOGGER.info("Started ServerStarting Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished ServerStarting Phase!");
    }

    @Mod.EventHandler
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        ModuleCoreOld.LOGGER.info("Started ServerStarted Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished ServerStarted Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        ModuleCoreOld.LOGGER.info("Started ServerStopping Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished ServerStopping Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {
        ModuleCoreOld.LOGGER.info("Started ServerStopped Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCoreOld.LOGGER.info("Finished ServerStopped Phase!");
    }
}
