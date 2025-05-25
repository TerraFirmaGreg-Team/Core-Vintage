package su.terrafirmagreg.api.library;

import net.minecraftforge.fml.common.event.FMLStateEvent;

@FunctionalInterface
public interface EventStateWrapper<E extends FMLStateEvent> {

  void route(E event);

}
