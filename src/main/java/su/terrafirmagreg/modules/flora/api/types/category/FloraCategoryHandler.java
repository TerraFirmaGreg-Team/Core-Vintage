package su.terrafirmagreg.modules.flora.api.types.category;

import su.terrafirmagreg.modules.flora.object.block.BlockPlant;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantCactus;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantCreeping;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantEmergentTallWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantEpiphyte;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantFloatingWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantHanging;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantHangingCreeping;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantHangingTall;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantMushroom;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantShortGrass;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTall;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTallGrass;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTallWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantWater;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.EnumPlantType;

public class FloraCategoryHandler {

  public static void init() {

    FloraCategories.STANDARD = FloraCategory
      .builder("standard")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    FloraCategories.CREEPING = FloraCategory
      .builder("creeping")
      .material(Material.PLANTS)
      .factory(BlockPlantCreeping::new)
      .canBePotted()
      .build();

    FloraCategories.HANGING = FloraCategory
      .builder("hanging")
      .material(Material.VINE)
      .factory(BlockPlantHanging::new)
      .build();

    FloraCategories.HANGING_TALL = FloraCategory
      .builder("hanging_tall")
      .material(Material.VINE)
      .factory(BlockPlantHangingTall::new)
      .build();

    FloraCategories.HANGING_CREEPING = FloraCategory
      .builder("hanging_creeping")
      .material(Material.VINE)
      .factory(BlockPlantHangingCreeping::new)
      .build();

    FloraCategories.FLOATING = FloraCategory
      .builder("floating")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Water)
      .factory(BlockPlantFloatingWater::new)
      .build();

    FloraCategories.FLOATING_SEA = FloraCategory
      .builder("floating_sea")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Water)
      .factory(BlockPlantFloatingWater::new)
      .waterType("salt_water")
      .build();

    FloraCategories.DESERT = FloraCategory
      .builder("desert")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    FloraCategories.DESERT_TALL_PLANT = FloraCategory
      .builder("desert_tall_plant")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlantTall::new)
      .build();

    FloraCategories.DRY = FloraCategory
      .builder("dry")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .canBePotted()
      .build();

    FloraCategories.DRY_TALL_PLANT = FloraCategory
      .builder("dry_tall_plant")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    FloraCategories.TALL_PLANT = FloraCategory
      .builder("tall_plant")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .canBePotted()
      .build();

    FloraCategories.CACTUS = FloraCategory
      .builder("cactus")
      .material(Material.CACTUS)
      .enumPlantType(EnumPlantType.Desert)
      .factory(BlockPlantCactus::new)
      .canBePotted()
      .build();

    FloraCategories.SHORT_GRASS = FloraCategory
      .builder("short_grass")
      .material(Material.VINE)
      .factory(BlockPlantShortGrass::new)
      .build();

    FloraCategories.TALL_GRASS = FloraCategory
      .builder("tall_grass")
      .material(Material.VINE)
      .factory(BlockPlantTallGrass::new)
      .build();

    FloraCategories.EPIPHYTE = FloraCategory
      .builder("epiphyte")
      .material(Material.PLANTS)
      .factory(BlockPlantEpiphyte::new)
      .build();

    FloraCategories.REED = FloraCategory
      .builder("reed")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .build();

    FloraCategories.REED_SEA = FloraCategory
      .builder("reed_sea")
      .material(Material.PLANTS)
      .factory(BlockPlant::new)
      .build();

    FloraCategories.TALL_REED = FloraCategory
      .builder("tall_reed")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    FloraCategories.TALL_REED_SEA = FloraCategory
      .builder("tall_reed_sea")
      .material(Material.PLANTS)
      .factory(BlockPlantTall::new)
      .build();

    FloraCategories.WATER = FloraCategory
      .builder("water")
      .material(Material.CORAL)
      .factory(BlockPlantWater::new)
      .build();

    FloraCategories.WATER_SEA = FloraCategory
      .builder("water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantWater::new)
      .waterType("salt_water")
      .build();

    FloraCategories.TALL_WATER = FloraCategory
      .builder("tall_water")
      .material(Material.CORAL)
      .factory(BlockPlantTallWater::new)
      .build();

    FloraCategories.TALL_WATER_SEA = FloraCategory
      .builder("tall_water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantTallWater::new)
      .waterType("salt_water")
      .build();

    FloraCategories.EMERGENT_TALL_WATER = FloraCategory
      .builder("emergent_tall_water")
      .material(Material.CORAL)
      .factory(BlockPlantEmergentTallWater::new)
      .build();

    FloraCategories.EMERGENT_TALL_WATER_SEA = FloraCategory
      .builder("emergent_tall_water_sea")
      .material(Material.CORAL)
      .factory(BlockPlantEmergentTallWater::new)
      .waterType("salt_water")
      .build();

    FloraCategories.MUSHROOM = FloraCategory
      .builder("mushroom")
      .material(Material.PLANTS)
      .enumPlantType(EnumPlantType.Cave)
      .factory(BlockPlantMushroom::new)
      .canBePotted()
      .build();
  }
}
