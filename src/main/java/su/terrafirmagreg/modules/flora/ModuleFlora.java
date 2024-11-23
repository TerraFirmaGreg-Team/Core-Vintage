package su.terrafirmagreg.modules.flora;

import su.terrafirmagreg.api.base.creativetab.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.flora.api.types.type.FloraTypeHandler;
import su.terrafirmagreg.modules.flora.init.BlocksFlora;
import su.terrafirmagreg.modules.flora.init.ItemsFlora;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.PLANT;

@ModuleInfo(moduleID = PLANT)
public final class ModuleFlora extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleFlora.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModuleFlora() {
    TAB = BaseItemGroup.of("flora", "plant/crop/seed/rice");
    REGISTRY = enableAutoRegistry(TAB);
  }

  @Override
  public void onRegister() {
    FloraTypeHandler.init();

    BlocksFlora.onRegister(registryManager);
    ItemsFlora.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
