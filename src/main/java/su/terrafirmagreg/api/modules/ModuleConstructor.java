package su.terrafirmagreg.api.modules;

import net.minecraftforge.fml.common.FMLLog;

import org.jetbrains.annotations.Nullable;

public class ModuleConstructor {

    @Nullable
    public ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {

        try {
            ModuleBase module = moduleClass.newInstance();
            ModuleManager.LOGGER.info("[ {} ] Loaded module: {}", moduleClass.getName(), modId);
            return module;

        } catch (Exception e) {
            FMLLog.log.error("[ {} ] Error loading module: {} {}", moduleClass.getName(), modId, e);
        }

        return null;
    }

}
