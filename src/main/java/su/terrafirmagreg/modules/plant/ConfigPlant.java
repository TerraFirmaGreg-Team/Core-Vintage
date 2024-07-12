package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.modules.agriculture.ModuleAgriculture;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "plant")
public class ConfigPlant {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    public static final MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ConfigPlant.class);
    }

    public static final class BlocksCategory {

    }

    public static final class ItemsCategory {

    }

    public static class MiscCategory {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleAgriculture.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
