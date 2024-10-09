package su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

public class OrePrefixRock extends OrePrefix {

  public OrePrefixRock(String name, MaterialStack secondaryMaterial) {
    super(name, -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }
}
