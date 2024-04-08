package su.terrafirmagreg.api.module;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.network.PacketRegistry;
import su.terrafirmagreg.api.network.PacketService;
import su.terrafirmagreg.api.network.ThreadedNetworkWrapper;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.registry.RegistryManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
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


import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class ModuleBase implements Comparable<ModuleBase> {

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
    private final String name;
    @Getter
    private final int priority;
    @NotNull
    private final String modID;

    protected RegistryManager registryManager;
    protected IPacketRegistry packetRegistry;
    protected IPacketService packetService;
    protected ThreadedNetworkWrapper threadedNetworkWrapper;
    protected NetworkEntityIdSupplier networkEntityIdSupplier;

    @Getter
    private Registry registry;
    @Getter
    private File configurationDirectory;

    protected ModuleBase() {
        this(-1);
    }

    protected ModuleBase(int priority) {
        this(priority, Loader.instance().activeModContainer().getModId());
    }

    protected ModuleBase(int priority, @NotNull String modID) {
        this.priority = priority;
        this.modID = modID;
        this.name = this.getClass().getSimpleName();
    }

    protected void setConfigurationDirectory(File file) {
        this.configurationDirectory = file;
    }

    protected void enableAutoRegistry() {
        enableAutoRegistry(null);
    }

    protected void enableAutoRegistry(CreativeTabs tab) {
        this.registryManager = new RegistryManager(tab).create();

        this.networkEntityIdSupplier = NETWORK_ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(this.modID, s -> new NetworkEntityIdSupplier());
        this.registryManager.setNetworkEntityIdSupplier(this.networkEntityIdSupplier);
        this.registry = registryManager.getRegistry();
    }

    /**
     * Call this in the constructor to enable network functionality for this module.
     * <p>
     * This will create a new network wrapper and packet registry for this module's mod id if they don't already exist. If they do already exist, the
     * existing network wrapper and packet registry will be used.
     *
     * @return a reference to the module's packet service
     */
    protected IPacketService enableNetwork() {

        if (this.threadedNetworkWrapper == null) {
            this.threadedNetworkWrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(this.modID, ThreadedNetworkWrapper::new);
            this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(this.modID, s -> new PacketRegistry(this.threadedNetworkWrapper));
            this.packetService = new PacketService(this.threadedNetworkWrapper);
        }

        return this.packetService;
    }

    // ===== FML Lifecycle ======================================================================================================================== //

    protected void onConstruction(FMLConstructionEvent event) {}

    protected void onPreInit(FMLPreInitializationEvent event) {}

    protected void onInit(FMLInitializationEvent event) {}

    protected void onPostInit(FMLPostInitializationEvent event) {}

    protected void onLoadComplete(FMLLoadCompleteEvent event) {}

    // ===== FML Lifecycle: Client ================================================================================================================ //

    @SideOnly(Side.CLIENT)
    protected void onClientPreInit(FMLPreInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    protected void onClientInit(FMLInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    protected void onClientPostInit(FMLPostInitializationEvent event) {}

    // ===== FML Lifecycle: Server =============================================================================================================== //

    protected void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

    protected void onServerStarting(FMLServerStartingEvent event) {}

    protected void onServerStarted(FMLServerStartedEvent event) {}

    protected void onServerStopping(FMLServerStoppingEvent event) {}

    protected void onServerStopped(FMLServerStoppedEvent event) {}

    // ===== Registration ========================================================================================================================= //

    protected void onNetworkRegister() {}

    protected void onNewRegister() {}

    protected void onRegister() {}

    @SideOnly(Side.CLIENT)
    protected void onClientRegister() {}

    /**
     * What other modules this module depends on.
     * <p>
     * e.g. <code>new ResourceLocation("tfg", "soil")</code> represents a dependency on the module "soil" in the container "tfg"
     */
    @NotNull
    public Set<ResourceLocation> getDependencyUids() {
        return Collections.emptySet();
    }

    /**
     * @return A list of classes to subscribe to the Forge event bus. As the class gets subscribed, not any specific instance, event handlers must be
     * static!
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
        if (this.priority == -1 && otherModule.getPriority() == -1) {
            return this.name.compareTo(otherModule.getName());
        } else if (this.priority == -1) {
            return -1;
        } else if (otherModule.getPriority() == -1) {
            return 1;
        } else {
            return Integer.compare(this.priority, otherModule.getPriority());
        }
    }
}
