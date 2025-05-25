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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Это пока реализации чего-то, что может быть изменено с помощью системы событий,
// альтернатива есть в модулях, но от туда она будет удалена, в пользу этой реализации
public interface IFeature {

  boolean hasSubscriptions();

  boolean isEnabled();

  // ===== FML Lifecycle

  default void onPreInit(FMLPreInitializationEvent event) {}

  default void onInit(FMLInitializationEvent event) {}

  default void onPostInit(FMLPostInitializationEvent event) {}

  default void onLoadComplete(FMLLoadCompleteEvent event) {}

  // ===== FML Lifecycle: Client

  @SideOnly(Side.CLIENT)
  default void onClientPreInit(FMLPreInitializationEvent event) {}

  @SideOnly(Side.CLIENT)
  default void onClientInit(FMLInitializationEvent event) {}

  @SideOnly(Side.CLIENT)
  default void onClientPostInit(FMLPostInitializationEvent event) {}

  // ===== FML Lifecycle: Server

  default void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

  default void onServerStarting(FMLServerStartingEvent event) {}

  default void onServerStarted(FMLServerStartedEvent event) {}

  default void onServerStopping(FMLServerStoppingEvent event) {}

  default void onServerStopped(FMLServerStoppedEvent event) {}

}
