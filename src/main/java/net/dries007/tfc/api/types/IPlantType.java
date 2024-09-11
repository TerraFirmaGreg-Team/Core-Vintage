package net.dries007.tfc.api.types;

import net.minecraft.block.material.Material;


import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;

public interface IPlantType {

  BlockPlantTFC create(Plant plant);

  Material getPlantMaterial();
}
