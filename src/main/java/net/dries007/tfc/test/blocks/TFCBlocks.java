package net.dries007.tfc.test.blocks;

import gregtech.api.GregTechAPI;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.fluid.properties.FluidWrapper;
import net.dries007.tfc.api.types.metal.MetalVariant;
import net.dries007.tfc.api.types.plant.Plant;
import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.dries007.tfc.api.types.soil.Soil;
import net.dries007.tfc.api.types.soil.SoilVariant;
import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.api.types.wood.WoodVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.objects.blocks.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.metal.BlockMetalCladding;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeat;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeatGrass;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.fluid.BlockFluidHotWater;
import net.dries007.tfc.objects.fluids.fluid.BlockFluidTFC;
import net.dries007.tfc.objects.fluids.fluid.BlockFluidWater;
import net.dries007.tfc.objects.items.itemblock.ItemBlockCrucible;
import net.dries007.tfc.objects.items.itemblock.ItemBlockLargeVessel;
import net.dries007.tfc.objects.items.itemblock.ItemBlockPowderKeg;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;

import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.*;

public class TFCBlocks {
    public static BlockDebug DEBUG;
    public static BlockSoilPeat PEAT;
    public static BlockSoilPeatGrass PEAT_GRASS;
    public static BlockAggregate AGGREGATE;
    public static BlockFireClay FIRE_CLAY_BLOCK;
    public static BlockThatch THATCH;
    public static BlockQuern QUERN;
    public static BlockCrucible CRUCIBLE;
    public static BlockFireBrick FIRE_BRICKS;
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
        FluidsTFC.preInit();

        //=== Rock ===================================================================================================//

        for (var rock : Rock.getRockTypes()) {
            for (var rockBlockType : RockBlockType.getRockBlockTypes()) {
                for (var pair : rockBlockType.getBlockFactoryMap()) {
                    var rockBlockVariant = pair.getLeft();
                    var factory = pair.getRight();

                    var resultBlock = factory.apply(rockBlockType, rockBlockVariant, rock);

                    if (ROCK_BLOCKS.put(new Triple<>(rockBlockType, rockBlockVariant, rock), resultBlock) != null)
                        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", rockBlockType, rockBlockVariant, rock));
                }
            }
        }

        //=== Soil ===================================================================================================//

