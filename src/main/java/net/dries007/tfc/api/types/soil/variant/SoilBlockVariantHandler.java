package net.dries007.tfc.api.types.soil.variant;

import net.dries007.tfc.objects.blocks.soil.*;

import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.*;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;

public class SoilBlockVariantHandler {
    public static void init() {
        DIRT = new SoilBlockVariant("dirt", BlockSoilDirt::new, VERTICAL_AND_HORIZONTAL);
        GRASS = new SoilBlockVariant("grass", BlockSoilDirtGrass::new, VERTICAL_AND_HORIZONTAL);
        DRY_GRASS = new SoilBlockVariant("dry_grass", BlockSoilGrass::new, VERTICAL_AND_HORIZONTAL);
        PATH = new SoilBlockVariant("path", BlockSoilPath::new, VERTICAL_ONLY_SOIL);
        CLAY = new SoilBlockVariant("clay", BlockSoilClay::new, VERTICAL_ONLY_SOIL);
        CLAY_GRASS = new SoilBlockVariant("clay_grass", BlockSoilClayGrass::new, VERTICAL_ONLY_SOIL);
        FARMLAND = new SoilBlockVariant("farmland", BlockSoilFarmland::new, VERTICAL_ONLY_SOIL);
    }
}
