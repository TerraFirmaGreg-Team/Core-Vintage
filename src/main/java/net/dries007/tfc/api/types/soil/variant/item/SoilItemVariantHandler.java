package net.dries007.tfc.api.types.soil.variant.item;

import net.dries007.tfc.common.objects.items.soil.ItemSoilMudBrick;
import net.dries007.tfc.common.objects.items.soil.ItemSoilMudWetBrick;
import net.dries007.tfc.common.objects.items.soil.ItemSoilPile;

import static net.dries007.tfc.api.types.soil.variant.item.SoilItemVariants.*;

public class SoilItemVariantHandler {
    public static void init() {

        PILE = new SoilItemVariant("pile", ItemSoilPile::new);
        MUD_BRICK = new SoilItemVariant("mud_brick", ItemSoilMudBrick::new);
        MUD_BRICK_WET = new SoilItemVariant("mud_brick_wet", ItemSoilMudWetBrick::new);

    }
}
