package su.terrafirmagreg.modules.metal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.lib.Constants.MOD_ID;
import static su.terrafirmagreg.api.lib.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "metal")
public class ModuleMetalConfig {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    @Config.LangKey("config." + MOD_ID + ".metal.blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    @Config.LangKey("config." + MOD_ID + ".metal.items")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    @Config.LangKey("config." + MOD_ID + ".metal.misc")
    public static final MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ModuleMetalConfig.class);
    }

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

    }

    public static final class MiscCategory {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleMetal.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
