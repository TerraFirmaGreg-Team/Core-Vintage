package su.terrafirmagreg.api.module;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.network.PacketRegistry;
import su.terrafirmagreg.api.network.PacketService;
import su.terrafirmagreg.api.network.ThreadedNetworkWrapper;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ModuleBase implements IModule {

  /**
   * Stores a network wrapper for each mod id.
   */
  private static final Map<String, ThreadedNetworkWrapper> NETWORK_WRAPPER_MAP = new HashMap<>();
  /**
   * Stores a packet registry for each mod id.
   */
  private static final Map<String, IPacketRegistry> PACKET_REGISTRY_MAP = new HashMap<>();
  /**
   * Stores a network entity id supplier for each mod id.
   */
  private static final Map<String, NetworkEntityIdSupplier> NETWORK_ENTITY_ID_SUPPLIER_MAP = new HashMap<>();

  @Getter
  protected static IPacketService packetService;

  private final String name;
  @NotNull
  private final String modID;

  protected RegistryManager registryManager;
  protected Registry registry;
  protected IPacketRegistry packetRegistry;

  private ThreadedNetworkWrapper threadedNetworkWrapper;
  private NetworkEntityIdSupplier networkEntityIdSupplier;

  @Setter
  private File configurationDirectory;

  protected ModuleBase() {
    this.modID = this.getClass().getAnnotation(Module.class).moduleID().getID();
    this.name = this.getClass().getSimpleName();
  }

  protected void enableAutoRegistry() {

    enableAutoRegistry(null);
  }

  protected void enableAutoRegistry(CreativeTabs tab) {
    this.registryManager = new RegistryManager(tab, modID);

    this.networkEntityIdSupplier = NETWORK_ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(this.modID, s -> new NetworkEntityIdSupplier());
    this.registryManager.setNetworkEntityIdSupplier(this.networkEntityIdSupplier);
    this.registry = registryManager.getRegistry();
  }

  /**
   * Call this in the constructor to enable network functionality for this module.
   * <p>
   * This will create a new network wrapper and packet registry for this module's mod id if they don't already exist. If they do already exist, the existing network
   * wrapper and packet registry will be used.
   */
  protected void enableNetwork() {

    if (this.threadedNetworkWrapper == null) {
      this.threadedNetworkWrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(this.modID, ThreadedNetworkWrapper::new);
      this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(this.modID, s -> new PacketRegistry(this.threadedNetworkWrapper));
      packetService = new PacketService(this.threadedNetworkWrapper);
    }

  }

  /**
   * @return A logger to use for this module.
   */
  public @NotNull LoggingHelper getLogger() {
    return LoggingHelper.of();
  }
}
