package su.terrafirmagreg.modules.integration.gregtech.unification.material.info;

import gregtech.api.unification.material.info.MaterialIconType;

public class MaterialIconTypeCore extends MaterialIconType {

  public static final MaterialIconTypeCore oreChunk = new MaterialIconTypeCore("oreChunk");

  public static final MaterialIconTypeCore toolHeadSense = new MaterialIconTypeCore("toolHeadSense");
  public static final MaterialIconTypeCore toolHeadKnife = new MaterialIconTypeCore("toolHeadKnife");
  public static final MaterialIconTypeCore toolHeadPropick = new MaterialIconTypeCore("toolHeadPropick");
  public static final MaterialIconTypeCore toolHeadChisel = new MaterialIconTypeCore("toolHeadChisel");
  public static final MaterialIconTypeCore toolHeadTuyere = new MaterialIconTypeCore("toolHeadTuyere");
  public static final MaterialIconTypeCore toolHeadMace = new MaterialIconTypeCore("toolHeadMace");
  public static final MaterialIconTypeCore toolHeadJavelin = new MaterialIconTypeCore("toolHeadJavelin");

  public MaterialIconTypeCore(String name) {
    super(name);
  }
}
