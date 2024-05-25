package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.modules.wood.objects.items.ItemWoodAnimalCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodBoat;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodLumber;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodPlowCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodSupplyCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodWheel;


import static su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariants.*;

public class WoodItemVariantHandler {

    public static void init() {

        BOAT = new WoodItemVariant
                .Builder("boat")
                .setFactory(ItemWoodBoat::new)
                .build();

        LUMBER = new WoodItemVariant
                .Builder("lumber")
                .setFactory(ItemWoodLumber::new)
                .build();

        WHEEL = new WoodItemVariant
                .Builder("wheel")
                .setFactory(ItemWoodWheel::new)
                .build();

        SUPPLY_CART = new WoodItemVariant
                .Builder("supply_cart")
                .setFactory(ItemWoodSupplyCart::new)
                .build();

        ANIMAL_CART = new WoodItemVariant
                .Builder("animal_cart")
                .setFactory(ItemWoodAnimalCart::new)
                .build();

        PLOW_CART = new WoodItemVariant
                .Builder("plow_cart")
                .setFactory(ItemWoodPlowCart::new)
                .build();
    }
}
