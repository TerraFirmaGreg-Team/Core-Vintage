package su.terrafirmagreg.modules;

import su.terrafirmagreg.api.module.Container;
import su.terrafirmagreg.api.module.IModuleContainer;

import net.minecraft.util.IStringSerializable;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Constants.MOD_ID;

@Getter
@Container
public enum ModuleContainer
        implements IStringSerializable, IModuleContainer {

    CORE(true),
    ROCK(true),
    SOIL(true),
    WOOD(true),
    METAL(true),
    ANIMAL(true),
    DEVICE(true),
    WORLD(true);

    // TODO переделать с enum на статические финальные поля

    private final boolean enabled;

    ModuleContainer(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getID() {
        return MOD_ID;
    }

    @Override
    public @NotNull String getName() {
        return this.name().toLowerCase();
    }
}
