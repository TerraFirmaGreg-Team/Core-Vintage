package su.terrafirmagreg.api.module;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleRegistry {

	private final List<ModuleBase> moduleList;
	private final ModuleConstructor moduleConstructor;
	private List<Class<? extends ModuleBase>> moduleClassList;

	ModuleRegistry(List<ModuleBase> moduleList, ModuleConstructor moduleConstructor) {

		this.moduleConstructor = moduleConstructor;
		this.moduleClassList = new ArrayList<>();
		this.moduleList = moduleList;
	}


	public final void registerModules(Class<? extends ModuleBase> moduleClass) {

		this.moduleClassList.add(moduleClass);
	}

	void initializeModules(@NotNull String modId) {

		for (Class<? extends ModuleBase> moduleClass : this.moduleClassList) {
			ModuleBase module = this.moduleConstructor.constructModule(modId, moduleClass);

			if (module != null) this.moduleList.add(module);
		}

		// Don't really need to keep this around.
		this.moduleClassList.clear();

		// Sort the module list by module priority.
		Collections.sort(this.moduleList);
	}
}
