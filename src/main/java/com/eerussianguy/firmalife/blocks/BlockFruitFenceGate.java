package com.eerussianguy.firmalife.blocks;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;

public class BlockFruitFenceGate extends BlockFenceGate {

  public BlockFruitFenceGate() {
    super(BlockPlanks.EnumType.OAK);
    setHarvestLevel(ToolClasses.AXE, 0);
    setHardness(2.0F);
    setResistance(15.0F);
  }
}
