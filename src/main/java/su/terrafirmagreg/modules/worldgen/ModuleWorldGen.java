package su.terrafirmagreg.modules.worldgen;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.util.LoggingUtils;
import su.terrafirmagreg.modules.worldgen.debugworld.WorldTypeDebugMod;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@ModuleTFG(moduleID = "WorldGen", name = "TFG Module WorldGen")
public final class ModuleWorldGen extends ModuleBase {

    public static final LoggingUtils LOGGER = new LoggingUtils(ModuleWorldGen.class.getSimpleName());

    public ModuleWorldGen() {
        this.enableAutoRegistry();

    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {

        field.setAccessible(true);
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        try {

            // Get the debug world field so we can change the value. We need to replace this
            // field specifically, because vanilla hardcodes for it everywhere.
            final Field debugWorldField = ObfuscationReflectionHelper.findField(WorldType.class, "field_180272_g");

            // Sets the world type to null. This is needed to get around a hardcoded limit on
            // world type name. If this is not null, the new world type can not be created with
            // the same ID.
            setFinalStatic(debugWorldField, null);
            setFinalStatic(debugWorldField, new WorldTypeDebugMod()); // Sets the world type to the new type.
        } catch (final Exception e) {

            ModuleWorldGen.LOGGER.catching(e);
        }
    }

    @Override
    public @NotNull LoggingUtils getLogger() {
        return LOGGER;
    }
}
