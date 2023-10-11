package su.terrafirmagreg.util.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.modules.IModule;
import su.terrafirmagreg.util.network.*;
import su.terrafirmagreg.util.network.tile.ITileDataService;
import su.terrafirmagreg.util.network.tile.TileDataServiceContainer;
import su.terrafirmagreg.util.registry.IRegistryEventHandler;
import su.terrafirmagreg.util.registry.Registry;
import su.terrafirmagreg.util.registry.RegistryEventHandler;
import su.terrafirmagreg.util.registry.RegistryEventHandlerNoOp;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ModuleBase implements Comparable<ModuleBase>, IModule {

    /**
     * Stores a network wrapper for each mod id.
     */
    private static final Map<String, ThreadedNetworkWrapper> NETWORK_WRAPPER_MAP = new ConcurrentHashMap<>();
    /**
     * Stores a packet registry for each mod id.
     */
    private static final Map<String, IPacketRegistry> PACKET_REGISTRY_MAP = new ConcurrentHashMap<>();
    /**
     * Stores a network entity id supplier for each mod id.
     */
    private static final Map<String, NetworkEntityIdSupplier> NETWORK_ENTITY_ID_SUPPLIER_MAP = new ConcurrentHashMap<>();
    private final String name;
    private final int priority;
    private final String modId;
    private final Map<String, Set<String>> integrationPluginMap;
    private Registry registry;
    private IRegistryEventHandler registryEventHandler;
    private ThreadedNetworkWrapper threadedNetworkWrapper;
    private IPacketRegistry packetRegistry;
    private IPacketService packetService;
    private ITileDataService tileDataService;
    private NetworkEntityIdSupplier networkEntityIdSupplier;
    private File configurationDirectory;

    /**
     * Создает экземпляр класса ModuleBase с указанными приоритетом и идентификатором мода.
     *
     * @param priority Приоритет модуля.
     * @param modId    Идентификатор мода.
     */
    protected ModuleBase(int priority, String modId) {

        this.priority = priority;
        this.modId = modId;
        this.name = this.getClass().getSimpleName();
        this.integrationPluginMap = new HashMap<>();
        this.registryEventHandler = RegistryEventHandlerNoOp.INSTANCE;
    }

    // --------------------------------------------------------------------------

    /**
     * Возвращает имя модуля.
     *
     * @return Имя модуля.
     */
    public String getName() {

        return this.name;
    }

    /**
     * Возвращает приоритет модуля.
     *
     * @return Приоритет модуля.
     */
    public int getPriority() {

        return this.priority;
    }

    /**
     * Возвращает директорию конфигурации модуля.
     *
     * @return Директория конфигурации модуля.
     */
    public File getConfigurationDirectory() {

        return this.configurationDirectory;
    }

    /**
     * Устанавливает директорию конфигурации модуля.
     *
     * @param file Директория конфигурации модуля.
     */
    protected void setConfigurationDirectory(File file) {

        this.configurationDirectory = file;
    }

    /**
     * Устанавливает реестр модуля.
     *
     * @param registry Реестр модуля.
     */
    protected void setRegistry(Registry registry) {
        Objects.requireNonNull(this.registry, "Trying to assign module registry after it has already been assigned");

        this.registry = registry;
    }

    /**
     * Включает автоматическую регистрацию модуля.
     * <p>
     * Должно быть вызвано в конструкторе модуля.
     */
    protected void enableAutoRegistry() {
        Objects.requireNonNull(this.registry, "Set module registry before enabling auto registry");

        this.networkEntityIdSupplier = NETWORK_ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(this.modId, s -> new NetworkEntityIdSupplier());
        this.registry.setNetworkEntityIdSupplier(this.networkEntityIdSupplier);
        this.registryEventHandler = new RegistryEventHandler(this.registry);
    }

    /**
     * Вызовите этот метод в конструкторе, чтобы включить функциональность сети для этого модуля.
     * <p>
     * Если сетевой обертки и реестра пакетов для идентификатора модуля еще нет,
     * будет создана новая сетевая обертка и реестр пакетов. Если они уже существуют,
     * будут использованы существующая сетевая обертка и реестр пакетов.
     *
     * @return ссылка на сервис пакетов модуля
     */
    protected IPacketService enableNetwork() {
        if (this.threadedNetworkWrapper == null) {

            this.threadedNetworkWrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(this.modId, ThreadedNetworkWrapper::new);
            this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(this.modId, s -> new PacketRegistry(this.threadedNetworkWrapper));
            this.packetService = new PacketService(this.threadedNetworkWrapper);
        }

        return this.packetService;
    }

    protected ITileDataService enableNetworkTileDataService(IPacketService packetService) {

        if (this.tileDataService == null) {
            this.tileDataService = TileDataServiceContainer.register(new ResourceLocation(this.modId, this.name), packetService);
        }

        return this.tileDataService;
    }

    // --------------------------------------------------------------------------
    // - Интеграция
    // --------------------------------------------------------------------------

    protected void registerIntegrationPlugin(String modId, Object plugin) {

        Set<String> list = this.integrationPluginMap.computeIfAbsent(modId, k -> new HashSet<>());
        list.add(plugin.getClass().getName());
    }

    public Map<String, Set<String>> getIntegrationPluginMap() {

        return this.integrationPluginMap;
    }

    // --------------------------------------------------------------------------
    // - Компаратор
    // --------------------------------------------------------------------------


    public int compareTo(@Nonnull ModuleBase otherModule) {

        return Integer.compare(otherModule.getPriority(), this.priority);
    }

    // --------------------------------------------------------------------------
    // - Регистрация
    // --------------------------------------------------------------------------

    /**
     * Этот метод вызывается для регистрации блоков, предметов и т.д.
     * <p>
     * Вызывается только если в конструкторе модуля был вызван метод {@link ModuleBase#enableAutoRegistry()}.
     *
     * @param registry реестр
     */
    public void onRegister(Registry registry) {}

    /**
     * Этот метод вызывается для регистрации элементов, доступных только на клиенте, таких как модели.
     * <p>
     * Вызывается только если в конструкторе модуля был вызван метод {@link ModuleBase#enableAutoRegistry()}.
     *
     * @param registry реестр
     */
    public void onClientRegister(Registry registry) {}

    /**
     * Этот метод вызывается для регистрации сетевых пакетов.
     * <p>
     * Вызывается только если в конструкторе модуля был вызван метод {@link ModuleBase#enableNetwork()}.
     *
     * @param registry реестр пакетов
     */
    public void onNetworkRegister(IPacketRegistry registry) {}

    public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
        this.registryEventHandler.onRegisterBlockEvent(event);

    }

    public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
        this.registryEventHandler.onRegisterItemEvent(event);

    }

    public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
        this.registryEventHandler.onRegisterPotionEvent(event);

    }

    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        this.registryEventHandler.onRegisterBiomeEvent(event);

    }

    public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
        this.registryEventHandler.onRegisterSoundEvent(event);

    }

    public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
        this.registryEventHandler.onRegisterPotionTypeEvent(event);

    }

    public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
        this.registryEventHandler.onRegisterEnchantmentEvent(event);

    }

    public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        this.registryEventHandler.onRegisterVillagerProfessionEvent(event);

    }

    public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
        this.registryEventHandler.onRegisterEntityEvent(event);

    }

    public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
        this.registryEventHandler.onRegisterRecipesEvent(event);

    }

    public void onRegisterTileEntitiesEvent() {
        this.registryEventHandler.onRegisterTileEntitiesEvent();

    }

    @SideOnly(Side.CLIENT)
    public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
        this.registryEventHandler.onClientRegisterModelsEvent(event);

    }

    // --------------------------------------------------------------------------
    // - FML Lifecycle
    // --------------------------------------------------------------------------

    public void onConstructionEvent(FMLConstructionEvent event) {
        if (this.packetRegistry != null) {
            this.onNetworkRegister(this.packetRegistry);
        }
    }

    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        this.setConfigurationDirectory(event.getModConfigurationDirectory());

        if (this.registry != null) {
            this.onRegister(this.registry);
        }
    }

    public void onInitializationEvent(FMLInitializationEvent event) {}

    public void onPostInitializationEvent(FMLPostInitializationEvent event) {}

    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {}

    // --------------------------------------------------------------------------
    // - FML Lifecycle: Client
    // --------------------------------------------------------------------------

    @SideOnly(Side.CLIENT)
    public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {

        if (this.registry != null) {
            this.onClientRegister(this.registry);
        }
    }

    @SideOnly(Side.CLIENT)
    public void onClientInitializationEvent(FMLInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {}

    // --------------------------------------------------------------------------
    // - FML Lifecycle: Server
    // --------------------------------------------------------------------------

    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {}

    public void onServerStartingEvent(FMLServerStartingEvent event) {}

    public void onServerStartedEvent(FMLServerStartedEvent event) {}

    public void onServerStoppingEvent(FMLServerStoppingEvent event) {}

    public void onServerStoppedEvent(FMLServerStoppedEvent event) {}

}
