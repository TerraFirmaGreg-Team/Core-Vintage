package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.api.util.LoggingUtils;
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

@ModuleTFG(moduleID = "Core", name = "TFG Module Core",
           description = "Core TFG content. Disabling this disables the entire mod and all its module.")
public final class ModuleCore extends ModuleBase {

    public static final LoggingUtils LOGGER = new LoggingUtils(ModuleCore.class.getSimpleName());
    public static final CreativeTabs CORE_TAB = new CreativeTabBase("misc", "core/wand");

    public static IPacketService PACKET_SERVICE;

    public ModuleCore() {
        super(1);
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
    public @NotNull LoggingUtils getLogger() {
        return LOGGER;
    }
}
