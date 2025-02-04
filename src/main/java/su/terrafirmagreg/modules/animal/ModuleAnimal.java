package su.terrafirmagreg.modules.animal;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.animal.event.EasyBreedingEventHandler;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.plugin.top.TheOneProbeAnimal;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.ANIMAL;
import static su.terrafirmagreg.modules.ModulesContainer.CORE;

@ModuleInfo(
  moduleID = ANIMAL,
  containerID = MOD_ID,
  name = "Animal",
  author = "Xikaro",
  version = "1.0.0",
  description = "Animal module"
)
public final class ModuleAnimal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleAnimal.class.getSimpleName());

  public static IRegistryManager REGISTRY;
  public static INetworkManager NETWORK;

  public static Supplier<BaseItemGroup> GROUP;

  public ModuleAnimal() {
    GROUP = BaseItemGroup.of(this, "halter");
    REGISTRY = enableRegistry().group(GROUP);
    NETWORK = enableNetwork();

  }


  @Override
  public void onRegister(IRegistryManager registry) {
    BlocksAnimal.onRegister(registry);
    ItemsAnimal.onRegister(registry);
    EntitiesAnimal.onRegister(registry);
    SoundsAnimal.onRegister(registry);
    LootTablesAnimal.onRegister(registry);
  }

  @Override
  public void onClientRegister(IRegistryManager registry) {
    EntitiesAnimal.onClientRegister(registry);

  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    TheOneProbeAnimal.init();
  }

  @NotNull
  @Override
  public Set<ResourceLocation> getDependencyUids() {
    return Collections.singleton(ModUtils.resource(CORE));
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(EasyBreedingEventHandler.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
