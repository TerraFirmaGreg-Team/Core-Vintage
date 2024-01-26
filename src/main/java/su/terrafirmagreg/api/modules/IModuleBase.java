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
public interface IModuleBase {

    // ===== Registration ========================================================================================================================= //

    default void onRegister() {}

    default void onClientRegister() {}

    default void onNetworkRegister() {}

    // ===== FML Lifecycle ======================================================================================================================== //

    default void onConstruction(FMLConstructionEvent event) {}

    default void onPreInit(FMLPreInitializationEvent event) {}

    default void onInit(FMLInitializationEvent event) {}

    default void onPostInit(FMLPostInitializationEvent event) {}

    default void onLoadComplete(FMLLoadCompleteEvent event) {}

    // ===== FML Lifecycle: Client ================================================================================================================ //

    @SideOnly(Side.CLIENT)
    default void onClientPreInit(FMLPreInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    default void onClientInit(FMLInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    default void onClientPostInit(FMLPostInitializationEvent event) {}

    // ===== Server =============================================================================================================================== //

    default void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

    default void onServerStarting(FMLServerStartingEvent event) {}

    default void onServerStarted(FMLServerStartedEvent event) {}

    default void onServerStopping(FMLServerStoppingEvent event) {}

    default void onServerStopped(FMLServerStoppedEvent event) {}

    /**
     * What other modules this module depends on.
     * <p>
     * e.g. <code>new ResourceLocation("tfg", "soil")</code> represents a dependency on the module
     * "soil" in the container "tfg"
     */
    @NotNull
    default Set<ResourceLocation> getDependencyUids() {
        return Collections.emptySet();
    }

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
