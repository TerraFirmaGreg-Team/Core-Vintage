package su.terrafirmagreg.framework.module.spi;

import su.terrafirmagreg.framework.manager.command.CommandManager;
import su.terrafirmagreg.framework.manager.command.api.ICommandManager;
import su.terrafirmagreg.framework.manager.feature.FeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.packet.PacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.plugin.PluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.registry.RegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.util.ResourceLocation;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class ModuleBase implements IModule {

  private final String name;
  @Setter
  private ResourceLocation identifier;

  private IPacketManager networkManager;
  private IRegistryManager registryManager;
  private ICommandManager commandManager;
  private IFeatureManager featureManager;
  private IPluginManager pluginManager;

  public ModuleBase(String name) {
    this.name = name;
  }


  public void enableNetwork() {

    this.networkManager = PacketManager.of(this);
  }

  protected void enableRegistry() {

    this.registryManager = RegistryManager.of(this);
  }

  protected void enableCommand() {

    this.commandManager = CommandManager.of(this);
  }

  protected void enableFeature() {

    this.featureManager = FeatureManager.of(this);
  }

  protected void enablePlugin() {

    this.pluginManager = PluginManager.of(this);
  }

}
