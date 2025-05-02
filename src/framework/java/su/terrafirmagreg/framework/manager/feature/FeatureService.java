package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeature;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

  protected void fireEvent(Consumer<IFeature> consumer) {

    this.map.values().forEach(wrapper -> consumer.accept(wrapper.getFeature()));
  }
}
