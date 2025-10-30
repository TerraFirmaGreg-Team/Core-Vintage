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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;
import static su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
public class BlockSoilDirt extends BaseBlockFalling implements IDirtBlock, IMudBlock, ISoilEntry {

  protected final SoilType type;

  public BlockSoilDirt(SoilType type) {
    super(BlockSettings.of()
      .material(Material.GROUND)
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .tag(Tags.DIRT)
      .hardness(2.0F)
    );

    this.type = type;

    setDefaultState(getBlockState().getBaseState()
      .withProperty(CLAY, Boolean.FALSE)
    );

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL);
  }


  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CLAY);
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(CLAY) ? Items.CLAY_BALL : ItemsSoil.PILE.get(type);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.GRASS.get(type).getDefaultState();
  }

  @Override
  public IBlockState getMud() {
    return BlocksSoil.MUD.get(type).getDefaultState();
  }
}
