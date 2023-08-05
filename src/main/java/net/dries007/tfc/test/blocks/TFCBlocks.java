package net.dries007.tfc.test.blocks;

import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.plant.Plant;
import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockType;
import net.dries007.tfc.api.types.rock.RockVariant;
import net.dries007.tfc.api.types.soil.Soil;
import net.dries007.tfc.api.types.soil.SoilVariant;
import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.api.types.wood.WoodVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeat;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeatGrass;
import net.dries007.tfc.objects.items.itemblock.ItemBlockCrucible;
import net.dries007.tfc.objects.items.itemblock.ItemBlockLargeVessel;
import net.dries007.tfc.objects.items.itemblock.ItemBlockPowderKeg;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;

import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.api.types.rock.RockVariant.*;

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


    private TFCBlocks() {
    }

    public static void preInit() {

        //=== Rock ===================================================================================================//

        for (Rock rock : Rock.values()) {
            for (RockType rockType : RockType.values()) {
                for (RockVariant rockVariant : rockType.getRockVariants()) {
                    var rockTypeBlock = rockType.create(rockVariant, rock);

                    if (ROCK_BLOCKS.put(new Triple<>(rockType, rockVariant, rock), rockTypeBlock) != null)
                        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", rockType, rockVariant, rock));
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

        //=== Alabaster ==============================================================================================//

        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            for (RockVariant rockVariant : new RockVariant[]{RAW, BRICK, SMOOTH}) {
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
        //NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(SEA_ICE = new BlockIceTFC(FluidsTFC.SALT_WATER.get())));
        ITEM_BLOCKS.add(new ItemBlockPowderKeg(POWDERKEG = new BlockPowderKeg()));

        BLOCKS.add(PLACED_ITEM_FLAT = new BlockPlacedItemFlat());
        BLOCKS.add(PLACED_HIDE = new BlockPlacedHide());
        BLOCKS.add(CHARCOAL_PILE = new BlockCharcoalPile());
        BLOCKS.add(LOG_PILE = new BlockLogPile());
        BLOCKS.add(MOLTEN = new BlockMolten());
        BLOCKS.add(BLOOM = new BlockBloom());
        BLOCKS.add(THATCH_BED = new BlockThatchBed());

    }
}
