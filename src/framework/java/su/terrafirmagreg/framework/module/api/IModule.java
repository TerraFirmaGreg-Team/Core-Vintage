package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.command.api.ICommandManager;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.network.api.INetworkManager;
import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
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

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface IModule {

  ResourceLocation getIdentifier();

  void setIdentifier(ResourceLocation identifier);

  LoggingHelper getLogger();

  INetworkManager getNetworkManager();

  IRegistryManager getRegistryManager();

  ICommandManager getCommandManager();

  IFeatureManager getFeatureManager();

  /**
   * What other modules this module depends on.
   * <p>
   * for example <code>ModUtils.resource("tfg", "soil")</code> represents a dependency on the module "soil" in the container "tfg"
   */
  default @NotNull Set<ResourceLocation> getDependencyUids() {
    return Collections.emptySet();
  }

  /**
   * @return A list of classes to subscribe to the Forge event bus. As the class gets subscribed, not any specific instance, event handlers must be static!
   */
  default @NotNull List<Class<?>> getEventBusSubscribers() {
    return Collections.emptyList();
  }

  // ===== FML Lifecycle

  default void onConstruction(FMLConstructionEvent event) {}

  default void onPreInit(FMLPreInitializationEvent event) {}

  default void onInit(FMLInitializationEvent event) {}

  default void onPostInit(FMLPostInitializationEvent event) {}

  default void onLoadComplete(FMLLoadCompleteEvent event) {}

  // ===== FML Lifecycle: Client

  @SideOnly(Side.CLIENT)
  default void onClientPreInit(FMLPreInitializationEvent event) {
  }

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

  // ===== Other

  default boolean processIMC(FMLInterModComms.IMCMessage message) {
    return false;
  }

  // ===== Registration


  default void onNewRegister() {}


  default void onRegistry(IRegistryRegistrar registrar) {}

  default void onRegistryClient(IRegistryRegistrar registrar) {}

  default void onNetwork(INetworkRegistrar registrar) {}

  default void onCommand(ICommandRegistrar registrar) {}

  default void onFeature(IFeatureRegistrar registrar) {}


}
