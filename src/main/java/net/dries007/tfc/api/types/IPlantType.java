package net.dries007.tfc.api.types;

import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.object.block.BlockPlant;

import net.minecraft.block.material.Material;

public interface IPlantType {

  BlockPlant create(PlantType plant);

  Material getMaterial();
}
