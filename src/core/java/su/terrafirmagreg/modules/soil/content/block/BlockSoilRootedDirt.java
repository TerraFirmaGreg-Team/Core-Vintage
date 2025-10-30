package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFalling;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IDirtBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IMudBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
public class BlockSoilRootedDirt extends BaseBlockFalling implements IDirtBlock, IMudBlock, ISoilEntry {

  protected final SoilType type;

  public BlockSoilRootedDirt(SoilType type) {
    super(BlockSettings.of()
      .material(Material.GROUND)
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .tag(Tags.ROOTED_DIRT)
      .addOreDict("coarse_dirt")
      .hardness(2.0F)
    );

    this.type = type;

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.PILE.get(type);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.DRY_GRASS.get(type).getDefaultState();
  }

  @Override
  public IBlockState getMud() {
    return BlocksSoil.MUD.get(type).getDefaultState();
  }
}
