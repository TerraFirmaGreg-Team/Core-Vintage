package su.terrafirmagreg.modules.soil.api.types.variant.item;


import su.terrafirmagreg.modules.soil.objects.items.ItemSoilMudBrick;
import su.terrafirmagreg.modules.soil.objects.items.ItemSoilMudWetBrick;
import su.terrafirmagreg.modules.soil.objects.items.ItemSoilPile;

public class SoilItemVariantHandler {

    public static void init() {

        SoilItemVariants.PILE = new SoilItemVariant
                .Builder("pile")
                .setFactory(ItemSoilPile::new)
                .build();

        SoilItemVariants.MUD_BRICK = new SoilItemVariant
                .Builder("mud_brick")
                .setFactory(ItemSoilMudBrick::new)
                .build();

        SoilItemVariants.MUD_BRICK_WET = new SoilItemVariant
                .Builder("mud_brick_wet")
                .setFactory(ItemSoilMudWetBrick::new)
                .build();

    }
}
