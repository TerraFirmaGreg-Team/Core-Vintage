package net.dries007.tfc.api.types;

import net.minecraft.block.material.Material;

import net.dries007.tfc.objects.blocks.plants.BlockPlant;

public interface IPlantType {

  BlockPlant create(Plant plant);

  Material getPlantMaterial();
}
