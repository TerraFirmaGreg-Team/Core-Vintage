package su.terrafirmagreg.modules.arboriculture;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "arboriculture")
public class ModuleArboricultureConfig {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    @Config.LangKey("config." + MOD_ID + ".arboriculture.blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Item settings")
    @Config.LangKey("config." + MOD_ID + ".arboriculture.items")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    @Config.LangKey("config." + MOD_ID + ".arboriculture.misc")
    public static final MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ModuleArboricultureConfig.class);
    }

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

    }

    public static final class MiscCategory {

        @Config.Comment({ "Enable trees being fully cut by axes.",
                "Disable it if you want other mods to handle tree felling." })
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.enableFelling")
        public boolean enableFelling = true;

        @Config.Comment("Enable smacking logs with a hammer giving sticks.")
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.enableHammerSticks")
        public boolean enableHammerSticks = true;

        @Config.Comment(
                "Should logs require tools (axes and saws, or hammers for sticks) to be cut? If false, breaking logs with an empty hand will not drop logs.")
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.requiresAxe")
        public boolean requiresAxe = true;

        @Config.Comment("If false, leaves will not drop saplings.")
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.enableSaplings")
        public boolean enableSaplings = true;

        @Config.Comment("Chance per log for an item to drop when using a stone axe.")
        @Config.RangeDouble(min = 0, max = 1)
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.stoneAxeReturnRate")
        public double stoneAxeReturnRate = 0.6;

        @Config.Comment("Normal leaf drop chance for sticks")
        @Config.RangeDouble(min = 0, max = 1)
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.leafStickDropChance")
        public double leafStickDropChance = 0.1;

        @Config.Comment("Chance that leaves will drop more sticks when harvested with configured tool classes.")
        @Config.RangeDouble(min = 0, max = 1)
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.leafStickDropChanceBonus")
        public double leafStickDropChanceBonus = 0.25;

        @Config.Comment("Tool classes that have the configured bonus to drop more sticks when harvesting leaves.")
        @Config.LangKey("config." + MOD_ID + ".arboriculture.misc.leafStickDropChanceBonusClasses")
        public String[] leafStickDropChanceBonusClasses = {
                "knife",
                "scythe"
        };

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleArboriculture.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

}
