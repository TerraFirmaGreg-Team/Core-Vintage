package su.terrafirmagreg.modules.animal;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.event.EasyBreedingEventHandler;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.ANIMAL;
import static su.terrafirmagreg.modules.core.ModuleCore.TAB;

@ModuleInfo(moduleID = ANIMAL)
public final class ModuleAnimal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleAnimal.class.getSimpleName());

  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;

  public ModuleAnimal() {
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();

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
