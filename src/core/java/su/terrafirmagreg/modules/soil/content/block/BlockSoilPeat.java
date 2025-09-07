package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.IDirtBlock;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockSoilPeat extends BaseBlock implements IDirtBlock {

  public BlockSoilPeat() {
    super(BlockSettings.of()
      .material(Material.GROUND)
      .registryKey("peat")
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .sound(SoundType.GROUND)
      .fireInfo(5, 10)
      .hardness(0.6F)
    );
    
    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.PEAT_GRASS.getDefaultState();
  }
}
