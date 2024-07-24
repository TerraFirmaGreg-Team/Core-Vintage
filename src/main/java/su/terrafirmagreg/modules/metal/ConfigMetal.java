package su.terrafirmagreg.modules.metal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "metal")
public class ConfigMetal {

    @Config.Name("Block")
    @Config.Comment("Block settings")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Item")
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

    }

    static {
        ConfigAnytime.register(ConfigMetal.class);
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
