package su.terrafirmagreg.api.modules;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.TFGModules;

import java.util.Collections;
import java.util.Set;

public abstract class ModuleBase implements ITFGModule {

	@NotNull
	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return Collections.singleton(Helpers.getID(TFGModules.MODULE_CORE));
	}
}
