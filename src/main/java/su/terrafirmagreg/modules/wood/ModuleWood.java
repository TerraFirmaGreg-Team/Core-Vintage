package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypeHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariantHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariantHandler;
import su.terrafirmagreg.modules.wood.event.EntityJoinWorldEventHandler;
import su.terrafirmagreg.modules.wood.event.KeyEventHandler;
import su.terrafirmagreg.modules.wood.event.MissingMappingEventHandler;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.EntitiesWood;
import su.terrafirmagreg.modules.wood.init.ItemsWood;
import su.terrafirmagreg.modules.wood.init.KeybindingsWood;
import su.terrafirmagreg.modules.wood.init.PacketWood;
import su.terrafirmagreg.modules.wood.init.RegistryWood;
import su.terrafirmagreg.modules.wood.init.recipes.LoomRecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.Modules.Wood;

@Module(moduleID = Wood)
public final class ModuleWood extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleWood.class.getSimpleName());
    public static final CreativeTabs WOOD_TAB = new BaseCreativeTab("wood", "wood/planks/pine");

    public static IPacketService PACKET_SERVICE;

    public ModuleWood() {
        this.enableAutoRegistry(WOOD_TAB);

        PACKET_SERVICE = this.enableNetwork();
    }

    @Override
    public void onNewRegister() {
        RegistryWood.onRegister();

    }

    @Override
    public void onNetworkRegister() {
        PacketWood.onRegister(packetRegistry);

    }

    @Override
    protected void onRecipesRegister() {
        LoomRecipes.onRegister();

    }

    @Override
    public void onRegister() {
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();

        BlocksWood.onRegister(registryManager);
        ItemsWood.onRegister(registryManager);
        EntitiesWood.onRegister(registryManager);

    }

    @Override
    public void onClientRegister() {
        BlocksWood.onClientRegister(registryManager);
        EntitiesWood.onClientRegister(registryManager);
        KeybindingsWood.onClientRegister(registryManager);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());
        MinecraftForge.EVENT_BUS.register(new MissingMappingEventHandler());
    }

    @Override
    public void onClientPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KeyEventHandler());
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleWood.class);
    }
}
