package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.ROCK;

@ModuleInfo(
  moduleID = ROCK,
  containerID = MOD_ID,
  name = "Rock",
  author = "Xikaro",
  version = "1.0.0"
)
public final class ModuleRock extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ROCK);

  public static Supplier<BaseItemGroup> GROUP;
  public static IRegistryManager REGISTRY;
  public static INetworkManager NETWORK;

  public ModuleRock() {

//    GROUP = BaseItemGroup.of(this, "raw/basalt");
//    REGISTRY = enableRegistry().group(GROUP);
//    NETWORK = enableNetwork();

  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    return list;
  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
