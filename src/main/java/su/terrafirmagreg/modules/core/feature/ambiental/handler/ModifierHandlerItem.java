package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierItem;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderItem;

public final class ModifierHandlerItem {


  public static final AmbientalRegistry<IAmbientalProviderItem> ITEM = new AmbientalRegistry<>();

  static {
    ITEM.register(((player, stack) ->
                     ModifierItem.defined(stack.getItem().getRegistryName().toString(), 0f, 0f)
    ));
  }

}
