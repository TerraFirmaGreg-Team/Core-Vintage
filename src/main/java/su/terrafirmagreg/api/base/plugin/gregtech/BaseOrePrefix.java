package su.terrafirmagreg.api.base.plugin.gregtech;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaseOrePrefix extends OrePrefix {

  public BaseOrePrefix(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition) {
    super(name, materialAmount, material, materialIconType, flags, condition);
  }

  public BaseOrePrefix(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition, @Nullable Function<Material, List<String>> tooltipFunc) {
    super(name, materialAmount, material, materialIconType, flags, condition, tooltipFunc);
  }
}
