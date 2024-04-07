package su.terrafirmagreg.api.network.tile;

import su.terrafirmagreg.Tags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class TileDataServiceLogger {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID + "." + TileDataService.class.getSimpleName());

    private TileDataServiceLogger() {}
}
