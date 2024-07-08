package su.terrafirmagreg.modules.world;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "world")
public class ConfigWorld {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Item settings")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Misc settings")
    public static final MiscCategory MISC = new MiscCategory();

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

    }

    public static final class MiscCategory {

        @Config.RequiresMcRestart
        @Config.Comment({
                "This controls which registered entities can respawn in TFG biomes.",
                "You must specify by following the pattern 'modid:entity <rarity> <minGroupSpawn> <maxGroupSpawn>'",
                "Invalid entries will be ignored." })
        public String[] respawnableCreatures = {
                "tfc:blackbeartfc 30 1 2", "tfc:boartfc 30 1 2", "tfc:cougartfc 30 1 2",
                "tfc:coyotetfc 30 3 6", "tfc:deertfc 70 2 4", "tfc:direwolftfc 30 1 2",
                "tfc:gazelletfc 70 2 4", "tfc:grizzlybeartfc 30 1 2", "tfc:grousetfc 70 2 3",
                "tfc:haretfc 70 2 3", "tfc:hyenatfc 30 3 6", "tfc:jackaltfc 30 3 6",
                "tfc:liontfc 30 1 2", "tfc:mongoosetfc 30 1 2", "tfc:muskoxtfc 70 2 4",
                "tfc:ocelottfc 70 2 4", "tfc:panthertfc 30 1 2", "tfc:pheasanttfc 70 2 3",
                "tfc:polarbeartfc 30 1 2", "tfc:quailtfc 70 2 3", "tfc:rabbittfc 70 2 3",
                "tfc:sabertoothtfc 30 1 2", "tfc:turkeytfc 70 2 3", "tfc:wildebeesttfc 70 2 4",
                "tfc:wolftfc 70 2 4", "tfc:yaktfc 70 2 4", "tfc:zebutfc 70 2 4"
        };
    }

    static {
        ConfigAnytime.register(ConfigWorld.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleWorld.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

}
