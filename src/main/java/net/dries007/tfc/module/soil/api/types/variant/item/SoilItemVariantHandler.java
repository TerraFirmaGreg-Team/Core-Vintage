package net.dries007.tfc.module.soil.api.types.variant.item;

import net.dries007.tfc.module.soil.objects.items.ItemSoilMudBrick;
import net.dries007.tfc.module.soil.objects.items.ItemSoilMudWetBrick;
import net.dries007.tfc.module.soil.objects.items.ItemSoilPile;

import static net.dries007.tfc.module.soil.api.types.variant.item.SoilItemVariants.*;

public class SoilItemVariantHandler {
    public static void init() {

        PILE = new SoilItemVariant("pile", ItemSoilPile::new);
        MUD_BRICK = new SoilItemVariant("mud_brick", ItemSoilMudBrick::new);
        MUD_BRICK_WET = new SoilItemVariant("mud_brick_wet", ItemSoilMudWetBrick::new);

    }
}
