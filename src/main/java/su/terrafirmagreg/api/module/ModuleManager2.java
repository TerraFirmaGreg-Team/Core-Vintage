package su.terrafirmagreg.api.module;

import com.google.common.base.Preconditions;
import lombok.Getter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static su.terrafirmagreg.Tags.MOD_NAME;

public class ModuleManager2 {

    public static final Logger LOGGER = LogManager.getLogger("TFG Module Manager");
    private static File configFolder;

    private final String modId;
    private final List<ModuleBase> loadedModules;
    private final ModuleRegistry moduleRegistry;
    private final ModuleEventRouter moduleEventRouter;

    private Map<String, IModuleContainer> containers = new LinkedHashMap<>();
    @Getter
    private IModuleContainer loadedContainer;
    private Configuration config;

    public ModuleManager2(String modId) {

        this.modId = modId;
        this.loadedModules = new ArrayList<>();
        this.moduleRegistry = new ModuleRegistry(this.loadedModules, new ModuleConstructor());
        this.moduleEventRouter = new ModuleEventRouter(this.loadedModules);

        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
    }

    public final void setup(ASMDataTable asmDataTable, File configDirectory) {

        discoverContainers(asmDataTable);
        containers = containers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        configFolder = new File(configDirectory, MOD_NAME);

        this.moduleRegistry.registerModules(asmDataTable);


    }

    public void onConstructionEvent() {

        // Initialize modules.
        this.moduleRegistry.initializeModules(this.modId);
    }

    private void discoverContainers(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(ModuleContainer.class.getCanonicalName());
        for (ASMDataTable.ASMData data : dataSet) {
            try {
                Class<?> clazz = Class.forName(data.getClassName());
                registerContainer((IModuleContainer) clazz.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                LOGGER.error("Could not initialize module container " + data.getClassName(), e);
            }
        }
    }

    public void registerContainer(IModuleContainer container) {
        Preconditions.checkNotNull(container);
        containers.put(container.getID(), container);
    }

    public void routeFMLStateEvent(FMLStateEvent event) {
        // Маршрутизируем событие FML
        this.moduleEventRouter.routeFMLStateEvent(event);
    }
}
