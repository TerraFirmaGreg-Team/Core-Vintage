package su.terrafirmagreg.mixin;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class LateMixin implements ILateMixinLoader {

  @Override
  public List<String> getMixinConfigs() {
    final var configs = new ArrayList<String>();

    configs.add("mixins.tfg.gregtech.json");
    configs.add("mixins.tfg.extraplanets.json");

    return configs;
  }
}
