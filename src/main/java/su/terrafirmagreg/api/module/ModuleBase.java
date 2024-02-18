package su.terrafirmagreg.api.module;

import lombok.Getter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.network.*;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.network.tile.TileDataServiceContainer;
import su.terrafirmagreg.api.registry.AutoRegistry;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.util.Helpers;

import java.io.File;
import java.util.*;


@Getter
public abstract class ModuleBase implements Comparable<ModuleBase> {

	/**
	 * Stores a network wrapper for each mod id.
	 */
	private static Map<String, ThreadedNetworkWrapper> NETWORK_WRAPPER_MAP = new HashMap<>();
	/**
	 * Stores a packet registry for each mod id.
	 */
	private static Map<String, IPacketRegistry> PACKET_REGISTRY_MAP = new HashMap<>();
	/**
	 * Stores a network entity id supplier for each mod id.
	 */
	private static Map<String, NetworkEntityIdSupplier> NETWORK_ENTITY_ID_SUPPLIER_MAP = new HashMap<>();

	@Getter
	private final String name;
	@Getter
	private final int priority;
	@NotNull
	private final String modid;

	protected RegistryManager registry;
	private ThreadedNetworkWrapper threadedNetworkWrapper;
	private IPacketRegistry packetRegistry;
	private IPacketService packetService;
	private ITileDataService tileDataService;
	private NetworkEntityIdSupplier networkEntityIdSupplier;
	@Getter
	private AutoRegistry autoRegistry;
	@Getter
	private File configurationDirectory;

	protected ModuleBase(int priority) {
		this.priority = priority;
		this.modid = Loader.instance().activeModContainer().getModId();
		this.name = this.getClass().getSimpleName();
	}

	protected void setConfigurationDirectory(File file) {
		this.configurationDirectory = file;
	}


	protected void enableAutoRegistry(CreativeTabs tab) {
		this.registry = new RegistryManager(tab).enableAutoRegistration();

		this.networkEntityIdSupplier = NETWORK_ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(this.modid, s -> new NetworkEntityIdSupplier());
		this.registry.setNetworkEntityIdSupplier(this.networkEntityIdSupplier);
		this.autoRegistry = registry.getAutoRegistry();
	}

	/**
	 * Call this in the constructor to enable network functionality for this module.
	 * <p>
	 * This will create a new network wrapper and packet registry for this module's
	 * mod id if they don't already exist. If they do already exist, the existing
	 * network wrapper and packet registry will be used.
	 *
	 * @return a reference to the module's packet service
	 */
	protected IPacketService enableNetwork() {

		if (this.threadedNetworkWrapper == null) {
			this.threadedNetworkWrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(this.modid, ThreadedNetworkWrapper::new);
			this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(this.modid, s -> new PacketRegistry(this.threadedNetworkWrapper));
			this.packetService = new PacketService(this.threadedNetworkWrapper);
		}

		return this.packetService;
	}

	protected ITileDataService enableNetworkTileDataService(IPacketService packetService) {

		if (this.tileDataService == null) {
			this.tileDataService = TileDataServiceContainer.register(Helpers.getID(this.name), packetService);
		}

		return this.tileDataService;
	}


	// ===== Registration ========================================================================================================================= //

	public void onRegister() {}

	public void onClientRegister() {}

	public void onNetworkRegister() {}

	// ===== FML Lifecycle ======================================================================================================================== //

	public void onConstruction(FMLConstructionEvent event) {}

	public void onPreInit(FMLPreInitializationEvent event) {}

	public void onInit(FMLInitializationEvent event) {}

	public void onPostInit(FMLPostInitializationEvent event) {}

	public void onLoadComplete(FMLLoadCompleteEvent event) {}

	// ===== FML Lifecycle: Client ================================================================================================================ //

	@SideOnly(Side.CLIENT)
	public void onClientPreInit(FMLPreInitializationEvent event) {}

	@SideOnly(Side.CLIENT)
	public void onClientInit(FMLInitializationEvent event) {}

	@SideOnly(Side.CLIENT)
	public void onClientPostInit(FMLPostInitializationEvent event) {}

	// ===== Server =============================================================================================================================== //

	public void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

	public void onServerStarting(FMLServerStartingEvent event) {}

	public void onServerStarted(FMLServerStartedEvent event) {}

	public void onServerStopping(FMLServerStoppingEvent event) {}

	public void onServerStopped(FMLServerStoppedEvent event) {}

	/**
	 * What other modules this module depends on.
	 * <p>
	 * e.g. <code>new ResourceLocation("tfg", "soil")</code> represents a dependency on the module
	 * "soil" in the container "tfg"
	 */
	@NotNull
	public Set<ResourceLocation> getDependencyUids() {
		return Collections.emptySet();
	}

	/**
	 * @return A list of classes to subscribe to the Forge event bus.
	 * As the class gets subscribed, not any specific instance, event handlers must be static!
	 */
	@NotNull
	public List<Class<?>> getEventBusSubscribers() {
		return Collections.emptyList();
	}

	public boolean processIMC(FMLInterModComms.IMCMessage message) {
		return false;
	}

	/**
	 * @return A logger to use for this module.
	 */
	@NotNull
	protected abstract Logger getLogger();

	// --------------------------------------------------------------------------
	// - Comparator
	// --------------------------------------------------------------------------

	public int compareTo(@NotNull ModuleBase otherModule) {
		return Integer.compare(otherModule.getPriority(), this.priority);
	}
}
