package net.dries007.tfc.objects.blocks.blocktype;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.types.BlockTypesTFCF.RockTFCF;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BlockWallTFCF extends BlockWall {

  private static final Map<Rock, EnumMap<RockTFCF, BlockWallTFCF>> TABLE = new HashMap<>();
  public final BlockRockVariantTFCF parent;

  public BlockWallTFCF(BlockRockVariantTFCF modelBlock) {
    super(modelBlock);

    if (!TABLE.containsKey(modelBlock.rock)) {TABLE.put(modelBlock.rock, new EnumMap<>(RockTFCF.class));}
    TABLE.get(modelBlock.rock).put(modelBlock.rockTFCF, this);

    parent = modelBlock;
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "wall");
    OreDictionaryHelper.registerRockType(this, modelBlock.rockTFCF, "wall");
  }

  public static BlockWallTFCF get(Rock rock, RockTFCF rockTFCF) {
    return TABLE.get(rock).get(rockTFCF);
  }

  @Override
  public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
    items.add(new ItemStack(this));
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 0;
  }

  @Override
  @Nonnull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }
}
