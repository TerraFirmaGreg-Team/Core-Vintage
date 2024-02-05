package su.terrafirmagreg.modules.soil.api.types.variant.block;


import su.terrafirmagreg.modules.soil.objects.blocks.*;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;

public class SoilBlockVariantHandler {

    public static void init() {

        SoilBlockVariants.DIRT = new SoilBlockVariant
                .Builder("dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.COARSE_DIRT = new SoilBlockVariant
                .Builder("coarse_dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.ROOTED_DIRT = new SoilBlockVariant
                .Builder("rooted_dirt")
                .setFactory(BlockSoilRootedDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.GRASS = new SoilBlockVariant
                .Builder("grass")
                .setFactory(BlockSoilGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.GRASS_PATH = new SoilBlockVariant
                .Builder("grass_path")
                .setFactory(BlockSoilGrassPath::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        SoilBlockVariants.DRY_GRASS = new SoilBlockVariant
                .Builder("dry_grass")
                .setFactory(BlockSoilDryGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.CLAY = new SoilBlockVariant
                .Builder("clay")
                .setFactory(BlockSoilClay::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        SoilBlockVariants.CLAY_GRASS = new SoilBlockVariant
                .Builder("clay_grass")
                .setFactory(BlockSoilClayGrass::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        SoilBlockVariants.DRY_CLAY_GRASS = new SoilBlockVariant
                .Builder("dry_clay_grass")
                .setFactory(BlockSoilClayGrass::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        SoilBlockVariants.FARMLAND = new SoilBlockVariant
                .Builder("farmland")
                .setFactory(BlockSoilFarmland::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        SoilBlockVariants.PODZOL = new SoilBlockVariant
                .Builder("podzol")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.MYCELIUM = new SoilBlockVariant
                .Builder("mycelium")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.MUD = new SoilBlockVariant
                .Builder("mud")
                .setFactory(BlockSoilMud::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SoilBlockVariants.MUD_BRICKS = new SoilBlockVariant
                .Builder("mud_bricks")
                .setFactory(BlockSoilMudBrick::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();
    }
}
