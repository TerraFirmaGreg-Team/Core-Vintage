package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry.ModuleSettings;
import su.terrafirmagreg.framework.module.base.BaseModule;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface IModuleEntry extends IBaseEntry<ModuleSettings, BaseModule> {


  FrameworkLogger getLogger();

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


  default void onRegistryRegistrar(IContentRegistrar registrar) {}

  default void onPacketRegistrar(IPacketRegistrar registrar) {}

  default void onCommandRegistrar(ICommandRegistrar registrar) {}

  default void onFeatureRegistrar(IFeatureRegistrar registrar) {}

  default void onPluginRegistrar(IPluginRegistrar registrar) {}

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class ModuleSettings extends BaseSettings<ModuleSettings> {

    protected boolean subscriptionEnabled = true;
    protected boolean packetManagerEnabled = true;
    protected boolean registryManagerEnabled = true;
    protected boolean commandManagerEnabled = true;
    protected boolean featureManagerEnabled = true;
    protected boolean pluginManagerEnabled = true;


    public static ModuleSettings of() {
      return new ModuleSettings();
    }

    public ModuleSettings disableSubscriptions() {
      this.subscriptionEnabled = false;
      return this.self();
    }

    public ModuleSettings disablePacket() {
      this.packetManagerEnabled = false;
      return this.self();
    }

    public ModuleSettings disableRegistry() {
      this.registryManagerEnabled = false;
      return this.self();
    }

    public ModuleSettings disableCommand() {
      this.commandManagerEnabled = false;
      return this.self();
    }

    public ModuleSettings disableFeature() {
      this.featureManagerEnabled = false;
      return this.self();
    }

    public ModuleSettings disablePlugin() {
      this.pluginManagerEnabled = false;
      return this.self();
    }

  }


}
