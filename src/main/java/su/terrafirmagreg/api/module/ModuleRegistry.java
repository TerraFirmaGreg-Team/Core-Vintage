package su.terrafirmagreg.api.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleRegistry {
    private List<Class<? extends ModuleBase>> moduleClassList;
    private List<ModuleBase> moduleList;
    private ModuleConstructor moduleConstructor;

    public ModuleRegistry(List<ModuleBase> moduleList, ModuleConstructor moduleConstructor) {
        this.moduleConstructor = moduleConstructor;

        this.moduleClassList = new ArrayList<>();
        this.moduleList = moduleList;
    }



    public final void registerModules(Class<? extends ModuleBase> moduleClass) {

        this.moduleClassList.add(moduleClass);
    }

    public void initializeModules(String modId) {

        for (Class<? extends ModuleBase> moduleClass : this.moduleClassList) {
            ModuleBase module = this.moduleConstructor.constructModule(modId, moduleClass);

            if (module != null) this.moduleList.add(module);
        }

        // Don't really need to keep this around.
        this.moduleClassList = null;

        // Sort the module list by module priority.
        Collections.sort(this.moduleList);
    }
}
