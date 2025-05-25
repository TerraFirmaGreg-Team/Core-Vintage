package su.terrafirmagreg.framework.manager.plugin.api;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPluginService {

  // ===== FML Lifecycle

  void onPreInit(FMLPreInitializationEvent event);

  void onInit(FMLInitializationEvent event);

  void onPostInit(FMLPostInitializationEvent event);

  void onLoadComplete(FMLLoadCompleteEvent event);

  // ===== FML Lifecycle: Client

  @SideOnly(Side.CLIENT)
  void onClientPreInit(FMLPreInitializationEvent event);

  @SideOnly(Side.CLIENT)
  void onClientInit(FMLInitializationEvent event);

  @SideOnly(Side.CLIENT)
  void onClientPostInit(FMLPostInitializationEvent event);

  // ===== FML Lifecycle: Server

  void onServerAboutToStart(FMLServerAboutToStartEvent event);

  void onServerStarting(FMLServerStartingEvent event);

  void onServerStarted(FMLServerStartedEvent event);

  void onServerStopping(FMLServerStoppingEvent event);

  void onServerStopped(FMLServerStoppedEvent event);
}
