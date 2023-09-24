package net.dries007.tfc.common.objects.blocks;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.bush.IBushBlock;
import net.dries007.tfc.api.types.bush.type.BushType;
import net.dries007.tfc.api.util.EnumColor;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.common.objects.blocks.devices.*;
import net.dries007.tfc.common.objects.blocks.fluid.BlockFluidHotWater;
import net.dries007.tfc.common.objects.blocks.fluid.BlockFluidWater;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeRootyMimic;
import net.dries007.tfc.module.rock.api.variant.block.IRockBlock;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.module.rock.common.blocks.BlockAlabaster;
import net.dries007.tfc.module.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.soil.common.blocks.peat.BlockPeat;
import net.dries007.tfc.module.soil.common.blocks.peat.BlockPeatGrass;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Map;

import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.*;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.*;
import static net.minecraft.block.material.Material.WATER;

public class TFCBlocks {

    //==== Block =====================================================================================================//


    public static final Map<Pair<RockBlockVariant, EnumColor>, BlockAlabaster> ALABASTER_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<BushType, IBushBlock> BUSH_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<GroundcoverType, BlockGroundcover> GROUNDCOVER_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();


    // Блоки
    public static final LinkedList<Block> BLOCKS = new LinkedList<>();

    // Жидкости
    public static final LinkedList<BlockFluidBase> FLUID = new LinkedList<>();

    public static BlockTreeRootyMimic ROOTY_DIRT_MIMIC;


    public static BlockDebug DEBUG;


    public static BlockAggregate AGGREGATE;
    public static BlockFireClay FIRE_CLAY_BLOCK;
    public static BlockThatch THATCH;
    public static BlockQuern QUERN;
    public static BlockCrucible CRUCIBLE;
    public static BlockBlastFurnace BLAST_FURNACE;
    public static BlockBellows BELLOWS;
    public static BlockBloomery BLOOMERY;

    public static BlockLargeVessel FIRED_LARGE_VESSEL;
    public static BlockFirePit FIREPIT;
    public static BlockThatchBed THATCH_BED;
    public static BlockPitKiln PIT_KILN;
    public static BlockPlacedItemFlat PLACED_ITEM_FLAT;
    public static BlockPlacedItem PLACED_ITEM;
    public static BlockPlacedHide PLACED_HIDE;
    public static BlockCharcoalPile CHARCOAL_PILE;
    public static BlockLogPile LOG_PILE;
    public static BlockCharcoalForge CHARCOAL_FORGE;
    public static BlockMolten MOLTEN;
    public static BlockBloom BLOOM;
    public static BlockIceTFC SEA_ICE;
    public static BlockPowderKeg POWDERKEG;


