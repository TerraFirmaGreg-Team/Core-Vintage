package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.api.base.creativetab.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypeHandler;
import su.terrafirmagreg.modules.wood.event.EntityJoinWorldEventHandler;
import su.terrafirmagreg.modules.wood.event.KeyEventHandler;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.EntitiesWood;
import su.terrafirmagreg.modules.wood.init.ItemsWood;
import su.terrafirmagreg.modules.wood.init.KeybindingsWood;
import su.terrafirmagreg.modules.wood.init.PacketWood;
import su.terrafirmagreg.modules.wood.init.RegistryWood;
import su.terrafirmagreg.modules.wood.init.recipes.LoomRecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.Modules.WOOD;

@ModuleInfo(moduleID = WOOD)
public final class ModuleWood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleWood.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;

  public ModuleWood() {
    TAB = BaseItemGroup.of("wood", "wood/log/aspen");
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());

  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new KeyEventHandler());

  }

  @Override
  public void onNetworkRegister() {
    PacketWood.onRegister(packetRegistry);

  }

  @Override
  public void onNewRegister() {
    RegistryWood.onRegister();

  }

  @Override
  public void onRegister() {
    WoodTypeHandler.init();

    BlocksWood.onRegister(registryManager);
    ItemsWood.onRegister(registryManager);
    EntitiesWood.onRegister(registryManager);

  }

  @Override
  public void onClientRegister() {
    BlocksWood.onClientRegister(registryManager);
    EntitiesWood.onClientRegister(registryManager);
    KeybindingsWood.onClientRegister(registryManager);
  }

  @Override
  public void onRecipesRegister() {
    LoomRecipes.onRegister();

  }

  @NotNull
  @Override
  public List<Class<?>> getEventBusSubscribers() {
    return Collections.singletonList(ModuleWood.class);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
