package net.dries007.firmalife.proxy;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.firmalife.world.WorldGeneratorFL;

@Mod.EventBusSubscriber(modid = ModIDs.FL)
public class CommonProxy {

  public void preInit(FMLPreInitializationEvent e) {
    GameRegistry.registerWorldGenerator(new WorldGeneratorFL(), 0);
  }

  public void init(FMLInitializationEvent e) {

  }

  public void postInit(FMLPostInitializationEvent e) {
  }
}
