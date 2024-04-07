package su.terrafirmagreg.modules.device;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "device")
public class ModuleDeviceConfig {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    @Config.LangKey("config." + MOD_ID + ".device.blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Item settings")
    @Config.LangKey("config." + MOD_ID + ".device.items")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    @Config.LangKey("config." + MOD_ID + ".device.misc")
    public static final MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ModuleDeviceConfig.class);
    }

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

        @Config.Comment("Water Flasks")
        @Config.LangKey("config." + MOD_ID + ".device.water_flasks")
        public final WaterFlasks WATER_FLASKS = new WaterFlasks();

        public static final class WaterFlasks {

            @Config.Comment("Liquid Capacity of Leather Flask (500 = 1/2 bucket = 5 drinks or 2 water bars)")
            @Config.RangeInt(min = 100)
            @Config.LangKey("config." + MOD_ID + ".device.water_flasks.leatherCap")
            public int leatherCap = 500;

            @Config.Comment("Liquid Capacity of Iron Flask (1000 = 1 bucket = 10 drinks or 4 water bars)")
            @Config.RangeInt(min = 100)
            @Config.LangKey("config." + MOD_ID + ".device.water_flasks.ironCap")
            public int ironCap = 2000;

            @Config.Comment("Damage Capability of Flasks are Capacity/(this value) 0 = MAXINT uses")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MOD_ID + ".device.water_flasks.damageFactor")
            public int damageFactor = 5;

        }

    }

    public static final class MiscCategory {

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
