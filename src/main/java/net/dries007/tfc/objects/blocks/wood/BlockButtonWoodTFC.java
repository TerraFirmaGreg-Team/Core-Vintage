package net.dries007.tfc.objects.blocks.wood;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.Tree;

import java.util.HashMap;
import java.util.Map;

public class BlockButtonWoodTFC extends BlockButtonWood {

  private static final Map<Tree, BlockButtonWoodTFC> MAP = new HashMap<>();
  public final Tree wood;

  public BlockButtonWoodTFC(Tree wood) {
    this.wood = wood;
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    setHardness(0.5F);
    setSoundType(SoundType.WOOD);
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }

  public static BlockButtonWoodTFC get(Tree wood) {
    return MAP.get(wood);
  }
}
