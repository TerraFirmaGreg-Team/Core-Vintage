package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilClay;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilClayGrass;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDirt;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilDryGrass;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilFarmland;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrass;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrassPath;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMud;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudBrick;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudSlab;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudStairs;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMudWall;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilMycelium;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPodzol;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilRootedDirt;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;
import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

public class SoilBlockVariantHandler {

    public static void init() {
        GRASS = new SoilBlockVariant.Builder("grass")
                .setFactory(BlockSoilGrass::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        DRY_GRASS = new SoilBlockVariant.Builder("dry_grass")
                .setFactory(BlockSoilDryGrass::new)
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

        GRASS_PATH = new SoilBlockVariant.Builder("grass_path")
                .setFactory(BlockSoilGrassPath::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        DIRT = new SoilBlockVariant.Builder("dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        COARSE_DIRT = new SoilBlockVariant.Builder("coarse_dirt")
                .setFactory(BlockSoilDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        ROOTED_DIRT = new SoilBlockVariant.Builder("rooted_dirt")
                .setFactory(BlockSoilRootedDirt::new)
                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
                .build();

        FARMLAND = new SoilBlockVariant.Builder("farmland")
                .setFactory(BlockSoilFarmland::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        CLAY_GRASS = new SoilBlockVariant.Builder("clay_grass")
                .setFactory(BlockSoilClayGrass::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        DRY_CLAY_GRASS = new SoilBlockVariant.Builder("dry_clay_grass")
                .setFactory(BlockSoilClayGrass::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        CLAY = new SoilBlockVariant.Builder("clay")
                .setFactory(BlockSoilClay::new)
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

        STAIRS_MUD_BRICKS = new SoilBlockVariant.Builder("stairs/mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudStairs(MUD_BRICKS, v, t))
                .build();

        SLAB_DOUBLE_MUD_BRICKS = new SoilBlockVariant.Builder("slab_double/mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudSlab.Double(MUD_BRICKS, v, t))
                .build();

        SLAB_MUD_BRICKS = new SoilBlockVariant.Builder("slab/mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudSlab.Half(MUD_BRICKS, SLAB_DOUBLE_MUD_BRICKS, v, t))
                .build();

        WALL_MUD_BRICKS = new SoilBlockVariant.Builder("wall/mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudWall(MUD_BRICKS, v, t))
                .build();

        MUD_MOSSY_BRICKS = new SoilBlockVariant.Builder("mossy_mud_bricks")
                .setFactory(BlockSoilMudBrick::new)
                .setFallingSpecification(VERTICAL_ONLY_SOIL)
                .build();

        STAIRS_MOSSY_MUD_BRICKS = new SoilBlockVariant.Builder("stairs/mossy_mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudStairs(MUD_MOSSY_BRICKS, v, t))
                .build();

        SLAB_DOUBLE_MOSSY_MUD_BRICKS = new SoilBlockVariant.Builder("slab_double/mossy_mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudSlab.Double(MUD_MOSSY_BRICKS, v, t))
                .build();

        SLAB_MUD_MOSSY_BRICKS = new SoilBlockVariant.Builder("slab/mossy_mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudSlab.Half(MUD_MOSSY_BRICKS, SLAB_DOUBLE_MOSSY_MUD_BRICKS, v, t))
                .build();

        WALL_MUD_MOSSY_BRICKS = new SoilBlockVariant.Builder("wall/mossy_mud_bricks")
                .setFactory((v, t) -> new BlockSoilMudWall(MUD_MOSSY_BRICKS, v, t))
                .build();
    }
}
