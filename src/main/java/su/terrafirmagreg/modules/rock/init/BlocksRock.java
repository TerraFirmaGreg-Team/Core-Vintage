package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariantHandler;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterBricks;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterRaw;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterSmooth;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockRockDecorative;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class BlocksRock {

    public static final Map<Pair<RockBlockVariant, RockType>, Block> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<RockBlockVariant, EnumDyeColor>, BlockRockDecorative> ALABASTER_COLOR_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static RockBlockVariant COBBLE;
    public static RockBlockVariant COBBLE_STAIRS;
    public static RockBlockVariant COBBLE_SLAB_DOUBLE;
    public static RockBlockVariant COBBLE_SLAB;
    public static RockBlockVariant COBBLE_WALL;

    public static RockBlockVariant MOSSY_COBBLE;
    public static RockBlockVariant MOSSY_COBBLE_STAIRS;
    public static RockBlockVariant MOSSY_COBBLE_SLAB_DOUBLE;
    public static RockBlockVariant MOSSY_COBBLE_SLAB;
    public static RockBlockVariant MOSSY_COBBLE_WALL;

    public static RockBlockVariant RAW;
    public static RockBlockVariant RAW_STAIRS;
    public static RockBlockVariant RAW_SLAB_DOUBLE;
    public static RockBlockVariant RAW_SLAB;
    public static RockBlockVariant RAW_WALL;

    public static RockBlockVariant MOSSY_RAW;
    public static RockBlockVariant MOSSY_RAW_STAIRS;
    public static RockBlockVariant MOSSY_RAW_SLAB_DOUBLE;
    public static RockBlockVariant MOSSY_RAW_SLAB;
    public static RockBlockVariant MOSSY_RAW_WALL;

    public static RockBlockVariant BRICKS;
    public static RockBlockVariant BRICKS_STAIRS;
    public static RockBlockVariant BRICKS_SLAB_DOUBLE;
    public static RockBlockVariant BRICKS_SLAB;
    public static RockBlockVariant BRICKS_WALL;

    public static RockBlockVariant BRICKS_MOSSY;
    public static RockBlockVariant BRICKS_MOSSY_STAIRS;
    public static RockBlockVariant BRICKS_MOSSY_SLAB_DOUBLE;
    public static RockBlockVariant BRICKS_MOSSY_SLAB;
    public static RockBlockVariant BRICKS_MOSSY_WALL;

    public static RockBlockVariant SMOOTH;
    public static RockBlockVariant SMOOTH_STAIRS;
    public static RockBlockVariant SMOOTH_SLAB_DOUBLE;
    public static RockBlockVariant SMOOTH_SLAB;
    public static RockBlockVariant SMOOTH_WALL;

    public static RockBlockVariant BRICKS_CRACKED;
    public static RockBlockVariant CHISELED;

    public static RockBlockVariant GRAVEL;
    public static RockBlockVariant SAND;
    public static RockBlockVariant SURFACE;
    public static RockBlockVariant SPELEOTHEM;
    public static RockBlockVariant BUTTON;
    public static RockBlockVariant PRESSURE_PLATE;
    public static RockBlockVariant ANVIL;
    public static RockBlockVariant MAGMA;
    public static RockBlockVariant STAND_GEM;

    public static BlockAlabasterBricks ALABASTER_BRICKS;
    public static BlockAlabasterSmooth ALABASTER_SMOOTH;
    public static BlockAlabasterRaw ALABASTER_RAW;

    public static void onRegister(RegistryManager registry) {
        RockBlockVariantHandler.init();

        registry.blocks(ROCK_BLOCKS.values());

        ALABASTER_BRICKS = registry.block(new BlockAlabasterBricks());
        ALABASTER_SMOOTH = registry.block(new BlockAlabasterSmooth());
        ALABASTER_RAW = registry.block(new BlockAlabasterRaw());

        registry.blocks(ALABASTER_COLOR_BLOCKS.values());
    }

}
