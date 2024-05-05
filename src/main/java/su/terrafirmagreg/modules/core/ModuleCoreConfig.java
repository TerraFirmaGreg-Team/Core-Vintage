package su.terrafirmagreg.modules.core;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "core")
public class ModuleCoreConfig {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    @Config.LangKey("config." + MOD_ID + ".core.blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    @Config.LangKey("config." + MOD_ID + ".core.items")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    @Config.LangKey("config." + MOD_ID + ".core.misc")
    public static final MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ModuleCoreConfig.class);
    }

    public static final class BlocksCategory {

        @Config.Comment("Barrel")
        @Config.LangKey("config." + MOD_ID + ".core.blocks.puddle")
        public final Puddle PUDDLE = new Puddle();

        public static final class Puddle {

            @Config.Name("Puddle Rate")
            @Config.Comment({
                    "The game will pick a random block every tick for every active chunk",
                    "Then it will check if a puddle can be placed there",
                    "Then it generates a random number between 0-99",
                    "And if that number is less than this puddle rate number, it puts a puddle",
                    "That means any value over 99 will flood your world with puddles"
            })
            public int puddleRate = 15;

            @Config.Name("Can Use Glass Bottle")
            @Config.Comment({ "Toggles filling glass bottles with puddle water" })
            public boolean canUseGlassBottle = true;

        }

    }

    public static final class ItemsCategory {

        @Config.Comment("Chance for the fire starter to be successful")
        @Config.RangeDouble(min = 0d, max = 1d)
        @Config.LangKey("config." + MOD_ID + ".core.items.fireStarterChance")
        public double fireStarterChance = 0.5;

    }

    public static final class MiscCategory {

        public final Weight WEIGHT = new Weight();
        @Config.Name("Debug Mode")
        @Config.Comment("When enabled, prints debug values to console")
        public boolean DEBUG = false;

        public static final class Weight {

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
            @Config.LangKey("config." + MOD_ID + ".core.misc.weight.very_heavy")
            public int VERY_HEAVY = 1;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Heavy items. Default = 4")
            @Config.LangKey("config." + MOD_ID + ".core.misc.weight.heavy")
            public int HEAVY = 4;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Medium items. Default = 16")
            @Config.LangKey("config." + MOD_ID + ".core.misc.weight.medium")
            public int MEDIUM = 16;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Light items. Default = 32")
            @Config.LangKey("config." + MOD_ID + ".core.misc.weight.light")
            public int LIGHT = 32;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Very Light items. Default = 64")
            @Config.LangKey("config." + MOD_ID + ".core.misc.weight.very_light")
            public int VERY_LIGHT = 64;
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleCore.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
