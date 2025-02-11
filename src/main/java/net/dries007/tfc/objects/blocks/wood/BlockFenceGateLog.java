package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

import net.dries007.tfc.api.types.Tree;

import java.util.HashMap;
import java.util.Map;

public class BlockFenceGateLog extends BlockFenceGate {

  private static final Map<Tree, BlockFenceGateLog> MAP = new HashMap<>();
  public final Tree wood;

  public BlockFenceGateLog(Tree wood) {
    super(BlockPlanks.EnumType.OAK);
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    this.wood = wood;
    setHarvestLevel("axe", 0);
    setHardness(2.0F); // match vanilla
    setResistance(15.0F);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "fence", "gate", "wood");
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "fence", "gate", "wood", wood.getRegistryName().getPath());
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "fence", "gate", "log", "wood");
    OreDictionaryHelper.register(this, "fence", "gate", "log", "wood", wood.getRegistryName().getPath());
    Blocks.FIRE.setFireInfo(this, 5, 20);
  }

  public static BlockFenceGateLog get(Tree wood) {
    return MAP.get(wood);
  }
}
