package su.terrafirmagreg;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.modules.*;

import java.io.File;
import java.util.*;

/**
 * Класс, отвечающий за управление модулями.
 */
public class ModuleManager implements IModuleManager {

    private static final ModuleManager INSTANCE = new ModuleManager();

    /**
     * Коллекция контейнеров модулей.
     */
    private final Map<String, IModuleContainer> containers = new HashMap<>();

    /**
     * Коллекция отсортированных модулей.
     */
    private final Map<ResourceLocation, IModule> sortedModules = new LinkedHashMap<>();

    /**
     * Коллекция загруженных модулей.
     */
    private final Set<IModule> loadedModules = new LinkedHashSet<>();

    /**
     * Логгер для модуля.
     */
    private final Logger logger = LogManager.getLogger("TFG Module Loader");

    /**
     * Текущий контейнер модуля.
     */
    private IModuleContainer currentContainer;

    /**
     * Текущая стадия загрузки модулей.
     */
    private ModuleStage currentStage = ModuleStage.C_SETUP;

    private ModuleManager() {}

    /**
     * Возвращает экземпляр класса ModuleManager.
     *
     * @return экземпляр класса ModuleManager
     */
    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает основной модуль из контейнера модулей.
     *
     * @param modules модули
     * @return основной модуль
     * @throws IllegalStateException если не удалось найти основной модуль
     */
    private static IModule getCoreModule(List<IModule> modules) {
        for (IModule module : modules) {
            ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
            if (annotation.coreModule()) {
                return module;
            }
        }
        return null;
    }

    /**
     * Возвращает идентификатор контейнера модуля.
     *
     * @param module модуль
     * @return идентификатор контейнера модуля
     */
    private static String getContainerID(IModule module) {
        ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
        return annotation.containerID();
    }

    /**
     * Проверяет, включен ли модуль с заданным идентификатором.
     *
     * @param id идентификатор модуля
     * @return true, если модуль включен, иначе false
     */
    @Override
    public boolean isModuleEnabled(ResourceLocation id) {
        return sortedModules.containsKey(id);
    }

    /**
     * Проверяет, включен ли модуль.
     *
     * @param module модуль
     * @return true, если модуль включен, иначе false
     */
    public boolean isModuleEnabled(IModule module) {
        return true;
        //ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
        //String comment = getComment(module);
        //Property prop = getConfiguration().get(MODULE_CFG_CATEGORY_NAME, annotation.containerID() + ":" + annotation.moduleID(), true, comment);
        //return prop.getBoolean();
    }

    /**
     * Возвращает загруженный контейнер модуля.
     *
     * @return загруженный контейнер модуля
     */
    @Override
    public IModuleContainer getLoadedContainer() {
        return currentContainer;
    }

    /**
     * Возвращает текущую стадию загрузки модулей.
     *
     * @return текущая стадия загрузки модулей
     */
    @Override
    public ModuleStage getStage() {
        return currentStage;
    }

    /**
     * Проверяет, пройдена ли указанная стадия загрузки модулей.
     *
     * @param stage стадия загрузки модулей
     * @return true, если указанная стадия пройдена, иначе false
     */
    @Override
    public boolean hasPassedStage(ModuleStage stage) {
        return currentStage.ordinal() > stage.ordinal();
    }

    /**
     * Регистрирует контейнер модуля.
     *
     * @param container контейнер модуля
     */
    @Override
    public void registerContainer(IModuleContainer container) {
        if (currentStage != ModuleStage.C_SETUP) {
            logger.error("Failed to register module container {}, as module loading has already begun", container);
            return;
        }
        Preconditions.checkNotNull(container);
        containers.put(container.getID(), container);
    }

