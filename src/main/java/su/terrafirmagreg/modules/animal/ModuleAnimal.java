package su.terrafirmagreg.modules.animal;

import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.animal.event.EasyBreedingEventHandler;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.core.ModuleCore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.ANIMAL;

@Module(moduleID = ANIMAL)
public final class ModuleAnimal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleAnimal.class.getSimpleName());

  public ModuleAnimal() {
    this.enableAutoRegistry(ModuleCore.CORE_TAB);
    this.enableNetwork();

  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new EasyBreedingEventHandler());
  }

  @Override
  public void onRegister() {
    BlocksAnimal.onRegister(registryManager);
    ItemsAnimal.onRegister(registryManager);
    EntitiesAnimal.onRegister(registryManager);
    LootTablesAnimal.onRegister(registryManager);
  }

  @Override
  public void onClientRegister() {
    EntitiesAnimal.onClientRegister(registryManager);

  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
