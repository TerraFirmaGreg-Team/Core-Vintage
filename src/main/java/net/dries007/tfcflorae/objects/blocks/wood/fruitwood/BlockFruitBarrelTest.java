package net.dries007.tfcflorae.objects.blocks.wood.fruitwood;

import net.minecraft.init.Blocks;

import net.dries007.tfc.objects.blocks.wood.BlockBarrel;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

public class BlockFruitBarrelTest extends BlockBarrel {

  public BlockFruitBarrelTest() {
    super();

    OreDictionaryHelper.register(this, "barrel");
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }
}
