package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockPeat extends Block {

  public BlockPeat(Material material) {
    super(material);
    setSoundType(SoundType.GROUND);
    setHardness(0.6F);
    setHarvestLevel(ToolClasses.SHOVEL, 0);
    OreDictionaryHelper.register(this, "peat");
    Blocks.FIRE.setFireInfo(this, 5, 10);
  }
}
