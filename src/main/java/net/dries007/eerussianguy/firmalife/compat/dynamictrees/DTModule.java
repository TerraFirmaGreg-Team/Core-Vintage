package net.dries007.eerussianguy.firmalife.compat.dynamictrees;

import su.terrafirmagreg.api.util.GameUtils;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.dries007.eerussianguy.firmalife.compat.ModuleCore;

public class DTModule extends ModuleCore {

  public DTModule() {
    super("dynamictreestfc");
  }

  @Override
  public boolean isLoaded() {
    return GameUtils.isModLoaded(this.getDep());
  }

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    addRegistry(new DTRegistry());
  }

  @Override
  public void init(FMLInitializationEvent event) {
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {

  }
}
