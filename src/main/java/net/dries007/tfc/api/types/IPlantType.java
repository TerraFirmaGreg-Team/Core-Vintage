package net.dries007.tfc.api.types;

import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.material.Material;

import net.dries007.tfc.objects.blocks.plants.BlockPlant;

public interface IPlantType {

  BlockPlant create(PlantType plant);

  Material getMaterial();
}
