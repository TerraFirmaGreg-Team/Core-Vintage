package net.dries007.tfc.api.types.rock.block.variant;

import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.*;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

public class RockBlockVariantHandler {
    public static void init() {
        RAW = new RockBlockVariant("raw", 6.5f, COLLAPSABLE_ROCK);
        COBBLE = new RockBlockVariant("cobble", 7f, VERTICAL_AND_HORIZONTAL_ROCK);
        SMOOTH = new RockBlockVariant("smooth", 7f, null);
        BRICK = new RockBlockVariant("brick", 5f, null);
        BRICK_CRACKED = new RockBlockVariant("brick_cracked", 5f, null);
        BRICK_CHISELED = new RockBlockVariant("brick_chiseled", 5f, null);

        GRAVEL = new RockBlockVariant("gravel", 5f, VERTICAL_AND_HORIZONTAL);
        SAND = new RockBlockVariant("sand", 5f, VERTICAL_AND_HORIZONTAL);
    }
}
