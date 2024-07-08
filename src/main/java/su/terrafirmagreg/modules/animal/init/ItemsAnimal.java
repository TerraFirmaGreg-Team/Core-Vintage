package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.objects.items.ItemAnimalMisc;

public final class ItemsAnimal {

    public static ItemAnimalMisc BLADDER;
    public static ItemAnimalMisc WOOL;
    public static ItemAnimalMisc WOOL_YARN;
    public static ItemAnimalMisc WOOL_CLOTH;
    public static ItemAnimalMisc SILK_CLOTH;

    public static void onRegister(RegistryManager registry) {

        BLADDER = registry.item(new ItemAnimalMisc("product/bladder", Size.SMALL, Weight.LIGHT));
        WOOL = registry.item(new ItemAnimalMisc("product/wool", Size.SMALL, Weight.LIGHT));
        WOOL_YARN = registry.item(new ItemAnimalMisc("product/wool_yarn", Size.VERY_SMALL, Weight.VERY_LIGHT, "string"));
        WOOL_CLOTH = registry.item(new ItemAnimalMisc("product/wool_cloth", Size.SMALL, Weight.LIGHT, "cloth_high_quality"));
        SILK_CLOTH = registry.item(new ItemAnimalMisc("product/silk_cloth", Size.SMALL, Weight.LIGHT, "cloth_high_quality"));

    }
}
