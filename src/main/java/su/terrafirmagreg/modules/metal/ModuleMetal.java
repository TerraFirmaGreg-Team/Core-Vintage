package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.modules.core.event.MaterialEventHandler;
import su.terrafirmagreg.modules.metal.api.types.type.MetalTypeHandler;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.init.EntitiesMetal;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;
import su.terrafirmagreg.modules.metal.init.RegistriesMetal;
import su.terrafirmagreg.modules.metal.plugin.top.PluginTheOneProbe;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

//@Module(moduleID = METAL)
public final class ModuleMetal extends ModuleBase {

    public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleMetal.class.getSimpleName());

    public final CreativeTabs METAL_TAB;

    public ModuleMetal() {
        this.METAL_TAB = BaseCreativeTab.of("metal", "metal/anvil/red_steel");

        this.enableAutoRegistry(METAL_TAB);
        this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(new MaterialEventHandler());
    }

    @Override
    public void onRegister() {
        MetalTypeHandler.init();

        BlocksMetal.onRegister(registryManager);
        ItemsMetal.onRegister(registryManager);
        EntitiesMetal.onRegister(registryManager);
    }

    @Override
    public void onClientRegister() {

        EntitiesMetal.onClientRegister(registryManager);
    }

    @Override
    public void onNewRegister() {

        RegistriesMetal.onRegister();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {

        PluginTheOneProbe.init();
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

    @Override
    public @NotNull List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleMetal.class);
    }
}
