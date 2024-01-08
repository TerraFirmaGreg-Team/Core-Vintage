package su.terrafirmagreg.api.modules;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * All modules must implement this interface.
 * <p>
 * Provides methods for responding to FML lifecycle events, adding event bus subscriber classes, and processing IMC
 * messages.
 */
public interface ITFGModule {

	/**
	 * What other modules this module depends on.
	 * <p>
	 * e.g. <code>new ResourceLocation("tfg", "foo_module")</code> represents a dependency on the module
	 * "foo_module" in the container "tfg"
	 */
	@NotNull
	default Set<ResourceLocation> getDependencyUids() {
		return Collections.emptySet();
	}

	// --------------------------------------------------------------------------
	// - FML Lifecycle
	// --------------------------------------------------------------------------

	default void construction(FMLConstructionEvent event) {}

	default void preInit(FMLPreInitializationEvent event) {}

	default void init(FMLInitializationEvent event) {}

	default void postInit(FMLPostInitializationEvent event) {}

	default void loadComplete(FMLLoadCompleteEvent event) {}

	// --------------------------------------------------------------------------
	// - Server
	// --------------------------------------------------------------------------

	default void serverAboutToStart(FMLServerAboutToStartEvent event) {}

	default void serverStarting(FMLServerStartingEvent event) {}

	default void serverStarted(FMLServerStartedEvent event) {}

	default void serverStopping(FMLServerStoppingEvent event) {}

	default void serverStopped(FMLServerStoppedEvent event) {}

	// --------------------------------------------------------------------------
	// - FML Lifecycle: Client
	// --------------------------------------------------------------------------

	@SideOnly(Side.CLIENT)
	default void clientPreInit(FMLPreInitializationEvent event) {}

	@SideOnly(Side.CLIENT)
	default void clientInit(FMLInitializationEvent event) {}

	@SideOnly(Side.CLIENT)
	default void clientPostInit(FMLPostInitializationEvent event) {}

	// --------------------------------------------------------------------------
	// - Registration
	// --------------------------------------------------------------------------

	/**
	 * Register packets using TFG's packet handling API here.
	 */
	default void onNetworkRegister() {}

	default void onRegister() {}

	default void onClientRegister() {}

	/**
	 * @return A list of classes to subscribe to the Forge event bus.
	 * As the class gets subscribed, not any specific instance, event handlers must be static!
	 */
	@NotNull
	default List<Class<?>> getEventBusSubscribers() {
		return Collections.emptyList();
	}

	default boolean processIMC(FMLInterModComms.IMCMessage message) {
		return false;
	}

	/**
	 * @return A logger to use for this module.
	 */
	@NotNull
	Logger getLogger();
}
