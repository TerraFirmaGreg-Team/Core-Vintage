package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.modules.soil.client.GrassColorHandler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "soil")
public class ConfigSoil {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")

    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Item settings")

    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")

    public static final MiscCategory MISC = new MiscCategory();

    @Config.Name("Client")
    @Config.Comment("Client Side")

    public static final ClientCategory CLIENT = new ClientCategory();

    static {
        ConfigAnytime.register(ConfigSoil.class);
    }

    public static final class BlocksCategory {

        public final GrassPath PATH = new GrassPath();

        public static final class GrassPath {

            @Config.Name("Destroy Vegetation")
            @Config.Comment("When enabled, vegetation like tall grass or loose rocks gets destroyed")
            public boolean DESTROY_VEGETATION = true;

            @Config.Name("All Entities")
            @Config.Comment("When enabled, all entities create paths (performance intensive)")
            public boolean ALL_ENTITIES = false;

            @Config.Name("Grass -> Dirt")
            @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
            @Config.RangeDouble(min = 0.0, max = 1000.0)
            public double PLAYER_GRASS_TO_DIRT = 30.0;

            @Config.Name("Dirt -> Path")
            @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
            @Config.RangeDouble(min = 0.0, max = 1000.0)
            public double PLAYER_DIRT_TO_PATH = 10.0;

            @Config.Name("Grass -> Dirt")
            @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
            @Config.RangeDouble(min = 0.0, max = 1000.0)
            public double MOB_GRASS_TO_DIRT = 15.0;

            @Config.Name("Dirt -> Path")
            @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
            @Config.RangeDouble(min = 0.0, max = 1000.0)
            public double MOB_DIRT_TO_PATH = 5.0;
        }
    }

    public static final class ItemsCategory {

    }

    public static final class MiscCategory {

    }

    public static final class ClientCategory {

        @Config.Comment("If true, grass and foliage will be slightly varied in color.")

        public boolean noiseEnable = true;

        @Config.Comment("If true, grass and foliage will be colored seasonally.")

        public boolean seasonColorEnable = true;

        @Config.Comment("The noise scale. Default = 10")

        public float noiseScale = 10f;

        @Config.Comment("How many darkness levels should the noise have? Default = 5")

        public int noiseLevels = 5;

        @Config.Comment("How potent should the darkness be? Default = 0.15")

        public float noiseDarkness = 0.15f;

        @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 1155FF44")

        public String seasonColorSummer = "1155FF44";

        @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 55FFDD44")

        public String seasonColorAutumn = "55FFDD44";

        @Config.Comment("ARGB code for winter coloring in hexadecimal. Default: 335566FF")

        public String seasonColorWinter = "335566FF";

        @Config.Comment("ARGB code for spring coloring in hexadecimal. Default: 3355FFBB")

        public String seasonColorSpring = "3355FFBB";

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleSoil.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

                GrassColorHandler.resetColors();
            }
        }
    }

}
