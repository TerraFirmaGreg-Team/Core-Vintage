package tfcflorae.objects.blocks.wood.fruitwood;

import net.dries007.tfc.objects.blocks.wood.BlockBarrel;

import net.minecraft.init.Blocks;

import tfcflorae.util.OreDictionaryHelper;

public class BlockFruitBarrelTest extends BlockBarrel {

  public BlockFruitBarrelTest() {
    super();

    OreDictionaryHelper.register(this, "barrel");
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }
}
