package com.eerussianguy.firmalife.blocks;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockFruitFence extends BlockFence {

  public BlockFruitFence() {
    super(Material.WOOD, Material.WOOD.getMaterialMapColor());
    setHarvestLevel(ToolClasses.AXE, 0);
    setHardness(2.0F);
    setResistance(15.0F);
  }
}
