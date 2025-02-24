package su.terrafirmagreg.modules.integration.gregtech;

import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.modules.integration.ModuleIntegration;
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

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.GREGTECH;
import static su.terrafirmagreg.modules.ModulesContainer.INTEGRATION;

@ModuleInfo(
  moduleID = INTEGRATION,
  containerID = MOD_ID,
  modDependencies = {GREGTECH},
  name = "GregTech Integration",
  author = "Xikaro",
  version = "1.0.0"
)
@SuppressWarnings("unused")
public class SubModuleGregTech extends ModuleIntegration.SubModule {

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
}
