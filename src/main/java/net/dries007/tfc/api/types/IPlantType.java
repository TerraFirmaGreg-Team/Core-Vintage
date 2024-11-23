package net.dries007.tfc.api.types;

import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.object.block.BlockPlant;

import net.minecraft.block.material.Material;

public interface IPlantType {

  BlockPlant create(FloraType plant);

  Material getMaterial();
}
