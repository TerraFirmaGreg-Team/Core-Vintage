package su.terrafirmagreg.util;

import net.minecraftforge.common.config.Config;

@Config(modid = UtilTFG.MOD_ID, name = UtilTFG.MOD_ID + "/" + UtilTFG.MOD_ID)
public class UtilTFGConfig {

    public static TileDataService TILE_DATA_SERVICE = new TileDataService();
    public static InteractionClient INTERACTION_CLIENT = new InteractionClient();

    public static class TileDataService {

        @Config.Comment({
                "Set to true to enable client-side display of Athenaeum's tile data service.",
                "Used for monitoring TE updates from the server for any TE registered with",
                "the data service."
        })
        public boolean ENABLED = false;

        @Config.Comment({
                "Change the update rate for the display."
        })
        public short UPDATE_INTERVAL_TICKS = 20;

        @Config.Comment({
                "Set the value that will be tracked (the blue value) in the display. "
        })
        public int TRACKING_INDEX = 10;

    }

    public static class InteractionClient {

        @Config.Comment({
                "Set to true to render the interaction bounds for debugging."
        })
        public boolean SHOW_INTERACTION_BOUNDS = false;

        @Config.Comment({
                "If true, the quantities shown when sneaking and looking at a block",
                "will always be shown, regardless of sneaking."
        })
        public boolean ALWAYS_SHOW_QUANTITIES = false;
    }

}
