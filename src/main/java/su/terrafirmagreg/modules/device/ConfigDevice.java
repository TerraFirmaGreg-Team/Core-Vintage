package su.terrafirmagreg.modules.device;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "device")
public class ConfigDevice {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Item settings")
    public static ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    public static MiscCategory MISC = new MiscCategory();

    public static class BlocksCategory {

    }

    public static class ItemsCategory {

        @Config.Comment("Water Flasks")
        public WaterFlasks WATER_FLASKS = new WaterFlasks();

        public static final class WaterFlasks {

            @Config.Comment("Liquid Capacity of Leather Flask (500 = 1/2 bucket = 5 drinks or 2 water bars)")
            @Config.RangeInt(min = 100)
            public int leatherCap = 500;

            @Config.Comment("Liquid Capacity of Iron Flask (1000 = 1 bucket = 10 drinks or 4 water bars)")
            @Config.RangeInt(min = 100)
            public int ironCap = 2000;

            @Config.Comment("Damage Capability of Flasks are Capacity/(this value) 0 = MAXINT uses")
            @Config.RangeInt(min = 0)
            public int damageFactor = 5;

        }

    }

    public static class MiscCategory {

    }

    static {
        ConfigAnytime.register(ConfigDevice.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleDevice.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

}
