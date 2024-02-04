package su.terrafirmagreg.modules;


import lombok.Getter;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.rock.ModuleRock;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.Tags.MOD_ID;


public enum ModuleContainer implements IStringSerializable {

    CORE(ModuleCore.class),
    ROCK(ModuleRock.class);
//    SOIL,
//    WOOD,
//    METAL,
//    FOOD,
//    ANIMAL,
//    DEVICE,
//    FLORA,
//    AGRICULTURE;

    @Getter
    private final Class<? extends ModuleBase> moduleClass;

    ModuleContainer(Class<? extends ModuleBase> moduleClass) {
        this.moduleClass = moduleClass;

    }


    public static List<Class<? extends ModuleBase>> getAllModuleClasses() {
        List<Class<? extends ModuleBase>> moduleClasses = new ArrayList<>();
        for (ModuleContainer container : ModuleContainer.values()) {
            moduleClasses.add(container.getModuleClass());
        }
        return moduleClasses;
    }

    public String getID() {
        return MOD_ID;
    }

    @Override
    public @NotNull String getName() {
        return this.name().toLowerCase();
    }
}