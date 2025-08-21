package su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix;

import su.terrafirmagreg.framework.manager.plugin.spi.gregtech.BaseOrePrefix;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.stack.MaterialStack;

public class OrePrefixRock extends BaseOrePrefix {

  public OrePrefixRock(String name, MaterialStack secondaryMaterial) {
    super(name, -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }
}
