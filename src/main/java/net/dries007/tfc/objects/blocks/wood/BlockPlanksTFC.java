package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.OreDictionaryHelper;

import java.util.HashMap;
import java.util.Map;

public class BlockPlanksTFC extends Block {

  private static final Map<Tree, BlockPlanksTFC> MAP = new HashMap<>();
  public final Tree wood;

  public BlockPlanksTFC(Tree wood) {
    super(Material.WOOD);
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    this.wood = wood;
    setSoundType(SoundType.WOOD);
    setHardness(2.0F).setResistance(5.0F);
    setHarvestLevel(ToolClasses.AXE, 0);
    OreDictionaryHelper.register(this, "plank", "wood");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "plank", "wood", wood.getRegistryName().getPath());
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }

  public static BlockPlanksTFC get(Tree wood) {
    return MAP.get(wood);
  }
}
