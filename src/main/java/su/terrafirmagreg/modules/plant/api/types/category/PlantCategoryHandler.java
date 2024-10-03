package su.terrafirmagreg.modules.plant.api.types.category;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.EnumPlantType;

import net.dries007.tfc.objects.blocks.plants.BlockPlant;
import net.dries007.tfc.objects.blocks.plants.BlockPlantCactus;
import net.dries007.tfc.objects.blocks.plants.BlockPlantCreeping;
import net.dries007.tfc.objects.blocks.plants.BlockPlantEmergentTallWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantEpiphyte;
import net.dries007.tfc.objects.blocks.plants.BlockPlantFloatingWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantHanging;
import net.dries007.tfc.objects.blocks.plants.BlockPlantMushroom;
import net.dries007.tfc.objects.blocks.plants.BlockPlantShortGrass;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTall;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTallGrass;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTallWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantWater;

public class PlantCategoryHandler {

  public static void init() {

    PlantCategories.STANDARD = PlantCategory
      .builder("standard")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    PlantCategories.CREEPING = PlantCategory
      .builder("creeping")
      .material(Material.PLANTS)
      .factory(BlockPlantCreeping::new)
      .canBePotted()
      .build();

    PlantCategories.HANGING = PlantCategory
      .builder("hanging")
      .material(Material.VINE)
      .factory(BlockPlantHanging::new)
      .build();

    PlantCategories.FLOATING = PlantCategory
      .builder("floating")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Water)
      .factory(BlockPlantFloatingWater::new)
      .build();

    PlantCategories.FLOATING_SEA = PlantCategory
      .builder("floating_sea")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Water)
      .factory(BlockPlantFloatingWater::new)
      .waterType("salt_water")
      .build();

    PlantCategories.DESERT = PlantCategory
      .builder("desert")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    PlantCategories.DESERT_TALL_PLANT = PlantCategory
      .builder("desert_tall_plant")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlantTall::new)
      .build();

    PlantCategories.DRY = PlantCategory
      .builder("dry")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    PlantCategories.DRY_TALL_PLANT = PlantCategory
      .builder("dry_tall_plant")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    PlantCategories.TALL_PLANT = PlantCategory
      .builder("tall_plant")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .canBePotted()
      .build();

    PlantCategories.CACTUS = PlantCategory
      .builder("cactus")
      .material(Material.CACTUS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlantCactus::new)
      .canBePotted()
      .build();

    PlantCategories.SHORT_GRASS = PlantCategory
      .builder("short_grass")
      .material(Material.VINE)
      .factory(BlockPlantShortGrass::new)
      .build();

    PlantCategories.TALL_GRASS = PlantCategory
      .builder("tall_grass")
      .material(Material.VINE)
      .factory(BlockPlantTallGrass::new)
      .build();

    PlantCategories.EPIPHYTE = PlantCategory
      .builder("epiphyte")
      .material(Material.PLANTS)
      .factory(BlockPlantEpiphyte::new)
      .build();

    PlantCategories.REED = PlantCategory
      .builder("reed")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .build();

    PlantCategories.REED_SEA = PlantCategory
      .builder("reed_sea")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .build();

    PlantCategories.TALL_REED = PlantCategory
      .builder("tall_reed")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    PlantCategories.TALL_REED_SEA = PlantCategory
      .builder("tall_reed_sea")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    PlantCategories.WATER = PlantCategory
      .builder("water")
      .material(Material.CORAL)
      .factory(BlockPlantWater::new)
      .build();

    PlantCategories.WATER_SEA = PlantCategory
      .builder("water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantWater::new)
      .waterType("salt_water")
      .build();

    PlantCategories.TALL_WATER = PlantCategory
      .builder("tall_water")
      .material(Material.CORAL)
      .factory(BlockPlantTallWater::new)
      .build();

    PlantCategories.TALL_WATER_SEA = PlantCategory
      .builder("tall_water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantTallWater::new)
      .waterType("salt_water")
      .build();

    PlantCategories.EMERGENT_TALL_WATER = PlantCategory
      .builder("emergent_tall_water")
      .material(Material.CORAL)
      .factory(BlockPlantEmergentTallWater::new)
      .build();

    PlantCategories.EMERGENT_TALL_WATER_SEA = PlantCategory
      .builder("emergent_tall_water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantEmergentTallWater::new)
      .waterType("salt_water")
      .build();

    PlantCategories.MUSHROOM = PlantCategory
      .builder("mushroom")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Cave)
      .factory(BlockPlantMushroom::new)
      .canBePotted()
      .build();
  }
}
