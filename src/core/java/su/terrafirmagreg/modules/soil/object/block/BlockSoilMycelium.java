package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

public class BlockSoilMycelium extends BlockSoilGrass {


  public BlockSoilMycelium(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("mycelium"))
      .addOreDict("mycelium");

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
