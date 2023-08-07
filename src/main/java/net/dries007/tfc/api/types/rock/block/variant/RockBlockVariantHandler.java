package net.dries007.tfc.api.types.rock.block.variant;

public class RockBlockVariantHandler {
    public static void init() {
        RockBlockVariants.Raw = new RockBlockVariant("raw", 6.5f);
        RockBlockVariants.Cobble = new RockBlockVariant("cobble", 7f);
        RockBlockVariants.Smooth = new RockBlockVariant("smooth", 7f);
        RockBlockVariants.Brick = new RockBlockVariant("brick", 5f);
        RockBlockVariants.BrickCracked = new RockBlockVariant("brick_cracked", 5f);
        RockBlockVariants.BrickChiseled = new RockBlockVariant("brick_chiseled", 5f);

        RockBlockVariants.Gravel = new RockBlockVariant("gravel", 5f);
        RockBlockVariants.Sand = new RockBlockVariant("sand", 5f);
    }
}
