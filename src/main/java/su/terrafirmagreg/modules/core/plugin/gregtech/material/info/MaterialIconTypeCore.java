package su.terrafirmagreg.modules.core.plugin.gregtech.material.info;

import gregtech.api.unification.material.info.MaterialIconType;

public class MaterialIconTypeCore extends MaterialIconType {

  public static final MaterialIconType oreChunk = new MaterialIconType("oreChunk");

  public static final MaterialIconType toolHeadSense = new MaterialIconType("toolHeadSense");
  public static final MaterialIconType toolHeadKnife = new MaterialIconType("toolHeadKnife");
  public static final MaterialIconType toolHeadPropick = new MaterialIconType("toolHeadPropick");
  public static final MaterialIconType toolHeadChisel = new MaterialIconType("toolHeadChisel");
  public static final MaterialIconType toolHeadTuyere = new MaterialIconType("toolHeadTuyere");

  public MaterialIconTypeCore(String name) {
    super(name);
  }
}
