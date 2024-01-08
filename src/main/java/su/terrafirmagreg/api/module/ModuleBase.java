package su.terrafirmagreg.api.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.modules.ITFGModule;
import su.terrafirmagreg.api.network.*;
import su.terrafirmagreg.api.registry.IRegistryEventHandler;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.registry.RegistryEventHandler;
import su.terrafirmagreg.api.registry.RegistryEventHandlerNoOp;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ModuleBase implements ITFGModule {

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
	private final String modId;
	private final Map<String, Set<String>> integrationPluginMap;
	private Registry registry;
	private IRegistryEventHandler registryEventHandler;
	private ThreadedNetworkWrapper threadedNetworkWrapper;
	private IPacketRegistry packetRegistry;
	private IPacketService packetService;
	private NetworkEntityIdSupplier networkEntityIdSupplier;
	@Getter
	@Setter
	private File configurationDirectory;

	protected ModuleBase() {
		this.modId = Loader.instance().activeModContainer().getModId();
		this.name = this.getClass().getSimpleName();
		this.integrationPluginMap = new HashMap<>();
		this.registryEventHandler = RegistryEventHandlerNoOp.INSTANCE;
	}

	// --------------------------------------------------------------------------

	protected void setRegistry(Registry registry) {

		if (this.registry != null) {
			throw new IllegalStateException("Trying to assign module registry after it has already been assigned");
		}

		this.registry = registry;
	}

	protected void enableAutoRegistry() {

		if (this.registry == null) {
			throw new IllegalStateException("Set module registry before enabling auto registry");
		}

		this.networkEntityIdSupplier = NETWORK_ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(
				this.modId,
				s -> new NetworkEntityIdSupplier()
		);
		this.registry.setNetworkEntityIdSupplier(this.networkEntityIdSupplier);

		if (this.registryEventHandler == RegistryEventHandlerNoOp.INSTANCE) {
			this.registryEventHandler = new RegistryEventHandler(this.registry);
		}
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
			this.threadedNetworkWrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(
					this.modId,
					ThreadedNetworkWrapper::new
			);
			this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(
					this.modId,
					s -> new PacketRegistry(this.threadedNetworkWrapper)
			);
			this.packetService = new PacketService(this.threadedNetworkWrapper);
		}

		return this.packetService;
	}

	// --------------------------------------------------------------------------
	// - Integration
	// --------------------------------------------------------------------------

	protected void registerIntegrationPlugin(String modId, String plugin) {

		Set<String> list = this.integrationPluginMap.computeIfAbsent(modId, k -> new HashSet<>());
		list.add(plugin);
	}

	Map<String, Set<String>> getIntegrationPluginMap() {

		return this.integrationPluginMap;
	}

	// --------------------------------------------------------------------------
	// - Registration
	// --------------------------------------------------------------------------

	/**
	 * This is called to allow the module to register blocks, items, etc.
	 * <p>
	 * Only called if {@link ModuleBase#enableAutoRegistry()} has been called in the module's constructor.
	 *
	 * @param registry the registry
	 */
	public void onRegister(Registry registry) {
	}

	/**
	 * This is called to allow the module to register client only things like models.
	 * <p>
	 * Only called if {@link ModuleBase#enableAutoRegistry()} has been called in the module's constructor.
	 *
	 * @param registry the registry
	 */
	public void onClientRegister(Registry registry) {
	}


	public void onRegisterBlock(RegistryEvent.Register<Block> event) {
		this.registryEventHandler.onRegisterBlockEvent(event);
	}

	public void onRegisterItem(RegistryEvent.Register<Item> event) {
		this.registryEventHandler.onRegisterItemEvent(event);
	}

	public void onRegisterPotion(RegistryEvent.Register<Potion> event) {
		this.registryEventHandler.onRegisterPotionEvent(event);
	}

	public void onRegisterBiome(RegistryEvent.Register<Biome> event) {
		this.registryEventHandler.onRegisterBiomeEvent(event);
	}

	public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		this.registryEventHandler.onRegisterSoundEvent(event);
	}

	public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {
		this.registryEventHandler.onRegisterPotionTypeEvent(event);
	}

	public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
		this.registryEventHandler.onRegisterEnchantmentEvent(event);
	}

	public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
		this.registryEventHandler.onRegisterVillagerProfessionEvent(event);
	}

	public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {
		this.registryEventHandler.onRegisterEntityEvent(event);
	}

	public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
		this.registryEventHandler.onRegisterRecipesEvent(event);
	}

	public void onRegisterTileEntities() {
		this.registryEventHandler.onRegisterTileEntitiesEvent();
	}

	@SideOnly(Side.CLIENT)
	public void onClientRegisterModels(ModelRegistryEvent event) {
		this.registryEventHandler.onClientRegisterModelsEvent(event);
	}

	// --------------------------------------------------------------------------
	// - FML Lifecycle
	// --------------------------------------------------------------------------

	public void onConstruction(FMLConstructionEvent event) {
		if (this.packetRegistry != null) {
			this.onNetworkRegister(this.packetRegistry);
		}
	}


	public void onPreInitialization(FMLPreInitializationEvent event) {
		this.setConfigurationDirectory(event.getModConfigurationDirectory());

		if (this.registry != null) {
			this.onRegister(this.registry);
		}
	}

	// --------------------------------------------------------------------------
	// - FML Lifecycle: Client
	// --------------------------------------------------------------------------

	@SideOnly(Side.CLIENT)
	public void onClientPreInit(FMLPreInitializationEvent event) {
		if (this.registry != null) {
			this.onClientRegister(this.registry);
		}
	}

	/**
	 * This is called to allow the module to register network packets.
	 * <p>
	 * Only called if {@link ModuleBase#enableNetwork()} has been called in the module's constructor.
	 *
	 * @param registry the packet registry
	 */
	public void onNetworkRegister(IPacketRegistry registry) {
	}


}
