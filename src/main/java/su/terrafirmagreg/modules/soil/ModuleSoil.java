package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;
import su.terrafirmagreg.modules.soil.plugin.top.TheOneProbeSoil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.ModuleContainer.SOIL;

@Module(moduleID = SOIL)
public final class ModuleSoil extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleSoil.class.getSimpleName());

  public final CreativeTabs SOIL_TAB;

  public ModuleSoil() {
    this.SOIL_TAB = BaseCreativeTab.of("soil", "soil/grass/humus");

    this.enableAutoRegistry(SOIL_TAB);
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
