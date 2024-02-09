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
import su.terrafirmagreg.modules.wood.init.ItemsWood;

@Module(moduleID = "Wood", name = "TFG Wood")
public class ModuleWood extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("ModuleWood");
    public static final CreativeTabs MISC_TAB = new CreativeTabBase("wood", "wood/planks/pine", false);

    public static IPacketService PACKET_SERVICE;
    public static ITileDataService TILE_DATA_SERVICE;

    public ModuleWood() {
        super(4);
        this.enableAutoRegistry(MISC_TAB);

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
    }

    @Override
    public @NotNull Logger getLogger() {
        return LOGGER;
    }
}
