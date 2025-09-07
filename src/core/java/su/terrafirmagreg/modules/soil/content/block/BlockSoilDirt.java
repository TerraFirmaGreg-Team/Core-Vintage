package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IDirtBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IMudBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;
import su.terrafirmagreg.modules.soil.content.block.spi.BlockSoil;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;

public class BlockSoilDirt extends BlockSoil implements IDirtBlock, IMudBlock {

  public BlockSoilDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("dirt"))
      .renderLayer(this.getBlockState().getBaseState().getValue(CLAY) ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID)
      .tag(Tags.DIRT);

    setDefaultState(getBlockState().getBaseState().withProperty(CLAY, Boolean.FALSE));

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
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
