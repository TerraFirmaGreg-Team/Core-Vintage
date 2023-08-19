package net.dries007.tfc.common.objects.blocks;

import gregtech.api.GregTechAPI;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.crop.variant.CropBlockVariant;
import net.dries007.tfc.api.types.metal.variant.MetalBlockVariant;
import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.common.objects.blocks.devices.*;
import net.dries007.tfc.common.objects.blocks.fluid.BlockFluidHotWater;
import net.dries007.tfc.common.objects.blocks.fluid.BlockFluidWater;
import net.dries007.tfc.common.objects.blocks.metal.BlockMetalCladding;
import net.dries007.tfc.common.objects.blocks.rock.BlockAlabaster;
import net.dries007.tfc.common.objects.blocks.soil.peat.BlockPeat;
import net.dries007.tfc.common.objects.blocks.soil.peat.BlockPeatGrass;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockCrucible;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockLargeVessel;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockPowderKeg;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.FluidRegistry;

import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.*;

public class TFCBlocks {
    public static BlockDebug DEBUG;
    public static BlockPeat PEAT;
    public static BlockPeatGrass PEAT_GRASS;
    public static BlockAggregate AGGREGATE;
    public static BlockFireClay FIRE_CLAY_BLOCK;
    public static BlockThatch THATCH;
    public static BlockQuern QUERN;
    public static BlockCrucible CRUCIBLE;
    public static BlockBlastFurnace BLAST_FURNACE;
    public static BlockBellows BELLOWS;
    public static BlockBloomery BLOOMERY;
    public static BlockNestBox NEST_BOX;
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
    public static BlockMetalCladding CLADDING;

