package su.terrafirmagreg.modules.plant.api.types.category;

import net.minecraft.block.material.Material;

import static su.terrafirmagreg.modules.world.classic.ChunkGenClassic.SALT_WATER;

public class PlantCategoryHandler {

  public static void init() {

    PlantCategories.STANDARD = PlantCategory
      .builder("standard")
      .material(Material.PLANTS)
      .canBePotted()
      .build();

    PlantCategories.CREEPING = PlantCategory
      .builder("creeping")
      .material(Material.PLANTS)
      .canBePotted()
      .build();

    PlantCategories.HANGING = PlantCategory
      .builder("hanging")
      .material(Material.VINE)
      .build();

    PlantCategories.FLOATING = PlantCategory
      .builder("floating")
      .material(Material.PLANTS)
      .build();

    PlantCategories.FLOATING_SEA = PlantCategory
      .builder("floating_sea")
      .material(Material.PLANTS)
      .waterType(SALT_WATER)
      .build();

    PlantCategories.DESERT = PlantCategory
      .builder("desert")
      .material(Material.PLANTS)
      .canBePotted()
      .build();

    PlantCategories.DESERT_TALL_PLANT = PlantCategory
      .builder("desert_tall_plant")
      .material(Material.PLANTS)
      .build();

    PlantCategories.DRY = PlantCategory
      .builder("dry")
      .material(Material.PLANTS)
      .canBePotted()
      .build();

    PlantCategories.DRY_TALL_PLANT = PlantCategory
      .builder("dry_tall_plant")
      .material(Material.PLANTS)
      .build();

    PlantCategories.TALL_PLANT = PlantCategory
      .builder("tall_plant")
      .material(Material.PLANTS)
      .canBePotted()
      .build();

    PlantCategories.CACTUS = PlantCategory
      .builder("cactus")
      .material(Material.CACTUS)
      .canBePotted()
      .build();

    PlantCategories.SHORT_GRASS = PlantCategory
      .builder("short_grass")
      .material(Material.VINE)
      .build();

    PlantCategories.TALL_GRASS = PlantCategory
      .builder("tall_grass")
      .material(Material.VINE)
      .build();

    PlantCategories.EPIPHYTE = PlantCategory
      .builder("epiphyte")
      .material(Material.PLANTS)
      .build();

    PlantCategories.REED = PlantCategory
      .builder("reed")
      .material(Material.PLANTS)
      .build();

    PlantCategories.REED_SEA = PlantCategory
      .builder("reed_sea")
      .material(Material.PLANTS)
      .build();

    PlantCategories.TALL_REED = PlantCategory
      .builder("tall_reed")
      .material(Material.PLANTS)
      .build();

    PlantCategories.TALL_REED_SEA = PlantCategory
      .builder("tall_reed_sea")
      .material(Material.PLANTS)
      .build();

    PlantCategories.WATER = PlantCategory
      .builder("water")
      .material(Material.CORAL)
      .build();

    PlantCategories.WATER_SEA = PlantCategory
      .builder("water_sea")
      .material(Material.CORAL)
      .waterType(SALT_WATER)
      .build();

    PlantCategories.TALL_WATER = PlantCategory
      .builder("tall_water")
      .material(Material.CORAL)
      .build();

    PlantCategories.TALL_WATER_SEA = PlantCategory
      .builder("tall_water_sea")
      .material(Material.CORAL)
      .waterType(SALT_WATER)
      .build();

    PlantCategories.EMERGENT_TALL_WATER = PlantCategory
      .builder("emergent_tall_water")
      .material(Material.CORAL)
      .build();

    PlantCategories.EMERGENT_TALL_WATER_SEA = PlantCategory
      .builder("emergent_tall_water_sea")
      .material(Material.CORAL)
      .waterType(SALT_WATER)
      .build();

    PlantCategories.MUSHROOM = PlantCategory
      .builder("mushroom")
      .material(Material.PLANTS)
      .canBePotted()
      .build();
  }
}
