package net.dries007.tfc.module.soil.api.variant.block;

import net.dries007.tfc.module.soil.common.blocks.*;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.*;

public class SoilBlockVariantHandler {
    public static void init() {
        DIRT = new SoilBlockVariant("dirt", BlockSoilDirt::new, VERTICAL_AND_HORIZONTAL);
        GRASS = new SoilBlockVariant("grass", BlockSoilGrass::new, VERTICAL_AND_HORIZONTAL);
        GRASS_PATH = new SoilBlockVariant("grass_path", BlockSoilGrassPath::new, VERTICAL_ONLY_SOIL);
        DRY_GRASS = new SoilBlockVariant("dry_grass", BlockSoilDryGrass::new, VERTICAL_AND_HORIZONTAL);
        CLAY = new SoilBlockVariant("clay", BlockSoilClay::new, VERTICAL_ONLY_SOIL);
        CLAY_GRASS = new SoilBlockVariant("clay_grass", BlockSoilClayGrass::new, VERTICAL_ONLY_SOIL);
        FARMLAND = new SoilBlockVariant("farmland", BlockSoilFarmland::new, VERTICAL_ONLY_SOIL);
        ROOTED_DIRT = new SoilBlockVariant("rooted_dirt", BlockSoilRootedDirt::new, VERTICAL_AND_HORIZONTAL);
        MUD = new SoilBlockVariant("mud", BlockSoilMud::new, VERTICAL_AND_HORIZONTAL);
        MUD_BRICKS = new SoilBlockVariant("mud_bricks", BlockSoilMudBrick::new, VERTICAL_AND_HORIZONTAL);
    }
}
