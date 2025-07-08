package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureService;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.spi.EventState;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class FeatureService implements IFeatureService {

  private final IModule module;
  private final IFeatureRegistrar registrar;
  private final FeatureMap map;

  public FeatureService(FeatureManager manager) {

    this.module = manager.getModule();
    this.registrar = manager.getRegistrar();
    this.map = manager.getMap();
  }


  @Override
  @SubscribeEvent
  public void onPreInit(EventState.PreInitialization event) {

    fireEvent(IFeatureEntry::onPreInit);
  }

  @Override
  @SubscribeEvent
  public void onInit(EventState.Initialization event) {

    fireEvent(IFeatureEntry::onInit);
  }

  @Override
  @SubscribeEvent
  public void onPostInit(EventState.PostInitialization event) {

    fireEvent(IFeatureEntry::onPostInit);
  }

  @Override
  @SubscribeEvent
  public void onLoadComplete(EventState.LoadComplete event) {

    fireEvent(IFeatureEntry::onLoadComplete);
  }

  @Override
  @SubscribeEvent
  public void onServerAboutToStart(EventState.ServerAboutToStart event) {

    fireEvent(IFeatureEntry::onServerAboutToStart);
  }

  @Override
  @SubscribeEvent
  public void onServerStarting(EventState.ServerStarting event) {

    fireEvent(IFeatureEntry::onServerStarting);
  }

  @Override
  @SubscribeEvent
  public void onServerStarted(EventState.ServerStarted event) {

    fireEvent(IFeatureEntry::onServerStarted);
  }

  @Override
  @SubscribeEvent
  public void onServerStopping(EventState.ServerStopping event) {

    fireEvent(IFeatureEntry::onServerStopping);
  }

  @Override
  @SubscribeEvent
  public void onServerStopped(EventState.ServerStopped event) {

    fireEvent(IFeatureEntry::onServerStopped);
  }

  protected void fireEvent(Consumer<IFeatureEntry> consumer) {

    this.map.values().forEach(consumer);
  }
}
