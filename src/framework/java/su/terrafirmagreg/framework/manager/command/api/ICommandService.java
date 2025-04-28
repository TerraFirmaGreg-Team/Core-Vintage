package su.terrafirmagreg.framework.manager.command.api;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface ICommandService {

  void routeEvent(FMLServerStartingEvent event);
}
