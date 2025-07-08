package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.command.api.ICommandManager;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface IModule {

  String getName();

  ResourceLocation getIdentifier();

  void setIdentifier(ResourceLocation identifier);

  LoggingHelper getLogger();

  IPacketManager getNetworkManager();

  IRegistryManager getRegistryManager();

  ICommandManager getCommandManager();

  IFeatureManager getFeatureManager();

  IPluginManager getPluginManager();

  /**
   * What other modules this module depends on.
   * <p>
   * for example <code>ModUtils.resource("tfg", "soil")</code> represents a dependency on the module "soil" in the container "tfg"
   */
  @Deprecated
  default @NotNull Set<ResourceLocation> getDependencyUids() {
    return Collections.emptySet();
  }

  /**
   * @return A list of classes to subscribe to the Forge event bus. As the class gets subscribed, not any specific instance, event handlers must be static!
   */
  default @NotNull List<Class<?>> getEventBusSubscribers() {
    return Collections.emptyList();
  }

  // ===== Other

  default boolean processIMC(FMLInterModComms.IMCMessage message) {
    return false;
  }

  // ===== Registration

  default void onNewRegister() {}


  default void onRegistryRegistrar(IRegistryRegistrar registrar) {}

  default void onPacketRegistrar(IPacketRegistrar registrar) {}

  default void onCommandRegistrar(ICommandRegistrar registrar) {}

  default void onFeatureRegistrar(IFeatureRegistrar registrar) {}

  default void onPluginRegistrar(IPluginRegistrar registrar) {}


}
