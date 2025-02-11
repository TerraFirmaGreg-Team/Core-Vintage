package net.dries007.sharkbark.cellars;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.dries007.sharkbark.cellars.proxy.CommonProxy;
import net.dries007.sharkbark.cellars.util.Reference;
import net.dries007.sharkbark.cellars.util.handlers.PacketHandler;
import net.dries007.sharkbark.cellars.util.handlers.RegistryHandler;

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


  }

  @Mod.EventHandler
  public void PostInit(FMLPostInitializationEvent event) {
    Reference.initialized = true;
    PacketHandler.init();
  }

}
