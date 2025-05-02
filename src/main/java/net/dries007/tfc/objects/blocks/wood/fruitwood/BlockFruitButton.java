package net.dries007.tfc.objects.blocks.wood.fruitwood;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;

public class BlockFruitButton extends BlockButtonWood {

  public BlockFruitButton() {
    setHardness(0.5F);
    setSoundType(SoundType.WOOD);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "button");
    OreDictionaryHelper.register(this, "button", "wood");
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }
}
