package su.terrafirmagreg.framework.module.spi;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleEvent extends Event {

  public static class RegisterNetworkEvent extends ModuleEvent {

  }

  public static class RegisterFeatureEvent extends ModuleEvent {

  }

  public static class RegisterCommandEvent extends ModuleEvent {

  }
}
