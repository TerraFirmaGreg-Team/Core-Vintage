package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.core.api.capabilities.pull.PullCapability;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketCore;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.Module_Core;

@Module(
        moduleID = Module_Core,
        description = "Core TFG content. Disabling this disables the entire mod and all its module.",
        coreModule = true
)
public final class ModuleCore extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleCore.class.getSimpleName());
    public static final CreativeTabs CORE_TAB = new CreativeTabBase("misc", "core/wand");

    public static IPacketService PACKET_SERVICE;

    public ModuleCore() {
        this.enableAutoRegistry(CORE_TAB);

        PACKET_SERVICE = this.enableNetwork();
    }

    @Override
    public void onNetworkRegister() {
        PacketCore.onRegister(packetRegistry);
    }

    @Override
    public void onRegister() {
        BlocksCore.onRegister(registryManager);
        ItemsCore.onRegister(registryManager);
        PotionsCore.onRegister(registryManager);
        LootTablesCore.onRegister(registryManager);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaGreg.getInstance(), new GuiHandler());

        PullCapability.preInit();
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
