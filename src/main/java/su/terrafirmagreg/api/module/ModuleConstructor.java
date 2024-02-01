package su.terrafirmagreg.api.module;

import net.minecraftforge.fml.common.FMLLog;

import org.jetbrains.annotations.Nullable;

public class ModuleConstructor {

    @Nullable
    public ModuleBase constructModule(String container, Class<? extends ModuleBase> moduleClass) {

        try {
            ModuleBase module = moduleClass.newInstance();
            ModuleManager.LOGGER.info("[ {} ] Loaded module: {}", moduleClass.getName(), container);
            return module;

        } catch (Exception e) {
            ModuleManager.LOGGER.error("[ {} ] Error loading module: {} {}", moduleClass.getName(), container, e);
        }

        return null;
    }

}
