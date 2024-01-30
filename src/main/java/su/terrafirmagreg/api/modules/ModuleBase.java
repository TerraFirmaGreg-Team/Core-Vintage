package su.terrafirmagreg.api.modules;

import lombok.Getter;

import mcjty.theoneprobe.setup.Registration;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.AutoRegistry;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.ModuleContainerTFG;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Getter
public abstract class ModuleBase implements Comparable<ModuleBase> {

    protected Registry registry;
    //    protected IPacketRegistry packetRegistry;
    @Getter
    private AutoRegistry autoRegistry;
    @Getter
    private final int priority;

    protected ModuleBase(int priority) {
        this.priority = priority;
    }


    protected void enableAutoRegistry(CreativeTabs tab) {
        this.registry = new Registry(tab).enableAutoRegistration();
        this.autoRegistry = registry.getAutoRegistry();
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
        var annotation = otherModule.getClass().getAnnotation(ModuleTFG.class);
        if (annotation != null && annotation.coreModule()) {
            return -1;
        } else {
            return Integer.compare(otherModule.getPriority(), this.priority);
        }
    }
}
