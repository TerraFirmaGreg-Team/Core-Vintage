package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class BaseBlockStairs extends BlockStairs implements IBlockEntry {

  private static final Map<Block, BlockStairs> BLOCK_TO_STAIRS = new Object2ObjectOpenHashMap<>();

  protected final BlockSettings settings;

  public BaseBlockStairs(Block model) {
    this(model, BlockSettings.of(model));

  }

  public BaseBlockStairs(Block model, BlockSettings settings) {
    super(model.getDefaultState());

    this.settings = settings;

    BLOCK_TO_STAIRS.put(model, this);
  }

  public static BlockStairs getStairsFromBlock(Block blockIn) {
    BlockStairs item = BLOCK_TO_STAIRS.get(blockIn);
    return item == null ? (BlockStairs) Blocks.AIR : item;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public String getHarvestTool(IBlockState state) {
    return this.settings.getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {
    return this.settings.getHarvestLevel();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