        for (Soil soil : Soil.values()) {
            for (SoilVariant soilVariant : SoilVariant.values()) {
                var soilTypeBlock = soilVariant.create(soil);

                if (SOIL_BLOCKS.put(new Pair<>(soilVariant, soil), soilTypeBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", soilVariant, soil));
            }
        }

        //=== Plant ==================================================================================================//

        for (Plant plant : Plant.values()) {
            var plantTypeBlock = plant.getPlantVariant().create(plant);

            if (PLANT_BLOCKS.put(new Pair<>(plant.getPlantVariant(), plant), plantTypeBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", plant.getPlantVariant(), plant));
        }

        //=== Wood ===================================================================================================//

        for (Wood wood : Wood.values()) {
            for (WoodVariant woodVariant : WoodVariant.values()) {
                var woodVariantBlock = woodVariant.create(wood);

                if (WOOD_BLOCKS.put(new Pair<>(woodVariant, wood), woodVariantBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", woodVariant, wood));
            }
        }

        //=== Metal ==================================================================================================//

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            if (material.hasFlag(TFGMaterialFlags.GENERATE_TFC)) {
                for (MetalVariant metalVariant : MetalVariant.values()) {
                    var metalVariantBlock = metalVariant.create(material);

                    if (METAL_BLOCKS.put(new Pair<>(metalVariant, material), metalVariantBlock) != null)
                        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", metalVariant, material, metalVariantBlock));
                }
            }
        }

        //=== Alabaster ==============================================================================================//

        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            for (var rockVariant : new RockBlockVariant[]{RAW, BRICK, SMOOTH}) {
                var alabasterColorBlock = new BlockAlabaster(rockVariant, dyeColor);
                var alabasterBlock = new BlockAlabaster(rockVariant);

                if (ALABASTER_BLOCK.put(new Pair<>(dyeColor.getName(), rockVariant), alabasterColorBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", dyeColor, rockVariant));

                ALABASTER_BLOCK.put(new Pair<>("plain", rockVariant), alabasterBlock);
            }
        }

        //=== Groundcover ============================================================================================//

        for (GroundcoverType groundcoverType : GroundcoverType.values()) {
            var groundcoverBlock = new BlockGroundcover(groundcoverType);

            if (GROUNDCOVER_BLOCK.put(groundcoverType, groundcoverBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", groundcoverType, groundcoverBlock));
        }

        //=== Fluid ==================================================================================================//

        for (FluidWrapper wrapper : FluidsTFC.getAllAlcoholsFluids()) {
            FLUID.add(new BlockFluidTFC(wrapper.get(), Material.WATER));
        }

        for (FluidWrapper wrapper : FluidsTFC.getAllOtherFiniteFluids()) {
            FLUID.add(new BlockFluidTFC(wrapper.get(), Material.WATER));
        }
        for (EnumDyeColor color : EnumDyeColor.values()) {
            FluidWrapper wrapper = FluidsTFC.getFluidFromDye(color);
            FLUID.add(new BlockFluidTFC(wrapper.get(), Material.WATER));
        }

        FLUID.add(new BlockFluidHotWater());
        FLUID.add(new BlockFluidWater(FluidsTFC.FRESH_WATER.get(), Material.WATER, false));
        FLUID.add(new BlockFluidWater(FluidsTFC.SALT_WATER.get(), Material.WATER, true));

        //=== Other ==================================================================================================//

        ITEM_BLOCKS.add(new ItemBlockTFC(DEBUG = new BlockDebug()));
        ITEM_BLOCKS.add(new ItemBlockTFC(PEAT = new BlockSoilPeat(Material.GROUND)));
        ITEM_BLOCKS.add(new ItemBlockTFC(PEAT_GRASS = new BlockSoilPeatGrass(Material.GRASS)));
        ITEM_BLOCKS.add(new ItemBlockTFC(AGGREGATE = new BlockAggregate()));
        ITEM_BLOCKS.add(new ItemBlockTFC(FIRE_CLAY_BLOCK = new BlockFireClay()));
        ITEM_BLOCKS.add(new ItemBlockTFC(THATCH = new BlockThatch()));
        ITEM_BLOCKS.add(new ItemBlockTFC(QUERN = new BlockQuern()));
        ITEM_BLOCKS.add(new ItemBlockCrucible(CRUCIBLE = new BlockCrucible()));
        ITEM_BLOCKS.add(new ItemBlockTFC(FIRE_BRICKS = new BlockFireBrick()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BLAST_FURNACE = new BlockBlastFurnace()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BELLOWS = new BlockBellows()));
        ITEM_BLOCKS.add(new ItemBlockTFC(BLOOMERY = new BlockBloomery()));
        ITEM_BLOCKS.add(new ItemBlockTFC(NEST_BOX = new BlockNestBox()));
        ITEM_BLOCKS.add(new ItemBlockLargeVessel(FIRED_LARGE_VESSEL = new BlockLargeVessel()));
        ITEM_BLOCKS.add(new ItemBlock(FIREPIT = new BlockFirePit()));
        ITEM_BLOCKS.add(new ItemBlock(PIT_KILN = new BlockPitKiln()));
        ITEM_BLOCKS.add(new ItemBlock(PLACED_ITEM = new BlockPlacedItem()));
        ITEM_BLOCKS.add(new ItemBlock(CHARCOAL_FORGE = new BlockCharcoalForge()));
        ITEM_BLOCKS.add(new ItemBlockTFC(SEA_ICE = new BlockIceTFC(FluidsTFC.SALT_WATER.get())));
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
