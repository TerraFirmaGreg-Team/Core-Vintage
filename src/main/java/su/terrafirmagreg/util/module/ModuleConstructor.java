package su.terrafirmagreg.util.module;

import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;

public class ModuleConstructor {

    @Nullable
    public ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {
        try {
            ModuleBase module = moduleClass.newInstance();
            FMLLog.log.info("[{}] Loaded module: {}", modId, moduleClass.getName());
            return module;

        } catch (Exception e) {
            FMLLog.log.error("[{}] Error loading module: {}", modId, moduleClass.getName(), e);
        }
        return null;
    }

}
