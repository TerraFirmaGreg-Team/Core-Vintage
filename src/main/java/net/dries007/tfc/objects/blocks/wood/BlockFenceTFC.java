package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.OreDictionaryHelper;

import java.util.HashMap;
import java.util.Map;

public class BlockFenceTFC extends BlockFence {

  private static final Map<Tree, BlockFenceTFC> MAP = new HashMap<>();
  public final Tree wood;

  public BlockFenceTFC(Tree wood) {
    super(Material.WOOD, Material.WOOD.getMaterialMapColor());
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    this.wood = wood;
    setHarvestLevel(ToolClasses.AXE, 0);
    setHardness(2.0F); // match vanilla
    setResistance(15.0F);
    OreDictionaryHelper.register(this, "fence", "wood");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "fence", "wood", wood.getRegistryName().getPath());
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }

  public static BlockFenceTFC get(Tree wood) {
    return MAP.get(wood);
  }
}
