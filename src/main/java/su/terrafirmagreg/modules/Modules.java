package su.terrafirmagreg.modules;

import su.terrafirmagreg.api.module.IModuleContainer;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import static su.terrafirmagreg.Tags.MOD_ID;

@Getter
public enum Modules implements IStringSerializable, IModuleContainer {
  CORE(true),
  ROCK(true),
  SOIL(true),
  WOOD(true),
  METAL(true),
  ANIMAL(true),
  PLANT(true),
  DEVICE(true),
  AGRICULTURE(true),
  FOOD(true),
  WORLD(true);

  // TODO переделать с enum на статические финальные поля

  private final boolean enabled;

  Modules(boolean enabled) {
    this.enabled = enabled;
  }


  @Override
  public @NotNull String getName() {
    return this.name().toLowerCase();
  }

  @Override
  public String getID() {
    return MOD_ID;
  }
}
