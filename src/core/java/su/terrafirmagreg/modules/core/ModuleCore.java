package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityHandlerFood;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityHandlerMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilityHandlerSharpness;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayAmbiental;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiOpen;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiScreen;
import su.terrafirmagreg.modules.core.event.EventHandlerOnConfigChanged;
import su.terrafirmagreg.modules.core.event.EventHandlerPortalSpawn;
import su.terrafirmagreg.modules.core.event.EventHandlerPuddles;
import su.terrafirmagreg.modules.core.event.capabilities.EventHandlerCapabilitiesEntity;
import su.terrafirmagreg.modules.core.event.capabilities.EventHandlerCapabilitiesItemStack;
import su.terrafirmagreg.modules.core.event.player.EventHandlerItemTooltip;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerChangedDimension;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerLoggedIn;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerLoggedOut;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerRespawn;
import su.terrafirmagreg.modules.core.helper.OreDictHelper;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.CommandsCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.FeaturesCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@ModuleInfo(
  id = "core",
  author = "Xikaro",
  version = "1.0.0",
  description = "Core TerraFirmaGreg content."
)
public final class ModuleCore extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleCore.class.getSimpleName());


  public ModuleCore() {

    enableRegistry();
    enableNetwork();
    enableCommand();
    enableFeature();

  }

  @Override
  public void onRegistry(IRegistryRegistrar registrar) {
    registrar.group("wand");

    FluidsCore.onRegister(registrar);
    BlocksCore.onRegister(registrar);
    ItemsCore.onRegister(registrar);
    EntitiesCore.onRegister(registrar);
    EffectsCore.onRegister(registrar);
    LootTablesCore.onRegister(registrar);
  }

  @Override
  public void onCommand(ICommandRegistrar registrar) {

    CommandsCore.onRegister(registrar);
  }

  @Override
  public void onFeature(IFeatureRegistrar registrar) {

    FeaturesCore.onRegister(registrar);
  }

  @Override
  public void onNetwork(INetworkRegistrar registrar) {

    PacketsCore.onRegister(registrar);
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityHeat.register();
    CapabilityFood.register();
    CapabilityMetal.register();
    CapabilityForgeable.register();

    CapabilitySharpness.register();


  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(OverlayAmbiental.getInstance());
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    CapabilityHandlerHeat.init();
    CapabilityHandlerFood.init();
    CapabilityHandlerMetal.init();
    CapabilityHandlerSharpness.init();

  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {
    OreDictHelper.init();
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(EventHandlerGuiOpen.class);
    list.add(EventHandlerGuiScreen.class);

    list.add(EventHandlerPlayerChangedDimension.class);
    list.add(EventHandlerPlayerLoggedIn.class);
    list.add(EventHandlerPlayerLoggedOut.class);
    list.add(EventHandlerPlayerRespawn.class);

    list.add(EventHandlerCapabilitiesItemStack.class);
    list.add(EventHandlerCapabilitiesEntity.class);

    list.add(EventHandlerPortalSpawn.class);

    list.add(EventHandlerItemTooltip.class);

    list.add(EventHandlerOnConfigChanged.class);

    list.add(EventHandlerPuddles.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
