package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.core.event.EventHandlerMaterial;
import su.terrafirmagreg.modules.metal.api.types.type.MetalTypeHandler;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.init.EntitiesMetal;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;
import su.terrafirmagreg.modules.metal.init.RegistriesMetal;
import su.terrafirmagreg.modules.metal.plugin.top.PluginTheOneProbe;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//@Module(moduleID = METAL)
public final class ModuleMetal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleMetal.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;


  public ModuleMetal() {
    TAB = BaseCreativeTab.of("metal", "metal/anvil/red_steel");
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();

    MinecraftForge.EVENT_BUS.register(new EventHandlerMaterial());
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    PluginTheOneProbe.init();
  }

  @Override
  public void onNewRegister() {

    RegistriesMetal.onRegister();
  }

  @Override
  public void onRegister() {
    MetalTypeHandler.init();

    BlocksMetal.onRegister(registryManager);
    ItemsMetal.onRegister(registryManager);
    EntitiesMetal.onRegister(registryManager);
  }

  @Override
  public void onClientRegister() {

    EntitiesMetal.onClientRegister(registryManager);
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();
    
    list.add(ModuleMetal.class);
    list.add(EventHandlerMaterial.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
