//package su.terrafirmagreg.api.module;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.ImmutableList;
//
//import lombok.Getter;
//
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.fml.common.Loader;
//import net.minecraftforge.fml.common.discovery.ASMDataTable;
//import net.minecraftforge.fml.common.event.*;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import su.terrafirmagreg.api.util.Helpers;
//
//import java.io.File;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static su.terrafirmagreg.Tags.MOD_NAME;
//
//public class ModuleManager {
//
//    private static final String MODULE_CFG_FILE_NAME = "modules.cfg";
//    private static final String MODULE_CFG_CATEGORY_NAME = "modules";
//    private static File configFolder;
//    private final Map<ResourceLocation, ModuleBase> sortedModules;
//    private final Set<ModuleBase> loadedModules;
//    public static final Logger LOGGER = LogManager.getLogger("TFG Module Manager");
//    private final ModuleEventRouter moduleEventRouter;
//    private Map<String, IModuleContainer> containers = new LinkedHashMap<>();
//    @Getter
//    private IModuleContainer loadedContainer;
//    private Configuration config;
//
//
//    public ModuleManager() {
//        this.sortedModules = new LinkedHashMap<>();
//        this.loadedModules = new LinkedHashSet<>();
//        this.moduleEventRouter = new ModuleEventRouter(loadedModules);
//        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
//    }
//
//    private static ModuleBase getCoreModule(List<ModuleBase> modules) {
//        for (ModuleBase module : modules) {
//            var annotation = module.getClass().getAnnotation(Module.class);
//            if (annotation.coreModule()) return module;
//        }
//        return null;
//    }
//
//    private static String getContainerID(ModuleBase module) {
//        var annotation = module.getClass().getAnnotation(Module.class);
//        return annotation.containerID();
//    }
//

//
//
//
//
//
//    private String getComment(ModuleBase module) {
//        Module annotation = module.getClass().getAnnotation(Module.class);
//
//        String comment = annotation.description();
//        Set<ResourceLocation> dependencies = module.getDependencyUids();
//        if (!dependencies.isEmpty()) {
//            Iterator<ResourceLocation> iterator = dependencies.iterator();
//            StringBuilder builder = new StringBuilder(comment);
//            builder.append("\n");
//            builder.append("Module Dependencies: [ ");
//            builder.append(iterator.next());
//            while (iterator.hasNext()) {
//                builder.append(", ").append(iterator.next());
//            }
//            builder.append(" ]");
//            comment = builder.toString();
//        }
//        String[] modDependencies = annotation.modDependencies();
//        if (modDependencies != null && modDependencies.length > 0) {
//            Iterator<String> iterator = Arrays.stream(modDependencies).iterator();
//            StringBuilder builder = new StringBuilder(comment);
//            builder.append("\n");
//            builder.append("Mod Dependencies: [ ");
//            builder.append(iterator.next());
//            while (iterator.hasNext()) {
//                builder.append(", ").append(iterator.next());
//            }
//            builder.append(" ]");
//            comment = builder.toString();
//        }
//        return comment;
//    }
//
//    private Configuration getConfiguration() {
//        if (config == null) config = new Configuration(new File(configFolder, MODULE_CFG_FILE_NAME));
//        return config;
//    }
//
//    public void routeFMLStateEvent(FMLStateEvent event) {
//        System.out.println("Routing event: " + event);
//        this.moduleEventRouter.routeFMLStateEvent(event);
//    }
//}
