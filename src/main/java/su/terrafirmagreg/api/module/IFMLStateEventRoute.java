package su.terrafirmagreg.api.module;

import net.minecraftforge.fml.common.event.FMLStateEvent;

public interface IFMLStateEventRoute<E extends FMLStateEvent> {

    void routeEvent(E event);

}
