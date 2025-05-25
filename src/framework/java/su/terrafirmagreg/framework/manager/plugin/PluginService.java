package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.api.IPlugin;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class PluginService implements IPluginService {

  private final IModule module;
  private final PluginMap map;


  public PluginService(IPluginManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    fireEvent(plugin -> plugin.onPreInit(event));
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    fireEvent(plugin -> plugin.onInit(event));
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {

    fireEvent(plugin -> plugin.onPostInit(event));
  }

  @Override
  public void onLoadComplete(FMLLoadCompleteEvent event) {

    fireEvent(plugin -> plugin.onLoadComplete(event));
  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {

    fireEvent(plugin -> plugin.onClientPreInit(event));
  }

  @Override
  public void onClientInit(FMLInitializationEvent event) {

    fireEvent(plugin -> plugin.onClientInit(event));
  }

  @Override
  public void onClientPostInit(FMLPostInitializationEvent event) {

    fireEvent(plugin -> plugin.onClientPostInit(event));
  }

  @Override
  public void onServerAboutToStart(FMLServerAboutToStartEvent event) {

    fireEvent(plugin -> plugin.onServerAboutToStart(event));
  }

  @Override
  public void onServerStarting(FMLServerStartingEvent event) {

    fireEvent(plugin -> plugin.onServerStarting(event));
  }

  @Override
  public void onServerStarted(FMLServerStartedEvent event) {

    fireEvent(plugin -> plugin.onServerStarted(event));
  }

  @Override
  public void onServerStopping(FMLServerStoppingEvent event) {

    fireEvent(plugin -> plugin.onServerStopping(event));
  }

  @Override
  public void onServerStopped(FMLServerStoppedEvent event) {

    fireEvent(plugin -> plugin.onServerStopped(event));
  }

  protected void fireEvent(Consumer<IPlugin> consumer) {

    this.map.values().forEach(wrapper -> consumer.accept(wrapper.getPlugin()));
  }
}
