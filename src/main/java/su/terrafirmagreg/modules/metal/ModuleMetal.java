package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = Metal)
public final class ModuleMetal extends ModuleBase {

    public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleMetal.class.getSimpleName());
    public static final CreativeTabs METAL_TAB = new BaseCreativeTab("metal", "metal/anvil/red_steel");

    public static IPacketService PACKET_SERVICE;

    public ModuleMetal() {
        this.enableAutoRegistry(METAL_TAB);

        PACKET_SERVICE = this.enableNetwork();
    }

    @Override
    public void onRegister() {
        BlocksMetal.onRegister(registryManager);
        ItemsMetal.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
