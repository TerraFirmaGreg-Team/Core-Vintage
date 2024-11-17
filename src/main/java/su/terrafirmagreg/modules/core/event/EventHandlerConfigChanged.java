package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
public class EventHandlerConfigChanged {

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(MOD_ID)) {
      ModuleCore.LOGGER.warn("Config changed");
      ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

      GrassColorHandler.resetColors();

      ProviderTemperature.AVERAGE = ConfigCore.MISC.TEMPERATURE.averageTemperature;
      ProviderTemperature.HOT_THRESHOLD = ConfigCore.MISC.TEMPERATURE.hotTemperature;
      ProviderTemperature.COOL_THRESHOLD = ConfigCore.MISC.TEMPERATURE.coldTemperature;
      ProviderTemperature.BURN_THRESHOLD = ConfigCore.MISC.TEMPERATURE.burningTemperature;
      ProviderTemperature.FREEZE_THRESHOLD = ConfigCore.MISC.TEMPERATURE.freezingTemperature;
    }
  }
}
