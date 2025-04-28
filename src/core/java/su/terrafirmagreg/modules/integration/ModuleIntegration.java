package su.terrafirmagreg.modules.integration;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.modules.integration.gregtech.event.MaterialEventHandler;
import su.terrafirmagreg.modules.integration.gregtech.init.BlocksGregTech;
import su.terrafirmagreg.modules.integration.gregtech.init.ItemsGregTech;
import su.terrafirmagreg.modules.integration.gregtech.init.RecipesGregTech;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.GREGTECH;

@ModuleInfo(
  id = "integration",
  author = "Xikaro",
  version = "1.0.0",
  modDependencies = {GREGTECH}
)
public class ModuleIntegration extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleIntegration.class.getSimpleName());

  public ModuleIntegration() {}

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    ItemsGregTech.preInit();
    BlocksGregTech.preInit();
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {
    RecipesGregTech.postInit();
  }


  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(MaterialEventHandler.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
  
}
