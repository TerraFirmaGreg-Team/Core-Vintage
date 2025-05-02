package su.terrafirmagreg.framework.module;


import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleService;

import net.minecraftforge.common.MinecraftForge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class ModuleManager implements IModuleManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleManager.class);
  public static final Map<String, IModuleManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  private final String modId;
  private final ModuleMap map;

  private final IModuleRegistrar registrar;
  private final IModuleService service;


  private ModuleManager(String modId) {

    this.modId = modId;
    this.map = ModuleMap.of();

    this.registrar = new ModuleRegistrar(this);
    this.service = new ModuleService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }


  public static synchronized IModuleManager of(String modId) {

    return MANAGER_MAP.computeIfAbsent(modId, ModuleManager::new);
  }
}

