package su.terrafirmagreg.api.module;

import org.jetbrains.annotations.Nullable;

class ModuleConstructor {

    @Nullable
    ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {
        try {
            ModuleBase module = moduleClass.newInstance();
            ModuleManager.LOGGER.info("[ {} ] Loaded module: {}", modId, moduleClass.getName());
            return module;

        } catch (Exception e) {
            ModuleManager.LOGGER.error("[ {} ] Error loading module: {} {}", modId, moduleClass.getName(), e);
        }

        return null;
    }
}
