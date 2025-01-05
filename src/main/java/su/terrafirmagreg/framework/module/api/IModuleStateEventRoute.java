package su.terrafirmagreg.framework.module.api;

import net.minecraftforge.fml.common.event.FMLStateEvent;

public interface IModuleStateEventRoute<E extends FMLStateEvent> {

  void routeEvent(E event);

}