    public static void preInit() {

        //==== Alabaster =============================================================================================//

        for (var variant : new RockBlockVariant[]{RAW, BRICKS, SMOOTH}) {
            for (var color : EnumColor.values()) {
                var alabasterBlock = new BlockAlabaster(variant, color);

                if (ALABASTER_BLOCKS.put(new Pair<>(variant, color), alabasterBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", color, variant));
                BLOCKS.add(alabasterBlock);
            }
        }

        //==== Groundcover ===========================================================================================//

        for (var type : GroundcoverType.values()) {
            var groundcoverBlock = new BlockGroundcover(type);

            if (GROUNDCOVER_BLOCKS.put(type, groundcoverBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", type, groundcoverBlock));
            BLOCKS.add(groundcoverBlock);
        }

        //==== Fluid =================================================================================================//

        FLUID.add(new BlockFluidHotWater());
        FLUID.add(new BlockFluidWater(FluidRegistry.getFluid("fresh_water"), WATER, false));
        FLUID.add(new BlockFluidWater(FluidRegistry.getFluid("salt_water"), WATER, true));

        //==== Other =================================================================================================//

        BLOCKS.add(DEBUG = new BlockDebug());
        BLOCKS.add(AGGREGATE = new BlockAggregate());
        BLOCKS.add(FIRE_CLAY_BLOCK = new BlockFireClay());
        BLOCKS.add(THATCH = new BlockThatch());
        BLOCKS.add(QUERN = new BlockQuern());
        BLOCKS.add(CRUCIBLE = new BlockCrucible());
        BLOCKS.add(BLAST_FURNACE = new BlockBlastFurnace());
        BLOCKS.add(BELLOWS = new BlockBellows());
        BLOCKS.add(BLOOMERY = new BlockBloomery());
        BLOCKS.add(FIRED_LARGE_VESSEL = new BlockLargeVessel());
        BLOCKS.add(FIREPIT = new BlockFirePit());
        BLOCKS.add(PIT_KILN = new BlockPitKiln());
        BLOCKS.add(PLACED_ITEM = new BlockPlacedItem());
        BLOCKS.add(CHARCOAL_FORGE = new BlockCharcoalForge());
        BLOCKS.add(SEA_ICE = new BlockIceTFC(FluidRegistry.getFluid("salt_water")));
        BLOCKS.add(POWDERKEG = new BlockPowderKeg());

        BLOCKS.add(PLACED_ITEM_FLAT = new BlockPlacedItemFlat());
        BLOCKS.add(PLACED_HIDE = new BlockPlacedHide());
        BLOCKS.add(CHARCOAL_PILE = new BlockCharcoalPile());
        BLOCKS.add(LOG_PILE = new BlockLogPile());
        BLOCKS.add(MOLTEN = new BlockMolten());
        BLOCKS.add(BLOOM = new BlockBloom());
        BLOCKS.add(THATCH_BED = new BlockThatchBed());
        BLOCKS.add(ROOTY_DIRT_MIMIC = new BlockTreeRootyMimic());
    }


    @Nonnull
    public static Block getAlabasterBlock(@Nonnull RockBlockVariant variant, @Nonnull EnumColor color) {
        var block = (Block) ALABASTER_BLOCKS.get(new Pair<>(variant, color));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", color, variant));
    }

    @Nonnull
    public static Block getBushBlock(@Nonnull BushType type) {
        var block = (Block) BUSH_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }

    @Nonnull
    public static Block getGroundcoverBlock(@Nonnull GroundcoverType type) {
        var block = (Block) GROUNDCOVER_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }


    public static boolean isWater(IBlockState current) {
        return current.getMaterial() == WATER;
    }

    public static boolean isFreshWater(IBlockState current) {
        return current == FluidRegistry.getFluid("fresh_water").getBlock().getDefaultState();
    }

    public static boolean isSaltWater(IBlockState current) {
        return current == FluidRegistry.getFluid("salt_water").getBlock().getDefaultState();
    }

    public static boolean isFreshWaterOrIce(IBlockState current) {
        return current.getBlock() == Blocks.ICE || isFreshWater(current);
    }

    public static boolean isRawStone(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getBlockVariant() == RAW;
        return false;
    }

    public static boolean isClay(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == CLAY || variant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isDirt(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getBlockVariant() == DIRT;
        return false;
    }

    public static boolean isSand(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getBlockVariant() == SAND;
        }
        return false;
    }

    public static boolean isGravel(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getBlockVariant() == GRAVEL;
        }
        return false;
    }

    public static boolean isSoil(IBlockState current) {
        if (current.getBlock() instanceof BlockPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS ||
                    variant == DIRT || variant == CLAY || variant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isGrowableSoil(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS ||
                    variant == DIRT || variant == CLAY || variant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isSoilOrGravel(IBlockState current) {
        if (current.getBlock() instanceof BlockPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS || variant == DIRT;
        }
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getBlockVariant() == GRAVEL;
        return false;
    }

    public static boolean isGrass(IBlockState current) {
        if (current.getBlock() instanceof BlockPeatGrass) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS || variant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isDryGrass(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getBlockVariant() == DRY_GRASS;
        return false;
    }

    public static boolean isGround(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            var variant = rockTypeBlock.getBlockVariant();
            return variant == GRAVEL || variant == SAND || variant == RAW;
        }
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS || variant == DIRT;
        }
        return false;
    }
}
