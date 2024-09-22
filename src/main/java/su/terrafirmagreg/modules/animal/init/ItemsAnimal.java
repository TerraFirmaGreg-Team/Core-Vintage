package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.api.base.item.spi.IItemSettings.Settings;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalMisc;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public final class ItemsAnimal {

  public static ItemAnimalMisc BLADDER;
  public static ItemAnimalMisc WOOL;
  public static ItemAnimalMisc WOOL_YARN;
  public static ItemAnimalMisc WOOL_CLOTH;
  public static ItemAnimalMisc SILK_CLOTH;

  public static void onRegister(RegistryManager registry) {

    BLADDER = registry.item(new ItemAnimalMisc("product/bladder", Settings.of().size(Size.SMALL).weight(Weight.LIGHT)));
    WOOL = registry.item(new ItemAnimalMisc("product/wool", Settings.of().size(Size.SMALL).weight(Weight.LIGHT)));
    WOOL_YARN = registry.item(new ItemAnimalMisc("product/wool_yarn", Settings.of().size(Size.VERY_SMALL).weight(Weight.VERY_LIGHT).oreDict("string")));
    WOOL_CLOTH = registry.item(new ItemAnimalMisc("product/wool_cloth", Settings.of().size(Size.SMALL).weight(Weight.LIGHT).oreDict("cloth_high_quality")));
    SILK_CLOTH = registry.item(new ItemAnimalMisc("product/silk_cloth", Settings.of().size(Size.SMALL).weight(Weight.LIGHT).oreDict("cloth_high_quality")));

  }
}
