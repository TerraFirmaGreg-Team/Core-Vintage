package su.terrafirmagreg.util.network.tile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.util.UtilTFG;

public final class TileDataServiceLogger {

    public static final Logger LOGGER = LogManager.getLogger(UtilTFG.MOD_ID + "." + TileDataService.class.getSimpleName());

    private TileDataServiceLogger() {
        //
    }
}
