package su.terrafirmagreg.api.modules;

import lombok.Getter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.*;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.TFGModules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public abstract class ModuleBase implements ITFGModule {



	protected Registry registry;

	protected ModuleBase() {}


	protected void setRegistry(Registry registry) {
		if (this.registry != null) throw new IllegalStateException("Trying to assign module registry after it has already been assigned");
		this.registry = registry;
	}

	@NotNull
	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return Collections.singleton(Helpers.getID(TFGModules.MODULE_CORE));
	}


}
