package net.sharkbark.cellars;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.sharkbark.cellars.proxy.CommonProxy;
import net.sharkbark.cellars.util.Reference;
import net.sharkbark.cellars.util.handlers.PacketHandler;
import net.sharkbark.cellars.util.handlers.RegistryHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:tfc")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class Main {

  @Mod.Instance
  public static Main INSTANCE;

  @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
  public static CommonProxy proxy;

  @Mod.EventHandler
  public void PreInit(FMLPreInitializationEvent event) {
    ModConfig.loadConfig(event);
  }

  @Mod.EventHandler
  public void Init(FMLInitializationEvent event) {

    RegistryHandler.initRegistries();

    Reference.COOL = new FoodTrait("sharkCool", ModConfig.coolMod);
    Reference.ICY = new FoodTrait("sharkIcy", ModConfig.icyMod);
    Reference.FREEZING = new FoodTrait("sharkIcle", ModConfig.icleMod);
    Reference.DRY = new FoodTrait("sharkDry", ModConfig.dryMod);
    Reference.PRESERVING = new FoodTrait("sharkPreserving", ModConfig.preservingMod);


  }

  @Mod.EventHandler
  public void PostInit(FMLPostInitializationEvent event) {
    Reference.initialized = true;
    PacketHandler.init();
  }

}
