package su.terrafirmagreg.modules.food;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import net.dries007.tfc.util.config.DecayTooltipMode;

import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "food")
public class ConfigFood {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    public static final MiscCategory MISC = new MiscCategory();

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

    }

    public static final class MiscCategory {

        public final Decay DECAY = new Decay();

        public static final class Decay {

            @Config.Comment("Modifier for how quickly food will decay. Higher values = faster decay. Set to 0 for infinite expiration time")
            @Config.RangeDouble(min = 0, max = 10)
            public double modifier = 1.0;

            @Config.Comment({ "The number of hours to which initial food decay will be synced. " +
                    "When a food item is dropped, it's initial expiration date will be rounded to the closest multiple of this (in hours)." })
            @Config.RangeInt(min = 1, max = 48)
            public int stackTime = 6;

            @Config.Comment("Food decay tooltip mode.")
            public DecayTooltipMode tooltipMode = DecayTooltipMode.ALL_INFO;
        }
    }

    static {
        ConfigAnytime.register(ConfigFood.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleFood.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
