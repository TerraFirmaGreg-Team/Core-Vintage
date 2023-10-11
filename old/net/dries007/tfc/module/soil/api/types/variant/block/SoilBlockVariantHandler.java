package net.dries007.tfc.module.soil.api.types.variant.block;

import net.dries007.tfc.module.soil.objects.blocks.*;

import static net.dries007.tfc.module.core.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.module.core.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;
import static net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariants.*;

public class SoilBlockVariantHandler {
    public static void init() {
        SoilBlockVariants.DIRT = new SoilBlockVariant("dirt", BlockSoilDirt::new, VERTICAL_AND_HORIZONTAL);
        SoilBlockVariants.GRASS = new SoilBlockVariant("grass", BlockSoilGrass::new, VERTICAL_AND_HORIZONTAL);
        SoilBlockVariants.GRASS_PATH = new SoilBlockVariant("grass_path", BlockSoilGrassPath::new, VERTICAL_ONLY_SOIL);
        SoilBlockVariants.DRY_GRASS = new SoilBlockVariant("dry_grass", BlockSoilDryGrass::new, VERTICAL_AND_HORIZONTAL);
        SoilBlockVariants.CLAY = new SoilBlockVariant("clay", BlockSoilClay::new, VERTICAL_ONLY_SOIL);
        SoilBlockVariants.CLAY_GRASS = new SoilBlockVariant("clay_grass", BlockSoilClayGrass::new, VERTICAL_ONLY_SOIL);
        SoilBlockVariants.FARMLAND = new SoilBlockVariant("farmland", BlockSoilFarmland::new, VERTICAL_ONLY_SOIL);
        SoilBlockVariants.ROOTED_DIRT = new SoilBlockVariant("rooted_dirt", BlockSoilRootedDirt::new, VERTICAL_AND_HORIZONTAL);
        SoilBlockVariants.MUD = new SoilBlockVariant("mud", BlockSoilMud::new, VERTICAL_AND_HORIZONTAL);
        SoilBlockVariants.MUD_BRICKS = new SoilBlockVariant("mud_bricks", BlockSoilMudBrick::new, VERTICAL_AND_HORIZONTAL);
    }
}