    public static void preInit() {

        //=== Crop ===================================================================================================//

        for (var cropBlockVariant : CropBlockVariant.getCropBlockVariants()) {
            for (var cropType : CropType.getCropTypes()) {
                var cropBlock = cropBlockVariant.create(cropType);

                if (CROP_BLOCKS.put(new Pair<>(cropBlockVariant, cropType), cropBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", cropBlockVariant, cropType));
            }
        }

        //=== Rock ===================================================================================================//

        for (var rockBlockVariant : RockBlockVariant.getRockBlockVariants()) {
            for (var rockType : RockType.getRockTypes()) {
                var rockBlock = rockBlockVariant.create(rockType);

                if (ROCK_BLOCKS.put(new Pair<>(rockBlockVariant, rockType), rockBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", rockBlockVariant, rockType));
            }
        }

        //=== Soil ===================================================================================================//

        for (var soilBlockVariant : SoilBlockVariant.getSoilBlockVariants()) {
            for (var soilType : SoilType.getSoilTypes()) {
                var soilBlock = soilBlockVariant.create(soilType);

                if (SOIL_BLOCKS.put(new Pair<>(soilBlockVariant, soilType), soilBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", soilBlockVariant, soilType));
            }
        }

        //=== Wood ===================================================================================================//

        for (var woodBlockVariant : WoodBlockVariant.getWoodBlockVariants()) {
            for (var woodType : WoodType.getWoodTypes()) {
                var woodBlock = woodBlockVariant.create(woodType);

                if (WOOD_BLOCKS.put(new Pair<>(woodBlockVariant, woodType), woodBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", woodBlockVariant, woodType));
            }
        }


        //=== Plant ==================================================================================================//

        for (var plant : PlantType.getPlantTypes()) {
            var plantTypeBlock = plant.getPlantVariant().create(plant);

            if (PLANT_BLOCKS.put(new Pair<>(plant.getPlantVariant(), plant), plantTypeBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", plant.getPlantVariant(), plant));
        }

        //=== Metal ==================================================================================================//

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            if (material.hasFlag(TFGMaterialFlags.GENERATE_ANVIL)) {
                for (MetalBlockVariant metalBlockVariant : MetalBlockVariant.getMetalBlockVariants()) {
                    var metalVariantBlock = metalBlockVariant.create(material);

                    if (METAL_BLOCKS.put(new Pair<>(metalBlockVariant, material), metalVariantBlock) != null)
                        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", metalBlockVariant, material, metalVariantBlock));
                }
            }
        }

        //=== Alabaster ==============================================================================================//

        for (var rockVariant : new RockBlockVariant[]{RAW, BRICK, SMOOTH}) {
            var alabasterBlock = new BlockAlabaster(rockVariant);

            ALABASTER_BLOCKS.put(new Pair<>("plain", rockVariant), alabasterBlock);

            for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
                var alabasterColorBlock = new BlockAlabaster(rockVariant, dyeColor);

                if (ALABASTER_BLOCKS.put(new Pair<>(dyeColor.getName(), rockVariant), alabasterColorBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", dyeColor, rockVariant));
            }
        }


        //=== Groundcover ============================================================================================//

        for (GroundcoverType groundcoverType : GroundcoverType.values()) {
            var groundcoverBlock = new BlockGroundcover(groundcoverType);

            if (GROUNDCOVER_BLOCKS.put(groundcoverType, groundcoverBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", groundcoverType, groundcoverBlock));
        }

        //=== Fluid ==================================================================================================//

        FLUID.add(new BlockFluidHotWater());
        FLUID.add(new BlockFluidWater(FluidRegistry.getFluid("fresh_water"), Material.WATER, false));
        FLUID.add(new BlockFluidWater(FluidRegistry.getFluid("salt_water"), Material.WATER, true));

        //=== Other ==================================================================================================//

        PEAT = new BlockPeat();
        PEAT_GRASS = new BlockPeatGrass();

        ITEM_BLOCKS.add(PEAT.getItemBlock());
        ITEM_BLOCKS.add(PEAT_GRASS.getItemBlock());
        ITEM_BLOCKS.add(new ItemBlockTFC(DEBUG = new BlockDebug()));
        ITEM_BLOCKS.add(new ItemBlockTFC(AGGREGATE = new BlockAggregate()));
        ITEM_BLOCKS.add(new ItemBlockTFC(FIRE_CLAY_BLOCK = new BlockFireClay()));
        ITEM_BLOCKS.add(new ItemBlockTFC(THATCH = new BlockThatch()));
        ITEM_BLOCKS.add(new ItemBlockTFC(QUERN = new BlockQuern()));
        ITEM_BLOCKS.add(new ItemBlockCrucible(CRUCIBLE = new BlockCrucible()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BLAST_FURNACE = new BlockBlastFurnace()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BELLOWS = new BlockBellows()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BLOOMERY = new BlockBloomery()));
        ITEM_BLOCKS.add(new ItemBlockTFC(NEST_BOX = new BlockNestBox()));
        ITEM_BLOCKS.add(new ItemBlockLargeVessel(FIRED_LARGE_VESSEL = new BlockLargeVessel()));
        ITEM_BLOCKS.add(new ItemBlock(FIREPIT = new BlockFirePit()));
        ITEM_BLOCKS.add(new ItemBlock(PIT_KILN = new BlockPitKiln()));
        ITEM_BLOCKS.add(new ItemBlock(PLACED_ITEM = new BlockPlacedItem()));
        ITEM_BLOCKS.add(new ItemBlock(CHARCOAL_FORGE = new BlockCharcoalForge()));
        ITEM_BLOCKS.add(new ItemBlockTFC(SEA_ICE = new BlockIceTFC(FluidRegistry.getFluid("salt_water"))));
        ITEM_BLOCKS.add(new ItemBlockPowderKeg(POWDERKEG = new BlockPowderKeg()));

        BLOCKS.add(PLACED_ITEM_FLAT = new BlockPlacedItemFlat());
        BLOCKS.add(PLACED_HIDE = new BlockPlacedHide());
        BLOCKS.add(CHARCOAL_PILE = new BlockCharcoalPile());
        BLOCKS.add(LOG_PILE = new BlockLogPile());
        BLOCKS.add(MOLTEN = new BlockMolten());
        BLOCKS.add(BLOOM = new BlockBloom());
        BLOCKS.add(THATCH_BED = new BlockThatchBed());
        BLOCKS.add(CLADDING = new BlockMetalCladding());


    }
}
