package net.dries007.tfc.module.soil.api.types.variant.block;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.module.soil.objects.blocks.*;

import static net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariants.*;

public class SoilBlockVariantHandler {
    public static void init() {
        DIRT = new SoilBlockVariant("dirt", BlockSoilDirt::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
        GRASS = new SoilBlockVariant("grass", BlockSoilGrass::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
        GRASS_PATH = new SoilBlockVariant("grass_path", BlockSoilGrassPath::new, FallingBlockManager.Specification.VERTICAL_ONLY_SOIL);
        DRY_GRASS = new SoilBlockVariant("dry_grass", BlockSoilDryGrass::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
        CLAY = new SoilBlockVariant("clay", BlockSoilClay::new, FallingBlockManager.Specification.VERTICAL_ONLY_SOIL);
        CLAY_GRASS = new SoilBlockVariant("clay_grass", BlockSoilClayGrass::new, FallingBlockManager.Specification.VERTICAL_ONLY_SOIL);
        FARMLAND = new SoilBlockVariant("farmland", BlockSoilFarmland::new, FallingBlockManager.Specification.VERTICAL_ONLY_SOIL);
        ROOTED_DIRT = new SoilBlockVariant("rooted_dirt", BlockSoilRootedDirt::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
        MUD = new SoilBlockVariant("mud", BlockSoilMud::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
        MUD_BRICKS = new SoilBlockVariant("mud_bricks", BlockSoilMudBrick::new, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);
    }
}
