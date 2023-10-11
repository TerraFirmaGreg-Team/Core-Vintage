package su.terrafirmagreg.util.module;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import su.terrafirmagreg.util.integration.IntegrationPluginHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ModuleManager управляет модулями и их обработчиками интеграции для конкретного мода.
 */
public class ModuleManager {

    private final String modId;
    private final List<ModuleBase> moduleList;
    private final ModuleRegistry moduleRegistry;
    private final ModuleEventRouter moduleEventRouter;
    private final IntegrationPluginHandlerRegistry integrationPluginHandlerRegistry;

    /**
     * Конструктор для создания нового экземпляра ModuleManager с указанным идентификатором мода.
     *
     * @param modId идентификатор мода
     */
    public ModuleManager(String modId) {

        this.modId = modId;
        this.moduleList = new ArrayList<>();
        this.moduleRegistry = new ModuleRegistry(this.moduleList, new ModuleConstructor());
        this.moduleEventRouter = new ModuleEventRouter(this.moduleList);
        this.integrationPluginHandlerRegistry = new IntegrationPluginHandlerRegistry(modId, this.moduleRegistry);

        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

//        this.registerIntegrationHandler("jei", "su.terrafirmagreg.util.integration.jei.IntegrationPluginHandler");
//
//        this.registerIntegrationHandler("crafttweaker", "su.terrafirmagreg.util.integration.crafttweaker.IntegrationPluginHandler");
//
//        this.registerIntegrationHandler("gamestages", "su.terrafirmagreg.util.integration.SimplePluginHandler");
//
//        this.registerIntegrationHandler("dropt", "su.terrafirmagreg.util.integration.SimplePluginHandler");
    }

    /**
     * Регистрирует указанные классы модулей.
     *
     * @param moduleClassArray массив классов модулей для регистрации
     */
    @SafeVarargs
    public final void registerModules(Class<? extends ModuleBase>... moduleClassArray) {

        this.moduleRegistry.registerModules(moduleClassArray);
    }

    public void registerIntegrationHandler(String modId, String handler) {

        this.integrationPluginHandlerRegistry.registerIntegrationHandler(modId, handler);
    }

    /**
     * Метод для обработки события конструирования.
     * <p>
     * Инициализирует обработчики интеграции.
     * Инициализирует модули.
     * Регистрирует плагины интеграции с использованием загруженных обработчиков.
     */
    public void onConstructionEvent() {

        // Инициализируйте обработчики интеграции.
        this.integrationPluginHandlerRegistry.initializeIntegrationHandlers();

        // Инициализируйте модули.
        this.moduleRegistry.initializeModules(this.modId);

        // Зарегистрируйте плагины интеграции, используя загруженные обработчики.
        for (ModuleBase module : this.moduleList) {
            this.integrationPluginHandlerRegistry.registerIntegrationPlugins(module.getIntegrationPluginMap());
        }
    }

    /**
     * Маршрутизирует указанное событие FML State Event в модульный роутер событий.
     *
     * @param event событие FML State Event для маршрутизации
     */
    public void routeFMLStateEvent(FMLStateEvent event) {

        this.moduleEventRouter.routeFMLStateEvent(event);
    }
}