    /**
     * Загружает модули.
     *
     * @param asmDataTable    таблица данных ASM
     * @param configDirectory директория конфигурации
     */
    public void setup(ASMDataTable asmDataTable, File configDirectory) {
        currentStage = ModuleStage.M_SETUP;

        //configFolder = new File(configDirectory, GTValues.MODID);
        Map<String, List<IModule>> modules = getModules(asmDataTable);
        configureModules(modules);

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Registering event handlers");
            for (Class<?> clazz : module.getEventBusSubscribers()) {
                MinecraftForge.EVENT_BUS.register(clazz);
            }
        }
    }

    /**
     * Обрабатывает сообщения межмодового взаимодействия.
     *
     * @param messages сообщения межмодового взаимодействия
     */
    public void processIMC(ImmutableList<FMLInterModComms.IMCMessage> messages) {
        for (FMLInterModComms.IMCMessage message : messages) {
            for (IModule module : loadedModules) {
                if (module.processIMC(message)) {
                    break;
                }
            }
        }
    }

    /**
     * Выполняет действия при конструировании модулей.
     *
     * @param event событие конструирования FML
     */
    public void onConstruction(FMLConstructionEvent event) {
        currentStage = ModuleStage.CONSTRUCTION;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Construction start");
            module.construction(event);
            module.getLogger().debug("Construction complete");
        }
    }

    /**
     * Выполняет действия перед инициализацией.
     *
     * @param event событие предварительной инициализации FML
     */
    public void onPreInit(FMLPreInitializationEvent event) {
        currentStage = ModuleStage.PRE_INIT;

        // Separate loops for strict ordering
        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Registering packets");
            module.registerPackets();
        }
        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Pre-init start");
            module.preInit(event);
            module.getLogger().debug("Pre-init complete");
        }
    }

    /**
     * Выполняет действия при инициализации модулей.
     *
     * @param event событие инициализации FML
     */
    public void onInit(FMLInitializationEvent event) {
        currentStage = ModuleStage.INIT;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Init start");
            module.init(event);
            module.getLogger().debug("Init complete");
        }
    }

    /**
     * Выполняет действия после инициализации модулей.
     *
     * @param event событие после инициализации FML
     */
    public void onPostInit(FMLPostInitializationEvent event) {
        currentStage = ModuleStage.POST_INIT;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Post-init start");
            module.postInit(event);
            module.getLogger().debug("Post-init complete");
        }
    }

    /**
     * Выполняет действия после полной загрузки модулей.
     *
     * @param event событие полной загрузки FML
     */
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        currentStage = ModuleStage.FINISHED;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Load-complete start");
            module.loadComplete(event);
            module.getLogger().debug("Load-complete complete");
        }
    }

    /**
     * Выполняет действия перед запуском сервера.
     *
     * @param event событие перед запуском сервера FML
     */
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        currentStage = ModuleStage.SERVER_ABOUT_TO_START;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Server-about-to-start start");
            module.serverAboutToStart(event);
            module.getLogger().debug("Server-about-to-start complete");
        }
    }

    /**
     * Выполняет действия при запуске сервера.
     *
     * @param event событие запуска сервера FML
     */
    public void onServerStarting(FMLServerStartingEvent event) {
        currentStage = ModuleStage.SERVER_STARTING;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Server-starting start");
            module.serverStarting(event);
            module.getLogger().debug("Server-starting complete");
        }
    }

    /**
     * Выполняет действия после запуска сервера.
     *
     * @param event событие после запуска сервера FML
     */
    public void onServerStarted(FMLServerStartedEvent event) {
        currentStage = ModuleStage.SERVER_STARTED;

        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Server-started start");
            module.serverStarted(event);
            module.getLogger().debug("Server-started complete");
        }
    }

    /**
     * Выполняет действия перед остановкой сервера.
     *
     * @param event событие перед остановкой сервера FML
     */
    public void onServerStopping(FMLServerStoppingEvent event) {
        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.serverStopping(event);
        }
    }

    /**
     * Выполняет действия после остановки сервера.
     *
     * @param event событие после остановки сервера FML
     */
    public void onServerStopped(FMLServerStoppedEvent event) {
        for (IModule module : loadedModules) {
            currentContainer = containers.get(getContainerID(module));
            module.serverStopped(event);
        }
    }

    /**
     * Конфигурирует модули на основе переданной карты модулей.
     *
     * @param modules карта модулей, где ключ - идентификатор контейнера, значение - список модулей
     */
    private void configureModules(Map<String, List<IModule>> modules) {
        //Locale locale = Locale.getDefault();
        //Locale.setDefault(Locale.ENGLISH);
        Set<ResourceLocation> toLoad = new HashSet<>();
        Set<IModule> modulesToLoad = new HashSet<>();
        //Configuration config = getConfiguration();

        for (IModuleContainer container : containers.values()) {
            String containerID = container.getID();
            List<IModule> containerModules = modules.get(containerID);
            //config.load();
            //config.addCustomCategoryComment(MODULE_CFG_CATEGORY_NAME,
            //        "ModuleTFG configuration file. Can individually enable/disable modules from GregTech and its addons");

            IModule coreModule = getCoreModule(containerModules);
            if (coreModule == null) {
                throw new IllegalStateException("Could not find core module for module container " + containerID);
            } else {
                containerModules.remove(coreModule);
                containerModules.add(0, coreModule);
            }

            // Удаляет отключенные модули и собирает потенциальные модули для загрузки
            Iterator<IModule> iterator = containerModules.iterator();
            while (iterator.hasNext()) {
                IModule module = iterator.next();
                if (!isModuleEnabled(module)) {
                    iterator.remove();
                    logger.debug("Module disabled: {}", module);
                    continue;
                }
                ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                toLoad.add(new ResourceLocation(containerID, annotation.moduleID()));
                modulesToLoad.add(module);
            }
        }

        // Проверяет зависимости модулей
        Iterator<IModule> iterator;
        boolean changed;
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                IModule module = iterator.next();

                // Проверяет зависимости модуля
                Set<ResourceLocation> dependencies = module.getDependencyUids();
                if (!toLoad.containsAll(dependencies)) {
                    iterator.remove();
                    changed = true;
                    ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                    String moduleID = annotation.moduleID();
                    toLoad.remove(new ResourceLocation(moduleID));
                    logger.info("Module {} is missing at least one of module dependencies: {}, skipping loading...", moduleID, dependencies);
                }
            }
        } while (changed);

        // Сортирует модули
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                IModule module = iterator.next();
                if (sortedModules.keySet().containsAll(module.getDependencyUids())) {
                    iterator.remove();
                    ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                    sortedModules.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
                    changed = true;
                    break;
                }
            }
        } while (changed);

        loadedModules.addAll(sortedModules.values());

        //if (config.hasChanged()) {
        //    config.save();
        //}
        //Locale.setDefault(locale);
    }

    /**
     * Возвращает карту модулей на основе переданной таблицы ASM.
     *
     * @param table таблица ASM, содержащая информацию о модулях
     * @return карта модулей, где ключ - идентификатор контейнера, значение - список модулей
     */
    private Map<String, List<IModule>> getModules(ASMDataTable table) {
        List<IModule> instances = getInstances(table);
        Map<String, List<IModule>> modules = new LinkedHashMap<>();
        for (IModule module : instances) {
            ModuleTFG info = module.getClass().getAnnotation(ModuleTFG.class);
            modules.computeIfAbsent(info.containerID(), k -> new ArrayList<>()).add(module);
        }
        return modules;
    }

    @SuppressWarnings("unchecked")
    private List<IModule> getInstances(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(ModuleTFG.class.getCanonicalName());
        List<IModule> instances = new ArrayList<>();
        for (ASMDataTable.ASMData data : dataSet) {
            String moduleID = (String) data.getAnnotationInfo().get("moduleID");
            List<String> modDependencies = (ArrayList<String>) data.getAnnotationInfo().get("modDependencies");
            if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
                try {
                    Class<?> clazz = Class.forName(data.getClassName());
                    instances.add((IModule) clazz.newInstance());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    logger.error("Could not initialize module " + moduleID, e);
                }
            } else {
                logger.info("ModuleTFG {} is missing at least one of mod dependencies: {}, skipping loading...", moduleID, modDependencies);
            }
        }
        return instances;
    }
}
