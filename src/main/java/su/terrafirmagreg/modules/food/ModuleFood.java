package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.api.base.creativetab.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.Modules;
import su.terrafirmagreg.modules.food.init.BlocksFood;
import su.terrafirmagreg.modules.food.init.ItemsFood;
import su.terrafirmagreg.modules.food.init.PacketsFood;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(moduleID = Modules.FOOD)
public final class ModuleFood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleFood.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;

  public ModuleFood() {
    TAB = BaseItemGroup.of("food", "food/ingredient/wheat_bread_sandwich");
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();
  }

  @Override
  public void onNetworkRegister() {

    PacketsFood.onRegister(packetRegistry);
  }

  @Override
  public void onRegister() {
    BlocksFood.onRegister(registryManager);
    ItemsFood.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
