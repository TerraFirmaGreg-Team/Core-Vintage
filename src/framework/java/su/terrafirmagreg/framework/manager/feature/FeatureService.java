package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeature;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureService;
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
public class FeatureService implements IFeatureService {

  private final IModule module;
  private final FeatureMap map;

  public FeatureService(FeatureManager manager) {
    this.module = manager.getModule();
    this.map = manager.getMap();
  }


  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    fireEvent(feature -> feature.onPreInit(event));
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    fireEvent(feature -> feature.onInit(event));
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {

    fireEvent(feature -> feature.onPostInit(event));
  }

  @Override
  public void onLoadComplete(FMLLoadCompleteEvent event) {

    fireEvent(feature -> feature.onLoadComplete(event));
  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {

    fireEvent(feature -> feature.onClientPreInit(event));
  }

  @Override
  public void onClientInit(FMLInitializationEvent event) {

    fireEvent(feature -> feature.onClientInit(event));
  }

  @Override
  public void onClientPostInit(FMLPostInitializationEvent event) {

    fireEvent(feature -> feature.onClientPostInit(event));
  }

  @Override
  public void onServerAboutToStart(FMLServerAboutToStartEvent event) {

    fireEvent(feature -> feature.onServerAboutToStart(event));
  }

  @Override
  public void onServerStarting(FMLServerStartingEvent event) {

    fireEvent(feature -> feature.onServerStarting(event));
  }

  @Override
  public void onServerStarted(FMLServerStartedEvent event) {

    fireEvent(feature -> feature.onServerStarted(event));
  }

  @Override
  public void onServerStopping(FMLServerStoppingEvent event) {

    fireEvent(feature -> feature.onServerStopping(event));
  }

  @Override
  public void onServerStopped(FMLServerStoppedEvent event) {

    fireEvent(feature -> feature.onServerStopped(event));
  }

  protected void fireEvent(Consumer<IFeature> consumer) {

    this.map.values().forEach(wrapper -> consumer.accept(wrapper.getFeature()));
  }
}
