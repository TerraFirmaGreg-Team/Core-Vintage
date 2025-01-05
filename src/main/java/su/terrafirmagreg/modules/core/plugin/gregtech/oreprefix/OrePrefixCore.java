package su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix;

import su.terrafirmagreg.api.base.plugin.gregtech.BaseOrePrefix;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.stack.MaterialStack;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class OrePrefixCore extends BaseOrePrefix {

  public OrePrefixCore(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition) {
    super(name, materialAmount, material, materialIconType, flags, condition);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition, @Nullable Function<Material, List<String>> tooltipFunc) {
    super(name, materialAmount, material, materialIconType, flags, condition, tooltipFunc);
  }

  public OrePrefixCore(String name, MaterialStack secondaryMaterial) {
    super(name, -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }

  public OrePrefixCore(String name, @Nullable MaterialIconType materialIconType, MaterialStack secondaryMaterial) {
    super(name, -1, null, materialIconType, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition) {
    super(name, materialAmount, null, materialIconType, flags, condition);
  }


}
