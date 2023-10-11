package su.terrafirmagreg.tfc.modules.core;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.modules.ModuleTFG;
import su.terrafirmagreg.api.util.CreativeTabBase;
import su.terrafirmagreg.tfc.TFCModules;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.network.IPacketService;
import su.terrafirmagreg.util.network.tile.ITileDataService;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.Tags.MOD_ID;
import static su.terrafirmagreg.api.Tags.MOD_NAME;


@ModuleTFG(
        moduleID = TFCModules.MODULE_CORE,
        containerID = TFCModules.CONTAINER,
        name = "TerraFirmaCraft Core",
        descriptionKey = "tfc.modules.core.description",
        coreModule = true
)
public class ModuleCore extends ModuleBase {

    public static final CreativeTabs MISC_TAB = new CreativeTabBase("misc", "tfc:wand");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCore.class.getSimpleName());
    public static IPacketService PACKET_SERVICE;
    public static ITileDataService TILE_DATA_SERVICE;


    public ModuleCore() {
        super(0, MOD_ID);

        //this.setRegistry(new Registry(MOD_ID, MISC_TAB));
        //this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();
        //TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);

        //MinecraftForge.EVENT_BUS.register(this);
    }

    @Nonnull
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
