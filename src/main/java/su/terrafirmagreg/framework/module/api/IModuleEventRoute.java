package su.terrafirmagreg.framework.module.api;

import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IModuleEventRoute {

  interface State<E extends FMLStateEvent> {

    void routeEvent(E event);

  }

  interface Registry<E extends Register<E> & IForgeRegistryEntry<E>> {

    void routeEvent(E event);

  }
}
