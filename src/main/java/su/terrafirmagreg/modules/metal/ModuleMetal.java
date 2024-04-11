package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.api.util.LoggingUtils;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@ModuleTFG(moduleID = "Metal", name = "TFG Module Metal")
public final class ModuleMetal extends ModuleBase {

    public static final LoggingUtils LOGGER = new LoggingUtils(ModuleMetal.class.getSimpleName());
    public static final CreativeTabs METAL_TAB = new CreativeTabBase("metal", "metal/anvil/red_steel");

    public static IPacketService PACKET_SERVICE;

    public ModuleMetal() {
        super(8);
        this.enableAutoRegistry(METAL_TAB);

        PACKET_SERVICE = this.enableNetwork();
    }

    @Override
    public void onRegister() {
        BlocksMetal.onRegister(registryManager);
        ItemsMetal.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingUtils getLogger() {
        return LOGGER;
    }
}
