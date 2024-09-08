package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

public class BlockSoilMycelium extends BlockSoilGrass {


  public BlockSoilMycelium(SoilBlockVariant variant, SoilType type) {
    super(variant, type);

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }

  @Override
  public IBlockColor getBlockColor() {
    return null;
  }

  @Override
  public IItemColor getItemColor() {
    return null;
  }

}
