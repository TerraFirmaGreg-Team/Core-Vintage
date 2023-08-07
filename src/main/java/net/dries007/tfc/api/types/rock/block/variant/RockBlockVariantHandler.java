package net.dries007.tfc.api.types.rock.block.variant;

import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.*;

public class RockBlockVariantHandler {
    public static void init() {
        RAW = new RockBlockVariant("raw", 6.5f);
        COBBLE = new RockBlockVariant("cobble", 7f);
        SMOOTH = new RockBlockVariant("smooth", 7f);
        BRICK = new RockBlockVariant("brick", 5f);
        BRICK_CRACKED = new RockBlockVariant("brick_cracked", 5f);
        BRICK_CHISELED = new RockBlockVariant("brick_chiseled", 5f);

        GRAVEL = new RockBlockVariant("gravel", 5f);
        SAND = new RockBlockVariant("sand", 5f);
    }
}
