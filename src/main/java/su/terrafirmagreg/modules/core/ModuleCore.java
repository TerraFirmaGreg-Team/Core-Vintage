package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.api.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.api.capabilities.damage.HandlerDamageResistance;
import su.terrafirmagreg.api.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.api.capabilities.egg.HandlerEgg;
import su.terrafirmagreg.api.capabilities.food.CapabilityFood;
import su.terrafirmagreg.api.capabilities.food.HandlerFood;
import su.terrafirmagreg.api.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.api.capabilities.heat.HandlerHeat;
import su.terrafirmagreg.api.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.api.capabilities.metal.HandlerMetal;
import su.terrafirmagreg.api.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.api.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.api.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.api.capabilities.size.CapabilitySize;
import su.terrafirmagreg.api.capabilities.size.HandlerSize;
import su.terrafirmagreg.api.capabilities.temperature.CapabilityTemperature;
import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayPlayerData;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayTemperature;
import su.terrafirmagreg.modules.core.event.AmbientalEventHandler;
import su.terrafirmagreg.modules.core.event.CapabilitiesChunkEventHandler;
import su.terrafirmagreg.modules.core.event.CapabilitiesEntityEventHandler;
import su.terrafirmagreg.modules.core.event.CapabilitiesItemEventHandler;
import su.terrafirmagreg.modules.core.event.DebugInfoEventHandler;
import su.terrafirmagreg.modules.core.event.MaterialEventHandler;
import su.terrafirmagreg.modules.core.event.PuddlesEventHandler;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.CommandsCore;
import su.terrafirmagreg.modules.core.init.DataSerializersCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.CORE;

@Module(
        moduleID = CORE,
        description = "Core TFG content. Disabling this disables the entire mod and all its module.",
        coreModule = true
)
public final class ModuleCore extends ModuleBase {

    public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleCore.class.getSimpleName());
    public static final CreativeTabs CORE_TAB = new BaseCreativeTab("misc", "core/wand");

    public ModuleCore() {
        this.enableAutoRegistry(CORE_TAB);
        this.enableNetwork();
    }

    @Override
    public void onNetworkRegister() {

        PacketsCore.onRegister(packetRegistry);
    }

    @Override
    public void onRegister() {

        DataSerializersCore.onRegister(registryManager);
        BlocksCore.onRegister(registryManager);
        FluidsCore.onRegister(registryManager);
        ItemsCore.onRegister(registryManager);
        PotionsCore.onRegister(registryManager);
        LootTablesCore.onRegister(registryManager);
        CommandsCore.onRegister(registryManager);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaGreg.getInstance(), new GuiHandler());

        CapabilityChunkData.register();
        CapabilityEgg.register();
        CapabilityHeat.register();
        CapabilityFood.register();
        CapabilityMetal.register();
        CapabilityPull.register();
        CapabilitySharpness.register();
        CapabilitySize.register();
        CapabilityPlayer.register();
        CapabilityTemperature.register();
        CapabilityDamageResistance.register();

        MinecraftForge.EVENT_BUS.register(new AmbientalEventHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilitiesChunkEventHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilitiesItemEventHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilitiesEntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new MaterialEventHandler());
        MinecraftForge.EVENT_BUS.register(new PuddlesEventHandler());

    }

    @Override
    protected void onClientPreInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new OverlayTemperature());
        MinecraftForge.EVENT_BUS.register(new OverlayPlayerData());
        MinecraftForge.EVENT_BUS.register(new DebugInfoEventHandler());
    }

    @Override
    protected void onInit(FMLInitializationEvent event) {
        HandlerSize.init();
        HandlerFood.init();
        HandlerEgg.init();
        HandlerMetal.init();
        HandlerHeat.init();
        HandlerDamageResistance.init();
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
