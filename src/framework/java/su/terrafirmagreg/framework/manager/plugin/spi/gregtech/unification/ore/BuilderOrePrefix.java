package su.terrafirmagreg.framework.manager.plugin.spi.gregtech.unification.ore;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class BuilderOrePrefix {

  private final String name;
  private final List<MaterialStack> secondaryMaterials = new ArrayList<>();

  private long materialAmount = -1;
  private Material material = null;
  private MaterialIconType materialIconType;
  private long flags;
  private Predicate<Material> condition;
  private Function<Material, List<String>> tooltipFunc;
  private byte maxStackSize = 64;
  private Function<Integer, Float> heatDamageFunction;
  private boolean isMarkerPrefix = false;
  private String alternativeOreName;

  public BuilderOrePrefix(String name) {
    this.name = name;
  }

  public BuilderOrePrefix setMaterialAmount(long materialAmount) {
    this.materialAmount = materialAmount;
    return this;
  }


  public BuilderOrePrefix setMaterial(Material material) {
    this.material = material;
    return this;
  }

  public BuilderOrePrefix setMaterialIconType(MaterialIconType materialIconType) {
    this.materialIconType = materialIconType;
    return this;
  }

  public BuilderOrePrefix setFlags(long flags) {
    this.flags = flags;
    return this;
  }

  public BuilderOrePrefix setCondition(Predicate<Material> condition) {
    this.condition = condition;
    return this;
  }

  public BuilderOrePrefix setTooltipFunc(Function<Material, List<String>> tooltipFunc) {
    this.tooltipFunc = tooltipFunc;
    return this;
  }

  public BuilderOrePrefix setMaxStackSize(byte maxStackSize) {
    this.maxStackSize = maxStackSize;
    return this;
  }

  public BuilderOrePrefix addSecondaryMaterial(MaterialStack secondaryMaterial) {
    this.secondaryMaterials.add(secondaryMaterial);
    return this;
  }

  public BuilderOrePrefix setHeatDamageFunction(Function<Integer, Float> heatDamageFunction) {
    this.heatDamageFunction = heatDamageFunction;
    return this;
  }

  public BuilderOrePrefix setMarkerPrefix(boolean isMarkerPrefix) {
    this.isMarkerPrefix = isMarkerPrefix;
    return this;
  }

  public BuilderOrePrefix setAlternativeOreName(String alternativeOreName) {
    this.alternativeOreName = alternativeOreName;
    return this;
  }

  public OrePrefix build() {
    OrePrefix prefix = OrePrefix.getPrefix(name);
    if (prefix == null) {
      prefix = new OrePrefix(name, materialAmount, material, materialIconType, flags, condition, tooltipFunc);
    }

    prefix.maxStackSize = maxStackSize;
    prefix.secondaryMaterials.addAll(secondaryMaterials);
    prefix.heatDamageFunction = heatDamageFunction;
    prefix.setMarkerPrefix(isMarkerPrefix);
    if (alternativeOreName != null) {
      prefix.setAlternativeOreName(alternativeOreName);
    }

    return prefix;
  }
}
