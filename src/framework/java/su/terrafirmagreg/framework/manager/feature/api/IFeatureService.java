package su.terrafirmagreg.framework.manager.feature.api;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public interface IFeatureService {

  // ===== FML Lifecycle

  void onPreInit(FMLPreInitializationEvent event);

  void onInit(FMLInitializationEvent event);

  void onPostInit(FMLPostInitializationEvent event);

  void onLoadComplete(FMLLoadCompleteEvent event);

  // ===== FML Lifecycle: Server

  void onServerAboutToStart(FMLServerAboutToStartEvent event);

  void onServerStarting(FMLServerStartingEvent event);

  void onServerStarted(FMLServerStartedEvent event);

  void onServerStopping(FMLServerStoppingEvent event);

  void onServerStopped(FMLServerStoppedEvent event);
}
