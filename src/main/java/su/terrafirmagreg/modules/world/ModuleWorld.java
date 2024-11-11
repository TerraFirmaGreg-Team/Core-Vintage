package su.terrafirmagreg.modules.world;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.util.InjectorUtils;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;
import su.terrafirmagreg.modules.world.debug.WorldTypeDebug;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.WORLD;

@ModuleInfo(moduleID = WORLD)
public final class ModuleWorld extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleWorld.class.getSimpleName());

  public static WorldTypeClassic WORLD_TYPE_CLASSIC;
  public static RegistryManager REGISTRY;

  public ModuleWorld() {
    REGISTRY = enableAutoRegistry();

    WORLD_TYPE_CLASSIC = new WorldTypeClassic();

  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    // Get the debug world field so we can change the value. We need to replace this
    // field specifically, because vanilla hardcodes for it everywhere.

    // Sets the world type to null. This is needed to get around a hardcoded limit on
    // world type name. If this is not null, the new world type can not be created with
    // the same ID.
    InjectorUtils.setFinalStaticFieldWithReflection(WorldType.class, "field_180272_g", null);
    // Sets the world type to the new type.
    InjectorUtils.setFinalStaticFieldWithReflection(WorldType.class, "field_180272_g", new WorldTypeDebug());
  }

  @Override
  public void onRegister() {

    BiomesWorld.onRegister(registryManager);
    //GeneratorWorld.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
