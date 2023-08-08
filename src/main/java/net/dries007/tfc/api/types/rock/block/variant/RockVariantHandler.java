package net.dries007.tfc.api.types.rock.block.variant;

import static net.dries007.tfc.api.types.rock.block.variant.RockVariants.*;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

public class RockVariantHandler {
    public static void init() {
        RAW = new RockVariant("raw", 6.5f, COLLAPSABLE_ROCK);
        COBBLE = new RockVariant("cobble", 7f, VERTICAL_AND_HORIZONTAL_ROCK);
        SMOOTH = new RockVariant("smooth", 7f, null);
        BRICK = new RockVariant("brick", 5f, null);
        BRICK_CRACKED = new RockVariant("brick_cracked", 5f, null);
        BRICK_CHISELED = new RockVariant("brick_chiseled", 5f, null);

        GRAVEL = new RockVariant("gravel", 5f, VERTICAL_AND_HORIZONTAL);
        SAND = new RockVariant("sand", 5f, VERTICAL_AND_HORIZONTAL);
    }
}
