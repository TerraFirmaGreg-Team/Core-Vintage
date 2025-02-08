package su.terrafirmagreg.framework.module.spi;

import su.terrafirmagreg.framework.command.CommandManager;
import su.terrafirmagreg.framework.command.api.ICommandManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.network.NetworkManager;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.RegistryManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import lombok.Getter;

@Getter
public abstract class ModuleBase implements IModule {

  private INetworkManager network;
  private IRegistryManager registry;
  private ICommandManager command;

  protected ModuleBase() {
    this.registry = RegistryManager.NoOp.of(this);
    this.network = NetworkManager.NoOp.of(this);
    this.command = CommandManager.NoOp.of(this);

  }

  protected INetworkManager enableNetwork() {
    this.network = NetworkManager.of(this);
    return this.network;
  }

  protected IRegistryManager enableRegistry() {
    this.registry = RegistryManager.of(this);
    return this.registry;
  }

  protected ICommandManager enableCommand() {
    this.command = CommandManager.of(this);
    return this.command;
  }

}
