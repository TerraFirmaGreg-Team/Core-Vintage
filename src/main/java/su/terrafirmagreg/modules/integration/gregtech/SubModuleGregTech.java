package su.terrafirmagreg.modules.integration.gregtech;

import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.modules.integration.ModuleIntegration;
import su.terrafirmagreg.modules.integration.gregtech.event.MaterialEventHandler;
import su.terrafirmagreg.modules.integration.gregtech.init.BlocksGregTech;
import su.terrafirmagreg.modules.integration.gregtech.init.ItemsGregTech;
import su.terrafirmagreg.temp.Recipes;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.GREGTECH;

@ModuleInfo(
  moduleID = GREGTECH,
  containerID = MOD_ID,
  name = "GregTech Integration",
  author = "Xikaro",
  version = "1.0.0"
)
@SuppressWarnings("unused")
public class SubModuleGregTech extends ModuleIntegration.SubModule {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    ItemsGregTech.init();
    BlocksGregTech.init();
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {
    Recipes.register();
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(MaterialEventHandler.class);

    return list;
  }
}
