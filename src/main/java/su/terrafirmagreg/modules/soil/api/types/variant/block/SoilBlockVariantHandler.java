package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDirt;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDirtClay;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDryGrass;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDryGrassClay;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilFarmland;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrass;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrassClay;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrassPath;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMud;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudBrick;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudSlab;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudStairs;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudWall;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMycelium;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPodzol;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilRootedDirt;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilSparseGrass;


import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;
import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

public final class SoilBlockVariantHandler {

    public static void init() {

        COARSE_DIRT = new SoilBlockVariant.Builder("coarse_dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        ROOTED_DIRT = new SoilBlockVariant.Builder("rooted_dirt")
                .setFactory(BlockSoilRootedDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        DIRT = new SoilBlockVariant.Builder("dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        GRASS = new SoilBlockVariant.Builder("grass")
                .setFactory(BlockSoilGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        DRY_GRASS = new SoilBlockVariant.Builder("dry_grass")
                .setFactory(BlockSoilDryGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        SPARSE_GRASS = new SoilBlockVariant.Builder("sparse_grass")
                .setFactory(BlockSoilSparseGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        PODZOL = new SoilBlockVariant.Builder("podzol")
                .setFactory(BlockSoilPodzol::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        MYCELIUM = new SoilBlockVariant.Builder("mycelium")
                .setFactory(BlockSoilMycelium::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        DIRT_CLAY = new SoilBlockVariant.Builder("dirt_clay")
                .setFactory(BlockSoilDirtClay::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        GRASS_CLAY = new SoilBlockVariant.Builder("grass_clay")
                .setFactory(BlockSoilGrassClay::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        DRY_GRASS_CLAY = new SoilBlockVariant.Builder("dry_grass_clay")
                .setFactory(BlockSoilDryGrassClay::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        //        SPARSE_GRASS_CLAY = new SoilBlockVariant.Builder("sparse_grass_clay")
        //                .setFactory(BlockSoilSparseGrassClay::new)
        //                .setFallingSpecification(VERTICAL_ONLY_SOIL)
        //                .build();

        //        PODZOL_CLAY = new SoilBlockVariant.Builder("podzol_clay")
        //                .setFactory(BlockSoilGrassClay::new)
        //                .setFallingSpecification(VERTICAL_ONLY_SOIL)
        //                .build();
        //
        //        MYCELIUM_CLAY = new SoilBlockVariant.Builder("mycelium_clay")
        //                .setFactory(BlockSoilGrassClay::new)
        //                .setFallingSpecification(VERTICAL_ONLY_SOIL)
        //                .build();

        FARMLAND = new SoilBlockVariant.Builder("farmland")
                .setFactory(BlockSoilFarmland::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        GRASS_PATH = new SoilBlockVariant.Builder("grass_path")
                .setFactory(BlockSoilGrassPath::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        MUD = new SoilBlockVariant.Builder("mud")
                .setFactory(BlockSoilMud::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        MUD_BRICKS = new SoilBlockVariant.Builder("mud_bricks")
                .setFactory(BlockSoilMudBrick::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        MUD_BRICKS_STAIRS = new SoilBlockVariant.Builder("mud_bricks/stairs")
                .setFactory((v, t) -> new BlockSoilMudStairs(MUD_BRICKS, v, t))
                .build();

        MUD_BRICKS_SLAB_DOUBLE = new SoilBlockVariant.Builder("mud_bricks/slab_double")
                .setFactory((v, t) -> new BlockSoilMudSlab.Double(MUD_BRICKS, v, t))
                .build();

        MUD_BRICKS_SLAB = new SoilBlockVariant.Builder("mud_bricks/slab")
                .setFactory((v, t) -> new BlockSoilMudSlab.Half(MUD_BRICKS, MUD_BRICKS_SLAB_DOUBLE, v, t))
                .build();

        MUD_BRICKS_WALL = new SoilBlockVariant.Builder("mud_bricks/wall")
                .setFactory((v, t) -> new BlockSoilMudWall(MUD_BRICKS, v, t))
                .build();

        MUD_BRICKS_MOSSY = new SoilBlockVariant.Builder("mud_bricks_mossy")
                .setFactory(BlockSoilMudBrick::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        MUD_BRICKS_MOSSY_STAIRS = new SoilBlockVariant.Builder("mud_bricks_mossy/stairs")
                .setFactory((v, t) -> new BlockSoilMudStairs(MUD_BRICKS_MOSSY, v, t))
                .build();

        MUD_BRICKS_MOSSY_SLAB_DOUBLE = new SoilBlockVariant.Builder("mud_bricks_mossy/slab_double")
                .setFactory((v, t) -> new BlockSoilMudSlab.Double(MUD_BRICKS_MOSSY, v, t))
                .build();

        MUD_BRICKS_MOSSY_SLAB = new SoilBlockVariant.Builder("mud_bricks_mossy/slab")
                .setFactory((v, t) -> new BlockSoilMudSlab.Half(MUD_BRICKS_MOSSY, MUD_BRICKS_MOSSY_SLAB_DOUBLE, v, t))
                .build();

        MUD_BRICKS_MOSSY_WALL = new SoilBlockVariant.Builder("mud_bricks_mossy/wall")
                .setFactory((v, t) -> new BlockSoilMudWall(MUD_BRICKS_MOSSY, v, t))
                .build();
    }
}
