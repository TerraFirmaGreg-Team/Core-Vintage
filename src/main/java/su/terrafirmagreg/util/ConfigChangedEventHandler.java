package su.terrafirmagreg.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = UtilTFG.MOD_ID)
public class ConfigChangedEventHandler {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equals(UtilTFG.MOD_ID)) {
            ConfigManager.sync(UtilTFG.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
