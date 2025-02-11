package net.dries007.tfc.objects.blocks.wood.fruitwood;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;

public class BlockFruitFenceGate extends BlockFenceGate {

  public BlockFruitFenceGate() {
    super(BlockPlanks.EnumType.OAK);
    setHarvestLevel("axe", 0);
    setHardness(2.0F);
    setResistance(15.0F);
    setSoundType(SoundType.WOOD);
    OreDictionaryHelper.register(this, "fence_gate");
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }
}
