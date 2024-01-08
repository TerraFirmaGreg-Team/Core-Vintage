//package su.tfg.api.module;
//
//import net.minecraftforge.fml.common.FMLLog;
//
//import org.jetbrains.annotations.Nullable;
//
//public class ModuleConstructor {
//
//    @Nullable
//    public ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {
//
//        try {
//            ModuleBase module = moduleClass.newInstance();
//            FMLLog.log.info("[" + modId + "] Loaded module: " + moduleClass.getName());
//            return module;
//
//        } catch (Exception e) {
//            FMLLog.log.error("[" + modId + "] Error loading module: " + moduleClass.getName(), e);
//        }
//
//        return null;
//    }
//
//}
