package net.dries007.tfc.test.blocks;

import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.api.types2.rock.RockVariant.*;

import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeat;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeatGrass;
import net.dries007.tfc.objects.items.itemblock.ItemBlockCrucible;
import net.dries007.tfc.objects.items.itemblock.ItemBlockSluice;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

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
    public static BlockSluice SLUICE;


    private TFCBlocks() {
    }

    public static void preInit() {

        //=== Rock ===================================================================================================//

        for (RockType rockType : RockType.values()) {
            for (RockBlockType rockBlockType : RockBlockType.values()) {
                for (RockVariant rockVariant : rockBlockType.getRockVariants()) {
                    var rockTypeBlock = rockBlockType.createBlockType(rockVariant, rockType);

                    if (ROCK_BLOCKS.put(new Triple<>(rockBlockType, rockVariant, rockType), rockTypeBlock) != null)
                        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", rockBlockType, rockVariant, rockType));
                }
            }
        }

        //=== Soil ===================================================================================================//

        for (SoilType soilType : SoilType.values()) {
            for (SoilVariant soilVariant : SoilVariant.values()) {
                var soilTypeBlock = soilVariant.createBlock(soilType);

                if (SOIL_BLOCKS.put(new Pair<>(soilVariant, soilType), soilTypeBlock) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", soilVariant, soilType));
            }
        }

        //=== Plant ==================================================================================================//

        for (PlantType plantType : PlantType.values()) {
            var plantTypeBlock = plantType.getPlantVariant().create(plantType);

            if (PLANT_BLOCKS.put(new Pair<>(plantType.getPlantVariant(), plantType), plantTypeBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", plantType.getPlantVariant(), plantType));
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


        //=== Other ==================================================================================================//

        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(DEBUG = new BlockDebug()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(PEAT = new BlockSoilPeat(Material.GROUND)));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(PEAT_GRASS = new BlockSoilPeatGrass(Material.GRASS)));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(AGGREGATE = new BlockAggregate()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(FIRE_CLAY_BLOCK = new BlockFireClay()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(THATCH = new BlockThatch()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(QUERN = new BlockQuern()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockCrucible(CRUCIBLE = new BlockCrucible()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(FIRE_BRICKS = new BlockFireBrick()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(BLAST_FURNACE = new BlockBlastFurnace()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(BELLOWS = new BlockBellows()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(BLOOMERY = new BlockBloomery()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(NEST_BOX = new BlockNestBox()));
        NORMAL_ITEM_BLOCKS.add(new ItemBlockSluice(SLUICE = new BlockSluice()));
    }
}
