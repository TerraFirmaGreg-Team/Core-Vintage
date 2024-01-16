package su.terrafirmagreg.modules.soil.api.types.variant.item;


import su.terrafirmagreg.modules.soil.objects.items.*;

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
