package net.dries007.tfc.objects.blocks;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

public class BlockAggregate extends BlockGravel {

  public BlockAggregate() {
    setSoundType(SoundType.SAND);
    setHardness(0.4f);
  }
}
