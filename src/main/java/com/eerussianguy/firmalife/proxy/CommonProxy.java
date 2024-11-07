package com.eerussianguy.firmalife.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.world.WorldGeneratorFL;

@Mod.EventBusSubscriber(modid = FirmaLife.MOD_ID)
public class CommonProxy {

  public void preInit(FMLPreInitializationEvent e) {
    GameRegistry.registerWorldGenerator(new WorldGeneratorFL(), 0);
  }

  public void init(FMLInitializationEvent e) {

  }

  public void postInit(FMLPostInitializationEvent e) {
  }
}
