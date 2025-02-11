package net.dries007.tfc.objects.blocks.wood.fruitwood;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.init.Blocks;

import net.dries007.tfc.objects.blocks.wood.BlockBarrel;

public class BlockFruitBarrelTest extends BlockBarrel {

  public BlockFruitBarrelTest() {
    super();

    OreDictionaryHelper.register(this, "barrel");
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }
}
