package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.METAL;

@ModuleInfo(
  moduleID = METAL,
  containerID = MOD_ID,
  name = "Metal",
  author = "Xikaro",
  version = "1.0.0"
)
public class ModuleMetal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(METAL);


  public static Supplier<BaseItemGroup> GROUP;
  public static IRegistryManager REGISTRY;
  public static INetworkManager NETWORK;

  public ModuleMetal() {

//    GROUP = BaseItemGroup.of(this, "anvil/red_steel");
//    REGISTRY = enableRegistry().group(GROUP);
//    NETWORK = enableNetwork();

  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
