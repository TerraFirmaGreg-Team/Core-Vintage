package su.terrafirmagreg.modules.wood;


import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypeHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariantHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariantHandler;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.EntitiesWood;
import su.terrafirmagreg.modules.wood.init.ItemsWood;

import java.util.Collections;
import java.util.List;

@Module(moduleID = "Wood", name = "TFG Wood")
public class ModuleWood extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("Module Wood");
    public static final CreativeTabs WOOD_TAB = new CreativeTabBase("wood", "wood/planks/pine", false);

    public static IPacketService PACKET_SERVICE;
    public static ITileDataService TILE_DATA_SERVICE;

    public ModuleWood() {
        super(4);
        this.enableAutoRegistry(WOOD_TAB);

        PACKET_SERVICE = this.enableNetwork();
        TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);
    }

    @Override
    public void onRegister() {
        WoodTypeHandler.init();
//        WoodTreeVariantHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();

        BlocksWood.onRegister(registry);
        ItemsWood.onRegister(registry);
        EntitiesWood.onRegister(registry);
    }

    @Override
    public void onClientRegister() {

        EntitiesWood.onClientRegister(registry);

    }

    @Override
    public @NotNull Logger getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleWood.class);
    }
}
