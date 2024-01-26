package su.terrafirmagreg.api.modules;

import lombok.Getter;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.AutoRegistry;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.ModuleContainerTFG;

import java.util.Collections;
import java.util.Set;


@Getter
public abstract class ModuleBase implements IModuleBase {

    protected Registry registry;
    //    protected IPacketRegistry packetRegistry;
    @Getter
    private AutoRegistry autoRegistry;

    protected ModuleBase() {}


    protected void enableAutoRegistry(CreativeTabs tab) {
        this.registry = new Registry(tab).enableAutoRegistration();
        this.autoRegistry = registry.getAutoRegistry();
    }


    /**
     * What other modules this module depends on.
     * <p>
     * e.g. <code>new ResourceLocation("tfg", "foo_module")</code> represents a dependency on the module
     * "foo_module" in the container "tfg"
     */
    @NotNull
    public Set<ResourceLocation> getDependencyUids() {
        return Collections.singleton(Helpers.getID(ModuleContainerTFG.MODULE_CORE));
    }
}
