package net.dries007.astikorcarts.proxy;

import net.dries007.astikorcarts.init.ModEntities;
import net.dries007.astikorcarts.init.ModKeybindings;

public class ClientProxy implements IProxy {

  public void preInit() {
    ModEntities.registerRenders();
  }

  public void init() {
    ModKeybindings.registerKeyBindings();
  }

  public void postInit() {

  }
}
