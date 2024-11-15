package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.base.creativetab.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;
import su.terrafirmagreg.modules.soil.plugin.top.TheOneProbeSoil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.Modules.SOIL;

@ModuleInfo(moduleID = SOIL)
public final class ModuleSoil extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleSoil.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModuleSoil() {
    TAB = BaseItemGroup.of("soil", "soil/grass/humus");
    REGISTRY = enableAutoRegistry(TAB);
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    TheOneProbeSoil.init();
  }

  @Override
  public void onRegister() {
    SoilTypeHandler.init();

    BlocksSoil.onRegister(registryManager);
    ItemsSoil.onRegister(registryManager);
  }

  @NotNull
  @Override
  public List<Class<?>> getEventBusSubscribers() {
    return Collections.singletonList(ModuleSoil.class);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
