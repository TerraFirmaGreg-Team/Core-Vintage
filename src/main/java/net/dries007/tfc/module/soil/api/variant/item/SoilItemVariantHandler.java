package net.dries007.tfc.module.soil.api.variant.item;

import net.dries007.tfc.module.soil.common.items.ItemSoilMudBrick;
import net.dries007.tfc.module.soil.common.items.ItemSoilMudWetBrick;
import net.dries007.tfc.module.soil.common.items.ItemSoilPile;

import static net.dries007.tfc.module.soil.api.variant.item.SoilItemVariants.*;

public class SoilItemVariantHandler {
    public static void init() {

        PILE = new SoilItemVariant("pile", ItemSoilPile::new);
        MUD_BRICK = new SoilItemVariant("mud_brick", ItemSoilMudBrick::new);
        MUD_BRICK_WET = new SoilItemVariant("mud_brick_wet", ItemSoilMudWetBrick::new);

    }
}
