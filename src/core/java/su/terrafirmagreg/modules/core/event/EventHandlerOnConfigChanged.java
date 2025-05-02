package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityProviderAmbiental;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
public class EventHandlerOnConfigChanged {

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(MOD_ID)) {
      ModuleCore.LOGGER.warn("Config changed");
      ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

//      GrassColorHandler.resetColors();

      CapabilityProviderAmbiental.AVERAGE = ConfigCore.FEATURE.AMBIENTAL.averageTemperature;
      CapabilityProviderAmbiental.HOT_THRESHOLD = ConfigCore.FEATURE.AMBIENTAL.hotTemperature;
      CapabilityProviderAmbiental.COOL_THRESHOLD = ConfigCore.FEATURE.AMBIENTAL.coldTemperature;
      CapabilityProviderAmbiental.BURN_THRESHOLD = ConfigCore.FEATURE.AMBIENTAL.burningTemperature;
      CapabilityProviderAmbiental.FREEZE_THRESHOLD = ConfigCore.FEATURE.AMBIENTAL.freezingTemperature;
    }
  }
}
