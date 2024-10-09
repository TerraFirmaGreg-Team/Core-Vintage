package su.terrafirmagreg.modules.plant.api.types.category;

import su.terrafirmagreg.modules.plant.object.block.BlockPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantCactus;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantCreeping;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantEmergentTallWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantEpiphyte;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantFloatingWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantHanging;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantHangingCreeping;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantHangingTall;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantMushroom;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantShortGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTall;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantWater;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.EnumPlantType;

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

    PlantCategories.HANGING_TALL = PlantCategory
      .builder("hanging_tall")
      .material(Material.VINE)
      .factory(BlockPlantHangingTall::new)
      .build();

    PlantCategories.HANGING_CREEPING = PlantCategory
      .builder("hanging_creeping")
      .material(Material.VINE)
      .factory(BlockPlantHangingCreeping::new)
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
