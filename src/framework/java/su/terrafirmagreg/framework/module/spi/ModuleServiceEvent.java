package su.terrafirmagreg.framework.module.spi;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleServiceEvent extends Event {

  public static class RegisterNetworkEvent extends ModuleServiceEvent {

  }

  public static class RegisterFeatureEvent extends ModuleServiceEvent {

  }

  public static class RegisterCommandEvent extends ModuleServiceEvent {

  }
}
