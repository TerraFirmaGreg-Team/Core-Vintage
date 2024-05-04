package su.terrafirmagreg.modules.worldgen;

import su.terrafirmagreg.api.lib.Injector;
import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.modules.worldgen.debugworld.WorldTypeDebugMod;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.WorldGen;

@Module(moduleID = WorldGen)
public final class ModuleWorldGen extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleWorldGen.class.getSimpleName());

    public ModuleWorldGen() {
        this.enableAutoRegistry();

    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        // Get the debug world field so we can change the value. We need to replace this
        // field specifically, because vanilla hardcodes for it everywhere.

        // Sets the world type to null. This is needed to get around a hardcoded limit on
        // world type name. If this is not null, the new world type can not be created with
        // the same ID.
        Injector.setFinalStaticFieldWithReflection(WorldType.class, "field_180272_g", null);
        Injector.setFinalStaticFieldWithReflection(WorldType.class, "field_180272_g",
                new WorldTypeDebugMod()); // Sets the world type to the new type.
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
